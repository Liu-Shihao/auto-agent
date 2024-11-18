package com.lsh.auto.agent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMessage {

    private String taskId;
    private String topic;
    private String payload;
}
