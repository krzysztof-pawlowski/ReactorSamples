package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

public class HotPublisherExample {

    private static Logger log = LoggerFactory.getLogger(HotPublisherExample.class);

    public static void main(String[] args)  {

        UnicastProcessor<String> hotSource = UnicastProcessor.create();

        Flux<String> hotFlux = hotSource.publish()
            .autoConnect()
            .map(String::toUpperCase);


        hotFlux.subscribe(d -> log.info("Subscriber 1 to Hot Source: "+d));

        hotSource.onNext("blue");
        hotSource.onNext("green");

        hotFlux.subscribe(d -> log.info("Subscriber 2 to Hot Source: "+d));

        hotSource.onNext("orange");
        hotSource.onNext("purple");
        hotSource.onComplete();

    }
}
