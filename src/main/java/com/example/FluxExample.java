package com.example;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxExample {

    private static Logger log = LoggerFactory.getLogger(FluxExample.class);

    public static void main(String[] args) throws InterruptedException {

        Flux<Integer> publisher = Flux.just(1, 2, 3);

        publisher.subscribe(new Subscriber<Integer>() {

            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            public void onNext(Integer integer) {
                log.info("Received: " + integer);
            }

            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
            }

            public void onComplete() {
                log.info("Completed!");
            }
        });

    }
}
