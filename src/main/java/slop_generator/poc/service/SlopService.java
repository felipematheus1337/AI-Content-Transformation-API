package slop_generator.poc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import slop_generator.poc.constants.SlopConstants;

import java.util.Map;

@Service
public class SlopService {

    private final ChatClient chatClient;
    private static final Logger LOG = LoggerFactory.getLogger(SlopService.class);

    public SlopService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    public String generate(String originalTweet, String topicHint, String emojiLevel, String modernizationLevel) {
        LOG.info("Generating slop for originalTweet: {}, topicHint: {}, emojiLevel: {}," +
                        " modernizationLevel: {}",
                originalTweet, topicHint, emojiLevel, modernizationLevel);

        PromptTemplate promptTemplate = new PromptTemplate(SlopConstants.USER_TEMPLATE);
        var userPrompt = createUserPrompt(promptTemplate, originalTweet, topicHint, emojiLevel, modernizationLevel);

        return chatClient
                .prompt()
                .system(SlopConstants.SYSTEM_TEMPLATE)
                .user(userPrompt)
                .call()
                .content();

    }

    private String createUserPrompt(PromptTemplate template, String originalTweet, String topicHint, String emojiLevel, String modernizationLevel) {
        LOG.info("Creating user prompt with originalTweet: {}, topicHint: {}, emojiLevel: {},",
                        " modernizationLevel: {}",
                originalTweet, topicHint, emojiLevel, modernizationLevel);
        return template.render(Map.of(
                "postText", originalTweet,
                "topicHint", topicHint,
                "emojiLevel", emojiLevel,
                "modernizationLevel", modernizationLevel
        ));
    }
}
