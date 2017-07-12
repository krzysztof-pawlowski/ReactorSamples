package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxErrorExample {

    private static Logger log = LoggerFactory.getLogger(FluxErrorExample.class);

    public static void main(String[] args) {

        Flux<Integer> publisher = Flux.generate(subscriber -> {
            log.info("Started emitting");

            log.info("Emitting 1st");
            subscriber.next(1);

            subscriber.error(new RuntimeException("Test exception"));

        });

        log.info("=======================");
        log.info("EXAMPLE 1 - NO HANDLING");
        log.info("=======================");

        publisher.subscribe(
            val -> log.info("Received: " + val),
            err -> log.error("Error: " + err),
            () -> log.info("Completed!")
        );

        log.info("===========================");
        log.info("EXAMPLE 2 - RETURN ON ERROR");
        log.info("===========================");

        publisher
            .onErrorReturn(-1)
            .subscribe(
                val -> log.info("Received: " + val),
                err -> log.error("Error: " + err),
                () -> log.info("Completed!")
            );

        log.info("==================================");
        log.info("EXAMPLE 3 - SWITCH STREAM ON ERROR");
        log.info("==================================");

        Flux<Integer> publisherOnError = Flux.range(5, 3);

        publisher
            .onErrorResumeWith(throwable -> publisherOnError)
            .subscribe(
                val -> log.info("Received: " + val),
                err -> log.error("Error: " + err),
                () -> log.info("Completed!")
            );

    }
}
