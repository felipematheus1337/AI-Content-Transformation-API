package slop_generator.poc.controller;

import org.springframework.web.bind.annotation.*;
import slop_generator.poc.dto.*;
import slop_generator.poc.service.SlopService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SlopController {

    private final SlopService service;

    public SlopController(SlopService service) {
        this.service = service;
    }

    @PostMapping("/tweets")
    public TweetConversionResponse convertTweet(
            @RequestBody TweetConversionRequest request) {
        
        NewTweets result = service.generate(
                request.originalTweet(),
                request.topicHint(),
                request.emojiLevel(),
                request.modernizationLevel()
        );

        List<TweetVariantResponse> variants = result.tweets().stream()
                .map(tv -> new TweetVariantResponse(
                        tv.tweet(),
                        tv.voice() != null ? tv.voice().name() : "UNKNOWN",
                        Math.random()
                ))
                .toList();

        return new TweetConversionResponse(variants);
    }

    @GetMapping("/tweets")
    public TweetConversionResponse tweets(
            @RequestParam(name = "originalTweet", required = false,
                    defaultValue = "How to learn how to program fast") String originalTweet,
            @RequestParam(name = "topicHint", required = false,
                    defaultValue = "Spring AI") String topicHint,
            @RequestParam(name = "emojiLevel", required = false,
                    defaultValue = "low") String emojiLevel,
            @RequestParam(name = "modernizationLevel", required = false,
                    defaultValue = "extreme") String modernizationLevel) {

        NewTweets result = service.generate(originalTweet, topicHint, emojiLevel, modernizationLevel);

        List<TweetVariantResponse> variants = result.tweets().stream()
                .map(tv -> new TweetVariantResponse(
                        tv.tweet(),
                        tv.voice() != null ? tv.voice().name() : "UNKNOWN",
                        Math.random()
                ))
                .toList();

        return new TweetConversionResponse(variants);
    }
}

