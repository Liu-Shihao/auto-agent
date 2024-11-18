package com.lsh.auto.agent;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import com.lsh.auto.agent.model.ResultMessage;
import com.lsh.auto.agent.model.TaskMessage;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
@Data
public class AgentManager {

    /**
     * agent registry
     */
    private ConcurrentHashMap<String,Agent> agents;

    private final HazelcastInstance hazelcastInstance;


    public AgentManager(){
        this.agents = new ConcurrentHashMap<>();
        Config config = new Config();
        config.setClusterName("AgentManager");
        this.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        ITopic<TaskMessage> taskResult = hazelcastInstance.getTopic("taskResult");
        taskResult.addMessageListener(new MessageListener<>() {
            @Override
            public void onMessage(Message<TaskMessage> message) {
                System.out.println("AgentManager Received message: " + message.getMessageObject());
            }
        });
    }

    /**
     *
     * @param agent
     */
    public void register(Agent agent){
        // 1.agent register
        this.agents.put(agent.getId(), agent);

    }

    public void dispatchTask(String topic, TaskMessage task) {
        ITopic<TaskMessage> topic1 = hazelcastInstance.getTopic(topic);
        topic1.publish(task);
    }

}
