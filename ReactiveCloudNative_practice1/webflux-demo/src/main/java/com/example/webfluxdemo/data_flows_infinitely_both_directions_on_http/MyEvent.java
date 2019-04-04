package com.example.webfluxdemo.data_flows_infinitely_both_directions_on_http;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "event") // 1
public class MyEvent {
    @Id
    private String id;    // 2
    private String message;



    public MyEvent(String pMessage) {
        this.message = pMessage;
    }
}