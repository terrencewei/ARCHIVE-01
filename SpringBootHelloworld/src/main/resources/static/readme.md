http://blog.csdn.net/hengyunabc/article/details/50120001
spring boot应用启动原理分析
```
...
index.html

比如需要配置一个index.html，这个可以直接放在代码里的src/main/resources/static目录下。

对于index.html欢迎页，spring boot在初始化时，就会创建一个ViewController来处理：

//ResourceProperties
public class ResourceProperties implements ResourceLoaderAware {

    private static final String[] SERVLET_RESOURCE_LOCATIONS = { "/" };

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
1
2
3
4
5
6
7
8
9
1
2
3
4
5
6
7
8
9WebMvcAutoConfigurationAdapter
//WebMvcAutoConfigurationAdapter
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            Resource page = this.resourceProperties.getWelcomePage();
            if (page != null) {
                logger.info("Adding welcome page: " + page);
                registry.addViewController("/").setViewName("forward:index.html");
            }
        }
1
2
3
4
5
6
7
8
9
1
2
3
4
5
6
7
8
9
template

像页面模板文件可以放在src/main/resources/template目录下。但这个实际上是模板的实现类自己处理的。比如ThymeleafProperties类里的：

public static final String DEFAULT_PREFIX = "classpath:/templates/";
1
1
jsp

jsp页面和template类似。实际上是通过spring mvc内置的JstlView来处理的。

可以通过配置spring.view.prefix来设定jsp页面的目录：

spring.view.prefix: /WEB-INF/jsp/
1
1
spring boot里统一的错误页面的处理

对于错误页面，Spring boot也是通过创建一个BasicErrorController来统一处理的。

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController 
1
2
3
4
1
2
3
4
对应的View是一个简单的HTML提醒：

    @Configuration
    @ConditionalOnProperty(prefix = "server.error.whitelabel", name = "enabled", matchIfMissing = true)
    @Conditional(ErrorTemplateMissingCondition.class)
    protected static class WhitelabelErrorViewConfiguration {

        private final SpelView defaultErrorView = new SpelView(
                "<html><body><h1>Whitelabel Error Page</h1>"
                        + "<p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>"
                        + "<div id='created'>${timestamp}</div>"
                        + "<div>There was an unexpected error (type=${error}, status=${status}).</div>"
                        + "<div>${message}</div></body></html>");

        @Bean(name = "error")
        @ConditionalOnMissingBean(name = "error")
        public View defaultErrorView() {
            return this.defaultErrorView;
        }
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
spring boot的这个做法很好，避免了传统的web应用来出错时，默认抛出异常，容易泄密。
...

```

http://blog.csdn.net/isea533/article/details/50412212

