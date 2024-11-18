package com.lsh.auto.agent;

import com.lsh.auto.agent.model.TaskMessage;

public class AutoAgentTest {

    public static void main(String[] args) {
        AgentManager agentManager = new AgentManager();

        Agent schedulingAssistantAgent = new Agent(
                "SchedulingAssistantAgent",
                "AI that helps you schedule meetings",
                agentManager.getHazelcastInstance()
                );
        agentManager.register(schedulingAssistantAgent);

        TaskMessage message = new TaskMessage(
                "1",
                "SchedulingAssistantAgent",
                "Please help me schedule a meeting at 2 p.m.");
        agentManager.dispatchTask("SchedulingAssistantAgent",message);

    }
}
