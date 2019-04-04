package com.terrencewei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.terrencewei.model.Blog;
import com.terrencewei.service.BlogService;

/**
 * @Controller标注表示Application类是一个处理HTTP请求的控制器
 */
@Controller
public class URLController {

    @Autowired
    BlogService blogService;



    /**
     * spring的controller类可以返回任何类型的数据,只是添加@ResponseBody注解后,会自动将这些类型转换为string类型的json字符串
     * 
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/blog")
    @ResponseBody
    public Page<Blog> handleBlogList(
            @PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        return blogService.findBlogs(pageable);
    }

    /* this is for hello world */
    // /**
    // * @Controller下该类中所有被@RequestMapping标注的方法都会用来处理对应URL的请求
    // index()方法上包含@RequestMapping("/")标注，意味着在浏览器中访问http://localhost:8080/（不考虑协议、host和port信息后的路径为"/"）后浏览器发送的请求会交给该方法进行处理
    // * @ResponseBody标注告诉Spring MVC直接将字符串作为Web响应（Reponse
    // * Body）返回，如果@ResponseBody标注的方法返回一个对象，则会自动将该对象转换为JSON字符串返回
    // */
    // @ResponseBody
    // @GetMapping("/hello")
    // public String handleHello() {
    // return "<html><head><title>Hello World!</title></head><body><h1>Hello
    // World!</h1><p>This is my first web site</p></body></html>";
    // }
    /* /this is for hello world */

    /* this is for thymeleaf html render */
    // @GetMapping("/blog")
    // public String handleBlogList(
    // @PageableDefault(value = 15, sort = { "id" }, direction =
    // Sort.Direction.DESC) Pageable pageable,
    // Model model) {
    // model.addAttribute("blogs", blogService.findBlogs(pageable));
    // return "blogList-thymeleaf";
    // }
    //
    //
    //
    // @GetMapping("/blog/reset")
    // @ResponseBody
    // public boolean handleBlogList() {
    // return blogService.resetBlogData();
    // }
    //
    //
    //
    // @GetMapping("/blog/{id}")
    // public String handleBlogDetail(@PathVariable Long id, Model model) {
    // // 往Model中添加对象有两种方式：
    // // model.addAttribute("blog", blog);
    // // model.addAttribute(blog);
    // // 使用第二种时，对象在Model中的命名默认为类名的首字母小写形式，任何时候对于同一种类型，只可能存在一个这样的“匿名”对象
    // model.addAttribute(new Blog(id, "this is title", "this is content", new
    // Date()));
    // return "blogDetail-thymeleaf";
    // }
    //
    //
    //
    // /**
    // *
    // [a-z0-9_]+是一个正则表达式，表示只能包含小写字母、数字和下划线。如此设置URL变量规则后，不合法的URL则不会被处理，直接由Spring
    // * MVC框架返回404 Not Found。
    // *
    // * @param userName
    // * @param topicId
    // * @return
    // */
    // @ResponseBody
    // @GetMapping("/users/{userName:[a-z0-9_]+}/topics/{topicId:[0-9]+}")
    // public String handleUserTopics(@PathVariable String userName,
    // @PathVariable String topicId,
    // @RequestParam(name = "pageIndex", required = false, defaultValue = "1")
    // int pageIndex,
    // @RequestParam(name = "pageSize", required = false, defaultValue = "10")
    // int pageSize,
    // @RequestParam(name = "sortBy", required = false, defaultValue =
    // "lastModifiedDate") String sortBy,
    // @RequestParam(name = "sortType", required = false, defaultValue =
    // "false") boolean sortType,
    // @RequestParam(name = "searchBy", required = false, defaultValue =
    // "topicName") String searchBy,
    // @RequestParam(name = "searchTerm", required = false, defaultValue = "*")
    // String searchTerm) {
    // return "this is user(" + userName + ")'s topic id:" + topicId;
    // }
    /* /this is for thymeleaf html render */

    /* this is for jsp render */
    // // 从 application.properties 中读取配置，如取不到默认值为HelloShanhy
    // @Value("${application.hello:Hello Angel}")
    // private String hello;
    //
    //
    //
    // @RequestMapping("/helloJsp")
    // public String handleHelloJsp(Map<String, Object> map) {
    // map.put("hello", hello);
    // return "helloJsp";
    //
    // }
    /* /this is for jsp render */

}