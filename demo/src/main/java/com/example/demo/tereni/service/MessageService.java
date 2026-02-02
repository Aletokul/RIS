package com.example.demo.tereni.service;

import com.example.demo.tereni.model.Message;
import com.example.demo.tereni.model.User;
import com.example.demo.tereni.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public void sendMessage(User sender, User receiver, String content) {
        if (sender == null || receiver == null) throw new IllegalArgumentException("Sender/receiver null");
        if (content == null || content.isBlank()) throw new IllegalArgumentException("Prazna poruka");

        Message m = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .build(); // createdAt i is_read postavlja @PrePersist
        repo.save(m);
    }

    public List<Message> getConversation(User u1, User u2) {
        return repo.findConversation(u1.getId(), u2.getId());
    }
}
