package org.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.AccountPojo.Account;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class customDeserializer implements Deserializer<Account> {


    @Override
    public Account deserialize(String topic, byte[] bytes) {
        Account account = new Account();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            account = objectMapper.readValue(bytes,Account.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return account;
    }
}
