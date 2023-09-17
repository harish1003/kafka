package org.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.AccountPojo.Account;
import org.apache.kafka.common.serialization.Serializer;

public class customSerializer implements Serializer<Account> {


    @Override
    public byte[] serialize(String topic, Account account) {
        byte response[] = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            response = objectMapper.writeValueAsString(account).getBytes();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
