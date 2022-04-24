package com.santana.kafka.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaManager {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        AdminClient client = AdminClient.create(properties);

        KafkaAdmin.create("topic-1", 2, (short) 1, client);
        KafkaAdmin.create("topic-2", 2, (short) 1, client);
        KafkaAdmin.list(client);
        KafkaAdmin.describe("topic-1", client);
        KafkaAdmin.delete("topic-1", client);
        KafkaAdmin.delete("topic-2", client);
        KafkaAdmin.listConsumerGroups(client);
        KafkaAdmin.deleteConsumerGroup("group", client);
        KafkaAdmin.describeCluster(client);
    }
}
