package com.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import okhttp3.Headers;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    void sendMessage() throws InterruptedException {
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // To read real time stream data from wikimedia, we use event source
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topicName);

        // using this only we got the data
        Headers headers = new Headers.Builder()
                .add("User-Agent", "Kafka-Wikimedia-Producer/1.0 (patelbhumi016@gmail.com)")
                .build();
        EventSource.Builder builder =new EventSource.Builder(eventHandler, URI.create(url)).headers(headers);;
        EventSource eventSource = builder.build();
        eventSource.start();
        Thread.currentThread().join();
        TimeUnit.MINUTES.sleep(10);
    }
}
