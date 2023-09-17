package org.example;

import org.AccountPojo.Account;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;

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

        ConsumerRecords<String,Integer> consumed = consumer.poll(Duration.ofSeconds(20));
        for(ConsumerRecord<String, Integer> record : consumed){
            System.out.println("Account Name" + record.key());
            System.out.println("Account Number" + record.value());
            System.out.println(record.toString());
        }
        consumer.close();


        //custom Deserializer
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers","localhost:9092");
        prop.setProperty("key.deserializer", StringDeserializer.class.getName());
        prop.setProperty("value.deserializer","org.serializers.customDeserializer");
        prop.setProperty("group.id","DEBITTOPIC");

        KafkaConsumer<String,Account> consumer1 = new KafkaConsumer<String, Account>(prop);
        consumer1.subscribe(Collections.singletonList("DEBIT"));

        ConsumerRecords<String, Account> consumed1 = consumer1.poll(Duration.ofSeconds(20));

        for(ConsumerRecord<String, Account> record : consumed1){
            System.out.println("Customer Name" + record.key());
            Account account = record.value();
            System.out.println(account.getAmountDebited());
        }
        consumer1.close();


    }

}
