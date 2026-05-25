package slop_generator.poc.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SlopService {

    private final ChatClient chatClient;

    public SlopService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


}
