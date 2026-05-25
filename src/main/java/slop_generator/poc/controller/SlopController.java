package slop_generator.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slop_generator.poc.service.SlopService;

@RestController
public class SlopController {

    private final SlopService service;

    public SlopController(SlopService service) {
        this.service = service;
    }

    @GetMapping("/tweets")
    public String tweets(
            @RequestParam(name = "originalTweet", required = false,
                    defaultValue = "How to learn how to program fast") String originalTweet,
            @RequestParam(name = "topicHint", required = false,
                    defaultValue = "Spring AI") String topicHint,
            @RequestParam(name = "emojiLevel", required = false,
                    defaultValue = "low") String emojiLevel,
            @RequestParam(name = "modernizationLevel", required = false,
                    defaultValue = "extreme") String modernizationLevel) {


        return service.generate(originalTweet, topicHint, emojiLevel, modernizationLevel);

    }
}

