package com.example.demo.tereni.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.tereni.model.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Friendship> findByReceiverIdAndStatus(Long receiverId, Friendship.Status status);
    List<Friendship> findBySenderIdAndStatus(Long senderId, Friendship.Status status);

    @Query("""
    		   SELECT f FROM Friendship f
    		   WHERE ((f.sender.id = :a AND f.receiver.id = :b)
    		       OR (f.sender.id = :b AND f.receiver.id = :a))
    		     AND f.status IN (com.example.demo.tereni.model.Friendship$Status.PENDING,
    		                      com.example.demo.tereni.model.Friendship$Status.ACCEPTED)
    		""")
    		Optional<Friendship> findActiveBetween(@Param("a") Long a, @Param("b") Long b);


    @Query("""
        SELECT f FROM Friendship f
        WHERE f.status = com.example.demo.tereni.model.Friendship$Status.ACCEPTED
          AND (f.sender.id = :userId OR f.receiver.id = :userId)
    """)
    List<Friendship> findAcceptedForUser(@Param("userId") Long userId);
}

