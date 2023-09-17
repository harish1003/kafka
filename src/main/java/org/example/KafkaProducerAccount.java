package org.example;

import org.AccountPojo.Account;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.partitioning.VIPPartitioner;
import org.serializers.customSerializer;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaProducerAccount {

    public static void main(String[] args) {
        System.out.println(AdminClientConfig.configNames());
        String topicName = "ACCOUNTS";
        createTopicIfNotExist(topicName);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");

        KafkaProducer<String,Integer> producer = new KafkaProducer(properties);
        ProducerRecord<String,Integer> record = new ProducerRecord<>("ACCOUNTS","HARISH",624849547);

        /* fire and forget

        try{
            producer.send(record);
            System.out.println("Message sent successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            producer.close();
        }*/

        //  get response
        try{
            // Approach one
            /*Future<RecordMetadata> records = producer.send(record);
            RecordMetadata records = records.get();*/

            // Approach 2 Sync Send
            RecordMetadata records = producer.send(record).get();
            System.out.println(records.offset());
            System.out.println(records.partition());
            System.out.println("Message sent successfully");

            //Async Send
            producer.send(record,new OrderCallBack());
            // here check will be done by the ordercallback call class which implements the kafka callBack interface
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            producer.close();
        }

        // creating the custom serializer

        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers","localhost:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", customSerializer.class.getName());
        prop.setProperty("partitioner.class", VIPPartitioner.class.getName());
//        prop.setProperty("value.serializer","org.serializers.customSerializer");
        createTopicIfNotExist("DEBIT");

        KafkaProducer<String, Account> producer1 = new KafkaProducer<String, Account>(prop);

        Account account = new Account();
        account.setAccountNum(1001);
        account.setName("Postpaid");
        account.setAmountDebited(500);
        account.setRemBalance(1002002);
        String customerName = "Harish";

        ProducerRecord<String, Account> producerRecord = new ProducerRecord<>("DEBIT",customerName,account);

        try {
            producer1.send(producerRecord, new OrderCallBack());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            producer1.close();
        }



    }

    private static void createTopicIfNotExist(String topicName) {

                String bootstrapServers = "localhost:9092"; // Replace with your Kafka broker address

                // Set up Kafka admin client properties
                Properties adminClientProps = new Properties();
                adminClientProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);


                // Define the topic name

                try (AdminClient adminClient = AdminClient.create(adminClientProps)) {
                    // Check if the topic already exists
                    ListTopicsResult listTopicsResult = adminClient.listTopics();
                    Set<String> existingTopics = listTopicsResult.names().get();

                    if (!existingTopics.contains(topicName)) {
                        // Define the number of partitions and replication factor
                        int numPartitions = 9;
                        short replicationFactor = 1;

                        // Create a new topic
                        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);

                        // Add any additional topic configurations if needed
                        // newTopic.configs(Collections.singletonMap("cleanup.policy", "compact"));

                        // Create the topic using the admin client
                        adminClient.createTopics(Collections.singletonList(newTopic));

                        System.out.println("Topic '" + topicName + "' created successfully.");
                    } else {
                        System.out.println("Topic '" + topicName + "' already exists.");
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
    }
        }

