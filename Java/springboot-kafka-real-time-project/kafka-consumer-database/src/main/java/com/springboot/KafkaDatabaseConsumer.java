package com.springboot;

import com.springboot.entity.WikiMediaData;
import com.springboot.repository.WikiMediaDataRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);
    private WikiMediaDataRepositoryInterface dataRepositoryInterface;

    public KafkaDatabaseConsumer(WikiMediaDataRepositoryInterface dataRepositoryInterface) {
        this.dataRepositoryInterface = dataRepositoryInterface;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage) {
        LOGGER.info("Event Message Received -> {}", eventMessage);
        WikiMediaData wikiMediaData = new WikiMediaData();
        wikiMediaData.setWikiEventData(eventMessage);
        dataRepositoryInterface.save(wikiMediaData);
    }
}
