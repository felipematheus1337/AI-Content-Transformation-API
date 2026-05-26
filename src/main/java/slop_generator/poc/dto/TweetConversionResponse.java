package slop_generator.poc.dto;

import java.util.List;

public record TweetConversionResponse(
        List<TweetVariantResponse> variants
) {
}

