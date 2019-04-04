package com.example.webfluxdemo.reactive_spring_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
// using defined port (server.port) at application.properties
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class reactive_spring_dataTest {

    @Value("${server.port}")
    private String port;

    @Autowired
    private WebTestClient webTestClient;



    @Test
    public void testCreateTweet() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        User user = new User("zhangsan", "11111", "example@email.com", "Zhang San", simpleDateFormat.parse("1990/1/1"));
        webTestClient
                .post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id")
                .isNotEmpty()
                .jsonPath("$.username")
                .isEqualTo("zhangsan");
    }



    /**
     * 1. 创建WebClient对象并指定baseUrl
     * 2. HTTP GET；
     * 3. 异步地获取response信息；
     * 4. 将response body解析为字符串；
     * 5. 打印出来；
     * 6. 由于是异步的，我们将测试线程sleep 1秒确保拿到response，也可以像前边的例子一样用CountDownLatch。
     */
    @Test
    public void webClientTest1() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:" + port);   // 1
        Mono<String> resp = webClient
                .get()
                .uri("/hello") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4
        resp.subscribe(System.out::println);    // 5
        TimeUnit.SECONDS.sleep(1);  // 6
    }



    /**
     * 1. 这次我们使用WebClientBuilder来构建WebClient对象；
     * 2. 配置请求Header：Content-Type: application/stream+json；
     * 3. 获取response信息，返回值为ClientResponse，retrive()可以看做是exchange()方法的“快捷版”；
     * 4. 使用flatMap来将ClientResponse映射为Flux；
     * 5. 只读地peek每个元素，然后打印出来，它并不是subscribe，所以不会触发流；
     * 6. 上个例子中sleep的方式有点low，blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用。
     */
    @Test
    public void webClientTest2() throws InterruptedException {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:" + port)
                .build(); // 1
        webClient
                .get()
                .uri("/user")
                .accept(MediaType.APPLICATION_STREAM_JSON) // 2
                .exchange() // 3
                .flatMapMany(response -> response.bodyToFlux(User.class))   // 4
                .doOnNext(System.out::println)  // 5
                .blockLast();   // 6
    }



    /**
     * 1. 配置请求Header：Content-Type: text/event-stream，即SSE；
     * 2. 这次用log()代替doOnNext(System.out::println)来查看每个元素；
     * 3. 由于/times是一个无限流，这里取前10个，会导致流被取消；
     */
    @Test
    public void webClientTest3() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:" + port);
        webClient
                .get()
                .uri("/times")
                .accept(MediaType.TEXT_EVENT_STREAM)    // 1
                .retrieve()
                .bodyToFlux(String.class)
                .log()  // 2
                .take(10)   // 3
                .blockLast();
    }

}