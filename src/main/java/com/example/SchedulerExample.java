package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SchedulerExample {

    private static Logger log = LoggerFactory.getLogger(SchedulerExample.class);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

        Flux<Integer> publisher = Flux.range(1, 5);

        publisher
            .subscribeOn(Schedulers.fromExecutorService(fixedThreadPool))
            .map(integer -> {
                log.info("Value: " + integer);
                return integer;
            })

            .publishOn(Schedulers.newParallel("foo"))
            .subscribe(
                val -> log.info("Received: " + val),
                err -> log.error("Error: " + err),
                () -> log.info("Completed!")
            );

    }
}
