#### SpringBoot中如何自定义错误页面
> 引入模板如thymeleaf，然后配置使默认的错误页面不能生效，server.error.errorwhitelabel.enabled=false, 然后写一个实现了ErrorController的Controller，具体见[自定义错误页面](https://www.baeldung.com/spring-boot-custom-error-page)。
***
#### SpringBoot Thymeleaf
>controller方法返回普通字符串用来生成ModelAndView中的View，提供页面地址，数据来源于请求域，请求域中的数据都可以被模板解析，所以在拦截器使用转发forward时，可以为请求域中添加数据（request.setAttribute）来实现为模板提供数据。常见的在Controller中使用的有以下两种方式,
1.
```java
public String handler(Model model){
    model.addAttribute(key,value); //解析为ModelAndView中的Model，提供数据
    return "test"; //解析为ModelAndView中的Viewname，提供页面地址
    }
```
2.
```java
public String handler(ModelMap m /*ModelMap是Model的扩展){
    m.addAttribute(KEY,VALUE); //提供数据，解析为ModelAndView找那个的Moel
    return "viewName";
}
```
>第二种方式返回ModelAndView
```java
public ModelAndView hanlder(){
    ModelAndView mv=new ModelAndView();
    mv.setViewName(viewName);
    mv.addObject(obj);
    return mv;
}
```
>响应中包含中文字符串时，除了在reponse中要设置characterEncoding为UTF-8之外，还要设置contet-type，如text/html,这样浏览器会将接收到的字符串正确解析，否则只设置编码方式，浏览器不能正确会把它当做纯文本来解析，遇到ASCNI外的字符会乱码。  
>thymeleaf绑定java中的变量与html数据，需要通过th:text='${变量名}'的方式，不能直接在html标签中使用${}。
>***
#### SpringBoot 异常处理
>   当没有ExceptionHandler能处理异常时，SpringBoot会发起/eror请求，而底层已经配置好了BasicErrorController处理异常，BasicErrorControler的处理逻辑如下
```java
  //注意默认的BasicErrorController已经配置好了错误信息
  @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections
				.unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value()); //数据包含了path,statuscode,message,errors,timestamp等
		ModelAndView modelAndView = resolveErrorView(request, response, status, model); /*如果在资源目录下  
		配置好了error目录下的400或者500等html，  并且错误状态能与error目录下的文件向对应，这里用DefaultErrorViewResolver解析  
		那么modelAndView不为空，响应返回400,500页面，否则为空*/
		return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);//如果前面构造的modleAndView为空  
		//则返回new ModelAndView("error", model),这会用BeanNameViewResolver来解析View(前提static目录下没有error.html,否则不一定能够是BeanNameViewResolver解决)，因为容器配置好了一个View组件，name为error,这  
		//个view视图不是从文件中读取，而是实时构造
	}
```
#### SpringBoot返回值处理器的选择
>HandlerMethodReturnValueHandlerComposite的selectHandler方法选择返回值处理器
***
>DispatcherServlet的resolverViewName和render方法用于选择viewResolver和渲染页面
* 当没有自定义ErrorController时,并且路径下没有error.html时, ContentNegotiatingViewResolver有四个viewResolver，包含了BeanNameViewResolver，此时选择的是BeanNameViewResolver。
* 如果路径下有error.html，ContentNegotiationResolver只有三个viewResolver，此时选择的是ThymeleafViewResolver
>InvocableHandlerMethod的getMethodsArgumentValues解决handler方法中所有的参数值,HandlerMethodArgumentResolverComposite中的getArgumentResolver选择具体的参数解析器  
>比较不同的错误处理方式
>* 自定义ErrController，可以像平常的errorController一样，返回多种数据类型，包括html，json等，似乎会拦截所有的异常（如果没有ExceptionHandler处理异常，在解决Excpetion时会抛出异常，然后springboot似乎会自己sendError），只有调用了sendError才会由这个controller处理，调用sendError的由404错误，由DefaultHandlerExceptionResolver，ResponseStatusExceptionResolver 处理的异常，以及自己调用的sendError方法。
>* 而ExceptionHandler+ControllerAdvice只能解决Handler中出现异常而引起的错误，不包括404等错误，先于ErrorController处理，(不加ResponseBody注解)只能返回模板，加了@ResponseBody注解可以返回json，但是可以拥有更精细的对错误的控制，不会覆盖所有的错误处理
>* 当发生异常时，调用response.sendError处理异常，然后调用ErrorController进行处理
>
>选择异常解析器这一步在DispatcherServlet类的processDispatchResult中的processHandlerException中
***  
#### Springboot单元测试
>需要引入spring-boot-starter-test依赖，注意测试类不能与主类同名
#### 注意事项
>1. controller方法没有ResponseBody，返回值会被解析为模板视图，要想要返回值为其他类型，需要加ResponseBody注解，这对于ExceptionHandler也一样，如果加了ResponseBody注解可以返回body数据，否则方法返回值会当成模板来解析
>2. springboot常用配置
```yaml
#配置spring-actuator
management:
  endpoints:
    web:
      exposure:
        include: "*"
        exclude: "shutdown"
        
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

   # 用于druid监控数据库的配置
    druid:
      aop-patterns: com.ternerwill.*  #监控SpringBean
      filters: stat,wall     # 底层开启功能，stat（sql监控），wall（防火墙）

      stat-view-servlet: # 配置监控页功能
        enabled: true
        login-username: root
        login-password: root
        resetEnable: true

      web-stat-filter: # 监控web
        enabled: true
        urlPattern: /*
        exclusions: '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*'


      filter:
        stat: # 对上面filters里面的stat的详细配置
          slow-sql-millis: 1000
          logSlowSql: true
          enabled: true
        wall:
          enabled: true
  #配置spring-boot-admin用于监控springboot 应用
  boot:
    admin:
      client:
        url: http://localhost:8888
        instance:
          name: firstSpringApplication
          prefer-ip: true
```
>3. IOC注意事项
> * @Autowired不一定是立即注入，例如使用@Autowired注入类的字段，有可能不在对象构造时期注入，看如下例子，在如下例子中，对象的生成顺序为

>MyConfig ---> service（此时service中的myproperties为null，@Autowired未生效）---> 执行myService的其他代码，并返回 ---> myproperties的构造 ---> 再将构造的myproperites注入到service中
> * 所以在myService方法中，设置myProperties没有用，最后还是会被覆盖
```java
@EnableConfigurationProperties(MyProperties.class)
@Configuration
public class MyConfig {
    public MyConfig(){
        System.out.println("调用了MyConfig的无参构造方法");
    }
    @Bean
    public MyService myService() throws InterruptedException {

        MyService service=new MyService();

        System.out.println(service);
        return service;
    }

}
```