静态资源处理
```
spring Boot 默认的处理方式就已经足够了，默认情况下Spring Boot 使用WebMvcAutoConfiguration中配置的各种属性。

建议使用Spring Boot 默认处理方式，需要自己配置的地方可以通过配置文件修改。

但是如果你想完全控制Spring MVC，你可以在@Configuration注解的配置类上增加@EnableWebMvc，增加该注解以后WebMvcAutoConfiguration中配置就不会生效，你需要自己来配置需要的每一项。这种情况下的配置方法建议参考WebMvcAutoConfiguration类。

本文以下内容针对Spring Boot 默认的处理方式，部分配置通过在application.yml配置文件中设置。

配置资源映射

Spring Boot 默认配置的/**映射到/static（或/public ，/resources，/META-INF/resources），/webjars/**会映射到classpath:/META-INF/resources/webjars/。

注意：上面的/static等目录都是在classpath:下面。

如果你想增加如/mystatic/**映射到classpath:/mystatic/，你可以让你的配置类继承WebMvcConfigurerAdapter，然后重写如下方法：

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/mystatic/**")
            .addResourceLocations("classpath:/mystatic/");
}
1
2
3
4
5
1
2
3
4
5
这种方式会在默认的基础上增加/mystatic/**映射到classpath:/mystatic/，不会影响默认的方式，可以同时使用。

静态资源映射还有一个配置选项，为了简单这里用.properties方式书写：

spring.mvc.static-path-pattern=/** # Path pattern used for static resources.
1
1
这个配置会影响默认的/**，例如修改为/static/**后，只能映射如/static/js/sample.js这样的请求（修改前是/js/sample.js）。这个配置只能写一个值，不像大多数可以配置多个用逗号隔开的。

使用注意

例如有如下目录结构：

└─resources
    │  application.yml
    │
    ├─static
    │  ├─css
    │  │      index.css
    │  │
    │  └─js
    │          index.js
    │
    └─templates
            index.ftl
1
2
3
4
5
6
7
8
9
10
11
12
1
2
3
4
5
6
7
8
9
10
11
12
在index.ftl中该如何引用上面的静态资源呢？ 
如下写法：

<link rel="stylesheet" type="text/css" href="/css/index.css">
<script type="text/javascript" src="/js/index.js"></script>
1
2
1
2
注意：默认配置的/**映射到/static（或/public ，/resources，/META-INF/resources）

当请求/css/index.css的时候，Spring MVC 会在/static/目录下面找到。

如果配置为/static/css/index.css，那么上面配置的几个目录下面都没有/static目录，因此会找不到资源文件！

所以写静态资源位置的时候，不要带上映射的目录名（如/static/，/public/ ，/resources/，/META-INF/resources/）！

使用WebJars

WebJars：http://www.webjars.org/

例如使用jQuery，添加依赖：

<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>1.11.3</version>
</dependency>
1
2
3
4
5
1
2
3
4
5
然后可以如下使用：

<script type="text/javascript" src="/webjars/jquery/1.11.3/jquery.js"></script>
1
1
你可能注意到href中的1.11.3版本号了，如果仅仅这么使用，那么当我们切换版本号的时候还要手动修改href，怪麻烦的，我们可以用如下方式解决。

先在pom.xml中添加依赖：

<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>webjars-locator</artifactId>
</dependency>
1
2
3
4
1
2
3
4
增加一个WebJarController：

@Controller
public class WebJarController {
    private final WebJarAssetLocator assetLocator = new WebJarAssetLocator();

    @ResponseBody
    @RequestMapping("/webjarslocator/{webjar}/**")
    public ResponseEntity locateWebjarAsset(@PathVariable String webjar, HttpServletRequest request) {
        try {
            String mvcPrefix = "/webjarslocator/" + webjar + "/";
            String mvcPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            String fullPath = assetLocator.getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));
            return new ResponseEntity(new ClassPathResource(fullPath), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
然后使用的时候按照如下方式：

<script type="text/javascript" src="/webjarslocator/jquery/jquery.js"></script>
1
1
注意：这里不需要在写版本号了，但是注意写url的时候，只是在原来url基础上去掉了版本号，其他的都不能少！

静态资源版本管理

Spring MVC 提供了静态资源版本映射的功能。

用途：当我们资源内容发生变化时，由于浏览器缓存，用户本地的静态资源还是旧的资源，为了防止这种情况导致的问题，我们可能会手动在请求url的时候加个版本号或者其他方式。

版本号如：

<script type="text/javascript" src="/js/sample.js?v=1.0.1"></script>
1
1
Spring MVC 提供的功能可以很容易的帮助我们解决类似问题。

Spring MVC 有两种解决方式。

注意：下面的配置方式针对freemarker模板方式，其他的配置方式可以参考。

资源名-md5 方式

例如：

<link rel="stylesheet" type="text/css" href="/css/index-2b371326aa93ce4b611853a309b69b29.css">
1
1
Spring 会自动读取资源md5，然后添加到index.css的名字后面，因此当资源内容发生变化的时候，文件名发生变化，就会更新本地资源。

配置方式：

在application.properties中做如下配置：

spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**
1
2
1
2
这样配置后，所有/**请求的静态资源都会被处理为上面例子的样子。

到这儿还没完，我们在写资源url的时候还要特殊处理。

首先增加如下配置：

@ControllerAdvice
public class ControllerConfig {

    @Autowired
    ResourceUrlProvider resourceUrlProvider;

    @ModelAttribute("urls")
    public ResourceUrlProvider urls() {
        return this.resourceUrlProvider;
    }

}
1
2
3
4
5
6
7
8
9
10
11
12
1
2
3
4
5
6
7
8
9
10
11
12
然后在页面写的时候用下面的写法：

<link rel="stylesheet" type="text/css" href="${urls.getForLookupPath('/css/index.css')}">
1
1
使用urls.getForLookupPath('/css/index.css')来得到处理后的资源名。

版本号 方式

在application.properties中做如下配置：

spring.resources.chain.strategy.fixed.enabled=true
spring.resources.chain.strategy.fixed.paths=/js/**,/v1.0.0/**
spring.resources.chain.strategy.fixed.version=v1.0.0
1
2
3
1
2
3
这里配置需要特别注意，将version的值配置在paths中。原因我们在讲Spring MVC 处理逻辑的时候说。

在页面写的时候，写法如下：

<script type="text/javascript" src="${urls.getForLookupPath('/js/index.js')}"></script>
1
1
注意，这里仍然使用了urls.getForLookupPath，urls配置方式见上一种方式。

在请求的实际页面中，会显示为：

<script type="text/javascript" src="/v1.0.0/js/index.js"></script>
1
1
可以看到这里的地址是/v1.0.0/js/index.js。

静态资源版本管理 处理过程

在Freemarker模板首先会调用urls.getForLookupPath方法，返回一个/v1.0.0/js/index.js或/css/index-2b371326aa93ce4b611853a309b69b29.css。

这时页面上的内容就是处理后的资源地址。

这之后浏览器发起请求。

这里分开说。

第一种md5方式

请求/css/index-2b371326aa93ce4b611853a309b69b29.css，我们md5配置的paths=/**，所以Spring MVC 会尝试url中是否包含-，如果包含会去掉后面这部分，然后去映射的目录（如/static/）查找/css/index.css文件，如果能找到就返回。

第二种版本方式

请求/v1.0.0/js/index.js。

如果我们paths中没有配置/v1.0.0，那么上面这个请求地址就不会按版本方式来处理，因此会找不到上面的资源。

如果配置了/v1.0.0，Spring 就会将/v1.0.0去掉再去找/js/index.js，最终会在/static/下面找到。

本文参考

http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
http://www.webjars.org/documentation
http://www.mscharhag.com/spring/resource-versioning-with-spring-mvc
https://spring.io/blog/2014/07/24/spring-framework-4-1-handling-static-web-resources
如果你使用的JSP或者其他模板，你可以参考上面几个链接的内容。

最后

以上是Spring Boot 静态资源处理的内容，有些不全面的地方或者读者有更多疑问，可以查看Spring Boot完整文档或本文参考的内容。

关于Spring Boot更多的内容可以继续关注本博客。

Spring Boot 系列

由于我博客Spring Boot 系列文章还不够多，所以暂时不打算创建专栏，如果再多几篇我就建专栏。

Spring Boot 入门

Spring Boot 属性配置和使用

Spring Boot 集成MyBatis

Spring Boot 静态资源处理
```