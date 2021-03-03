package com.codecool.priceudpateservice.service;

import com.codecool.priceudpateservice.service.PriceChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class PriceChangePublisher implements
        ApplicationListener<PriceChangeEvent>,
        Consumer<FluxSink<PriceChangeEvent>> {

    private final Executor executor;
    private final BlockingQueue<PriceChangeEvent> queue =
            new LinkedBlockingQueue<>();

    @Autowired
    PriceChangePublisher(Executor executor) {
        this.executor = executor;
    }


    @Override
    public void onApplicationEvent(PriceChangeEvent event) {
        this.queue.offer(event);
    }

    @Override
    public void accept(FluxSink<PriceChangeEvent> sink) {
        this.executor.execute(() -> {
            while (true)
                try {
                    PriceChangeEvent event = queue.take();
                    sink.next(event);
                }
                catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }
}