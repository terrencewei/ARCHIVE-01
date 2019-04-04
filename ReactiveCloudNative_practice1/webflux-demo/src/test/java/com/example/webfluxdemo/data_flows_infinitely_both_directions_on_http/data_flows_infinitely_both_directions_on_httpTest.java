package com.example.webfluxdemo.data_flows_infinitely_both_directions_on_http;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
// using defined port (server.port) at application.properties
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class data_flows_infinitely_both_directions_on_httpTest {

    @Value("${server.port}")
    private String port;



    /**
     * 1. 声明速度为每秒一个MyEvent元素的数据流，不加take的话表示无限个元素的数据流；
     * 2. 声明请求体的数据格式为application/stream+json；
     * 3. body方法设置请求体的数据。
     */
    @Test
    public void webClientTest4() {
        Flux<MyEvent> eventFlux = Flux
                .interval(Duration.ofSeconds(1))
                .map(l -> new MyEvent("message-" + l))
                .take(5); // 1
        WebClient webClient = WebClient.create("http://localhost:" + port);
        webClient
                .post()
                .uri("/events")
                .contentType(MediaType.APPLICATION_STREAM_JSON) // 2
                .body(eventFlux, MyEvent.class) // 3
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}