package org.partitioning;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

public class VIPPartitioner implements Partitioner {


    @Override
    public int partition(String topic, Object key, byte[] Keybytes, Object value, byte[] Valbytes1, Cluster cluster) {
        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);

        if(((String)key).equals("Harish")){
            System.out.println("Partitions size : "+ partitionInfoList.size());
            return partitionInfoList.size()-2;
        }

        return Utils.murmur2(Keybytes)%partitionInfoList.size()-1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
