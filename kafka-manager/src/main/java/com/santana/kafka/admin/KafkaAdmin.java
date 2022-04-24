package com.santana.kafka.admin;

import org.apache.kafka.clients.admin.*;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

public class KafkaAdmin {

    public static void list(AdminClient client) throws ExecutionException, InterruptedException {
        ListTopicsResult topics = client.listTopics();
        topics.names().get().forEach(System.out::println);
    }

    public static void create(String name, int partitions, short replications, AdminClient client) {
        final NewTopic topic = new NewTopic(name, partitions, replications);
        try{
            final CreateTopicsResult result = client.createTopics(singletonList(topic));
            result.all().get();
        }
        catch(Exception e) {
            throw new RuntimeException("Failed to create topic ".concat(name), e);
        }
    }

    public static void describe(String name, AdminClient client) throws ExecutionException, InterruptedException {
        DescribeTopicsResult result = client.describeTopics(singletonList(name));
        result.all().get().forEach((topicName, description) -> System.out.println(topicName + " " + description.topicId() + " " + description.partitions()));
    }

    public static void delete(String name, AdminClient client) {
        try{
            DeleteTopicsResult result = client.deleteTopics(singletonList(name));
            result.all().get();
        }
        catch(Exception e) {
            throw new RuntimeException("Failed to delete topic ".concat(name), e);
        }
    }

    public static void listConsumerGroups(AdminClient client) throws ExecutionException, InterruptedException {
        ListConsumerGroupsResult result = client.listConsumerGroups();
        result.all().get().stream().map(ConsumerGroupListing::groupId).forEach(System.out::println);
    }

    public static void deleteConsumerGroup(String name, AdminClient client) {
        try{
            DeleteConsumerGroupsResult result = client.deleteConsumerGroups(singletonList(name));
            result.all().get();
        }
        catch(Exception e) {
            throw new RuntimeException("Failed to delete consumer group ".concat(name), e);
        }
    }

    public static void describeCluster(AdminClient client) throws ExecutionException, InterruptedException {
        DescribeClusterResult result = client.describeCluster();
        System.out.println(result.clusterId().get());
    }
}
