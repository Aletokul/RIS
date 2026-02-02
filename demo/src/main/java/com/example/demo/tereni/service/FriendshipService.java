package com.example.demo.tereni.service;

import com.example.demo.tereni.model.Friendship;
import com.example.demo.tereni.model.User;
import com.example.demo.tereni.repository.FriendshipRepository;
import com.example.demo.tereni.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendshipService {

    private final FriendshipRepository repo;
    private final UserRepository users;

    public FriendshipService(FriendshipRepository repo, UserRepository users) {
        this.repo = repo;
        this.users = users;
    }

    // slanje zahteva po username-u
    public void sendRequestByUsername(Long requesterId, String toUsername) {
        User requester = users.findById(requesterId).orElseThrow();
        User addressee = users.findByUsername(toUsername)
                .orElseThrow(() -> new IllegalArgumentException("Korisnik ne postoji: " + toUsername));
        sendRequest(requester.getId(), addressee.getId());
    }

    // slanje zahteva kada imaš ID-jeve
    public void sendRequest(Long requesterId, Long addresseeId) {
        if (requesterId.equals(addresseeId)) {
            throw new IllegalArgumentException("Ne možeš poslati zahtev sebi");
        }

        // PROVERA samo PENDING/ACCEPTED – REJECTED ne blokira
        repo.findActiveBetween(requesterId, addresseeId).ifPresent(f -> {
            if (f.getStatus() == Friendship.Status.ACCEPTED) {
                throw new IllegalStateException("Već ste prijatelji.");
            } else { // PENDING
                if (f.getSender().getId().equals(requesterId)) {
                    throw new IllegalStateException("Već si poslao zahtev tom korisniku.");
                } else {
                    throw new IllegalStateException("Taj korisnik ti je već poslao zahtev — prihvati ga.");
                }
            }
        });

        User requester = users.findById(requesterId).orElseThrow();
        User addressee = users.findById(addresseeId).orElseThrow();

        Friendship f = Friendship.builder()
                .sender(requester)
                .receiver(addressee)
                .status(Friendship.Status.PENDING)
                .build();
        repo.save(f);
    }

    public void accept(Long friendshipId, Long currentUserId) {
        Friendship f = repo.findById(friendshipId).orElseThrow();
        if (!f.getReceiver().getId().equals(currentUserId)) {
            throw new IllegalStateException("Nemaš pravo da prihvatiš ovaj zahtev");
        }
        f.setStatus(Friendship.Status.ACCEPTED);
        repo.save(f);
    }

    public void decline(Long friendshipId, Long currentUserId) {
        Friendship f = repo.findById(friendshipId).orElseThrow();
        if (!f.getReceiver().getId().equals(currentUserId)) {
            throw new IllegalStateException("Nemaš pravo da odbiješ ovaj zahtev");
        }
        repo.delete(f); // brisanje umesto setStatus(REJECTED)
    }


    //ako sam ja sender vraca mene ako je false vraca sendera
    public List<User> myFriends(Long userId) {
        var friendships = repo.findAcceptedForUser(userId);
        return friendships.stream()
                .map(f -> f.getSender().getId().equals(userId) ? f.getReceiver() : f.getSender())
                .toList();
    }


    public List<Friendship> pendingForMe(Long userId) {
        return repo.findByReceiverIdAndStatus(userId, Friendship.Status.PENDING);
    }

    public List<Friendship> myPending(Long userId) {
        return repo.findBySenderIdAndStatus(userId, Friendship.Status.PENDING);
    }
}
