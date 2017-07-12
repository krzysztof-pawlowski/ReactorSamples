package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class ZipExample {

    private static Logger log = LoggerFactory.getLogger(ZipExample.class);

    public static void main(String[] args) throws InterruptedException {
        Flux<String> colors = Flux.just("red", "green", "blue");
        Flux<Long> timer = Flux.interval(Duration.ofSeconds(1));

        Flux<String> periodicEmitter = Flux.zip(colors, timer, (key, val) -> key);

        final CountDownLatch latch = new CountDownLatch(1);

        periodicEmitter.subscribe(
            val -> log.info("Received: " + val),
            err -> log.error("Error: " + err),
            () -> {
                log.info("Completed!");
                latch.countDown();
            }
        );

        latch.await();
    }
}
