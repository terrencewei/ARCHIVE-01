package com.terrencewei;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by terrencewei on 3/13/17.
 * <p>
 * 注解@SpringBootApplication: 包含@Configuration、@EnableAutoConfiguration、@ComponentScan
 * <p>
 * 用于SpringBoot主类, 会自动以该注解所在的文件的package当做root package, 自动扫描所有子package
 * <p>
 * 本例为: 自动扫描及include /src/main/java/com/terrencewei/Application.java所在的package com.terrencewei.*
 * <p>
 * 其他注解的使用场景:
 *
 * @Service用于标注业务层组件
 * @Controller用于标注控制层组件（如struts中的action）
 * @Repository用于标注数据访问组件，即DAO组件
 * @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。 <p>
 * 以上各注解只用于逻辑上分类, 实际使用没区别
 * <p>
 * 在需要注入的变量位置使用@Autowired即可
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // main()方法启动了一个HTTP服务器程序，这个程序默认监听8080端口，并将HTTP请求转发给我们的应用来处理
        // 此处读取application.properties中设置的端口server.port=8081
        SpringApplication.run(Application.class, args);
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------Spring Boot startup finished!----------------------------------------");
        System.out.println("");
        System.out.println("");
    }

}
