package com.lsh.auto.agent;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import com.lsh.auto.agent.model.ResultMessage;
import com.lsh.auto.agent.model.TaskMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Agent {

    private String id;
    private String name;
    private String describe;
    private final HazelcastInstance hazelcastInstance;


    public Agent(String name,String describe,HazelcastInstance hazelcastInstance){
        this.id = UUID.randomUUID().toString().replaceAll("_","");
        this.name = name;
        this.describe = describe;
        this.hazelcastInstance = hazelcastInstance;

        ITopic<TaskMessage> topic = this.hazelcastInstance.getTopic(this.name);
        topic.addMessageListener(new MessageListener<>() {
            @Override
            public void onMessage(Message<TaskMessage> message) {
                System.out.println("Agent Received message: " + message.getMessageObject());
                ITopic<String> taskResult = hazelcastInstance.getTopic("taskResult");
                taskResult.publish(new ResultMessage(message.getMessageObject().getTaskId(),message.getMessageObject().getTopic(),"agent result").toString());
            }
        });

    }

}
