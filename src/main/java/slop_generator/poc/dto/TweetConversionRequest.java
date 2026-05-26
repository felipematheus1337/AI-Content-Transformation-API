package slop_generator.poc.dto;

public record TweetConversionRequest(
        String originalTweet,
        String topicHint,
        String emojiLevel,
        String modernizationLevel
) {
}

