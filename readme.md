# 微服务或者单体应用基础

### 1、groot-base-common

**该模块的主要功能是提供非web的公用工具类**

```text
OperatorInfo：当前登录人信息
SearchData：条件查询工具类；负责接收查询条件，经常配合Pageable一起用做分页条件查询
```

### 2、groot-base-log

**该模块的主要功能是提供日志收集功能**

~~~text
annotation：
    EnableAuditLog：日志收集注解；开启全局日志切面配置和交换机、队列配置
aspect：
    GlobalAuditLog：全局日志切面
        现在只支持将日志发送到RabbitMQ的消息队列中这一种方式
config：
    DirectRabbitConfig：direct类型交换机和绑定的队列配置        
~~~

### 3、groot-base-web

**该模块主要提供web服务基础配置功能**

~~~text
annotation：
    EnableBaseWeb：基础web配置注解，其中包括全局异常处理配置、WebMvc配置、ApplicationRunner配置、Swwagge2配置
bean：
    model：
        BaseModel：基础Model，主要是声明雪花算法id
    result：
        所有返回类
    config：
        ApplicationRunnerCustom：自定义应用启动信息；配置了当前声明Swagger地址
        GlobalExceptionHandler：全局异常处理
        LoginInterceptor：登录拦截器；条件加载，单体应用时启用
        LoginInterceptorConfigurer：登录拦截器配置类；服务配置文件声明groot.login-interceptor=true时启用                
        SearchDataArgumentResolver: 分页条件查询条件参数解析器；将get请求中所有以s_开头的参数都放在SearchData中
        SwaggerConfig：swagger配置
        WebMvcConfig：WebMvc配置
convertor：
    BaseConvertor：基础转换类；Model与DTO相互转换抽象类
exception：
    BusinessRuntimeException：业务运行时异常
generator：
    雪花算法，需要在服务器配置环境变量worker_id范围在0~1023
repository：
    BaseRepository：基础Repository；queryDsl和queryFactory初始化，分页条件有序、无须查询
util：
    ExcelUtil：Excel工具类；基于EasyPOI
    LoginUserInfoUtil：登录人工具类；获取当前登录人信息                     
~~~