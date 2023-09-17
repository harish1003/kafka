package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerAccount {

    public static void main(String args[]){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.setProperty("group.id","AccountTopic");
        KafkaConsumer<String,Integer> consumer = new KafkaConsumer<String, Integer>(properties);
        consumer.subscribe(Collections.singletonList("ACCOUNTS"));

        consumer.poll(Duration.ofMinutes(1));
        ConsumerRecords<String,Integer> consumed = consumer.poll(Duration.ofMinutes(2));
        for(ConsumerRecord<String, Integer> record : consumed){
            System.out.println("Account Name" + record.key());
            System.out.println("Account Number" + record.value());
            System.out.println(record.toString());
        }
        consumer.close();
    }

}
