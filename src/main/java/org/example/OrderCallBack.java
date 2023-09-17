package org.example;

import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallBack implements org.apache.kafka.clients.producer.Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            e.printStackTrace();
        } else {
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
//            System.out.println(recordMetadata.serializedKeySize());
//            System.out.println(recordMetadata.serializedValueSize());
//            System.out.println(recordMetadata.timestamp());
//            System.out.println(recordMetadata.topic());
//            System.out.println(recordMetadata.timestamp());
            System.out.println("Message sent successfully");

        }
    }
}
