package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class MergeExample {

    private static Logger log = LoggerFactory.getLogger(MergeExample.class);

    public static void main(String[] args) throws InterruptedException {
        Flux<String> colors1 = Flux.just("red", "green");
        Flux<String> colors2 = Flux.just("blue", "yellow");

        Flux<String> periodicEmitter = Flux.merge(colors1, colors2);

        periodicEmitter.subscribe(
            val -> log.info("Received: " + val),
            err -> log.error("Error: " + err),
            () -> log.info("Completed!")
        );

    }
}
