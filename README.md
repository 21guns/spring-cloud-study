# 项目简介
本项目是《使用Spring Cloud与Docker实战微服务》：

[http://git.oschina.net/itmuch/spring-cloud-book](http://git.oschina.net/itmuch/spring-cloud-book)

[http://www.github.com/eacdy/spring-cloud-book](http://www.github.com/eacdy/spring-cloud-book)

Spring Cloud章节的配套代码，如有疑问，请移步至该地址。

微服务架构交流QQ群：157525002（已满），**564840207**，欢迎加入。

微服务架构讨论社区：[http://ask.itmuch.com/](http://ask.itmuch.com/)，欢迎加入。



内容主要包含：

| 微服务角色                 | 对应的技术选型                              |
| --------------------- | ------------------------------------ |
| 注册中心(Register Server) | Eureka                               |
| 服务提供者                 | spring mvc、spring-data-jpa、h2等       |
| 服务消费者                 | Ribbon/Feign消费服务提供者的接口               |
| 熔断器                   | Hystrix，包括Hystrix Dashboard以及Turbine |
| 配置服务                  | Spring Cloud Config Server           |
| API Gateway           | Zuul                                 |



# 准备

## 环境准备：

| 工具    | 版本或描述                |
| ----- | -------------------- |
| JDK   | 1.8                  |
| IDE   | STS 或者 IntelliJ IDEA |
| Maven | 3.x                  |

## 主机名配置：

| 主机名配置（C:\Windows\System32\drivers\etc\hosts文件） |
| ---------------------------------------- |
| 127.0.0.1 discovery config-server gateway movie user feign ribbon |

## 主机规划：

| 项目名称                                     | 端口   | 描述                     | URL             |
| ---------------------------------------- | ---- | ---------------------- | --------------- |
| microservice-api-gateway                 | 8050 | API Gateway            | 详见文章            |
| microservice-config-client               | 8041 | 配置服务的客户端               | 详见文章            |
| microservice-config-server               | 8040 | 配置服务                   | 详见文章            |
| microservice-consumer-movie-feign        | 8020 | Feign Demo             | /feign/1        |
| microservice-consumer-movie-feign-with-hystrix | 8021 | Feign Hystrix Demo     | /feign/1        |
| microservice-consumer-movie-feign-with-hystrix-stream | 8022 | Hystrix Dashboard Demo | /feign/1        |
| microservice-consumer-movie-ribbon       | 8010 | Ribbon Demo            | /ribbon/1       |
| microservice-consumer-movie-ribbon-with-hystrix | 8011 | Ribbon Hystrix Demo    | /ribbon/1       |
| microservice-discovery-eureka            | 8761 | 注册中心                   | /               |
| microservice-hystrix-dashboard           | 8030 | hystrix监控              | /hystrix.stream |
| microservice-hystrix-turbine             | 8031 | turbine                | /turbine.stream |
| microservice-provider-user               | 8000 | 服务提供者                  | /1              |
|                                          |      |                        |                 |


## 注册中心
1. eureka
    启动该spring boot项目
2. consul
    启动consul可以使用docker启动，如下命令启动Development模式consul
    `ocker run --name consul -p 8500:8500 -d consul`

## 熔断监控中心
hystrix.dashboard
- 使用rabbitmq，docker安装 
   * docker run -d --hostname rabbit --name web-rabbit -p 9191:15672 -p 5672:5672 -e RABBITMQ_ERLANG_COOKIE='123456' rabbitmq:3-management 

## 降级、限流、滚动、灰度、AB、金丝雀等等等等 [1](http://xujin.org/sc/sc-ribbon-demoted/) [2](http://www.jianshu.com/p/37ee1e84900a)
### eureka
 - 修改metadata
    http://localhost:8761/eureka/apps/MICROSERVICE-PROVIDER-USER/68c08d456a26:microservice-provider-user:8081/metadata?weight=200
### consul
 - 修改metadata
 
   目前consul service不支持metadata，使用tags进行设置，要想修改tags需要设置EnableTagOverride为true，目前spring cloud consul未提供EnableTagOverride的配置方式：
   * 1.使用如下命令将EnableTagOverride设置true
   
    curl \
    --request PUT \
    --data '{
        "ID": "microservice-provider-user-docker-8081",
         "Name": "microservice-provider-user",
		  "Tags": [
		    "weight=55"
		  ],
		 "Address": "192.168.2.177",
		  "Port": 8081,
		  "EnableTagOverride": true

		}' \
    http://localhost:8500/v1/agent/service/register
    * 2.更行tags
    https://www.consul.io/api/catalog.html#catalog_register
    
    json文件`{
        "ID": "e5624ba1-7aaa-d035-45b9-1e2aee06c2b6",
         "Node": "d9b621972724",
         "Address": "127.0.0.1",
         "Datacenter": "dc1",
         "TaggedAddresses": {
             "lan": "127.0.0.1",
             "wan": "127.0.0.1"
         },
         "NodeMeta": {"somekey": "somevalue"},
         "Service": {
           "ID": "microservice-provider-user-docker-8081",
           "Service": "microservice-provider-user",
           "Tags": [
               "weight=2"
           ],
           "EnableTagOverride": true, #可以覆盖tag
           "Address": "192.168.2.177",
           "Port": 8081
         },
         "Check": {
           "Node": "d9b621972724",
           "CheckID": "service:microservice-provider-user-docker-8081",
           "Name": "Redis health check",
           "Notes": "Script based health check",
           "Status": "passing",
           "ServiceID": "microservice-provider-user-docker-8081"
         }
       }`
       
    执行命令：
        `curl \
              --request PUT \
              --data @payload.json \
              http://localhost:8500/v1/catalog/register`
              
## 使用sleuth zipkin [spring cloud sleuth](http://tech.lede.com/2017/04/19/rd/server/SpringCloudSleuth/)
- 仅仅使用sleuth+log配置
- sleuth+zipkin+http
   * docker run -d -p 9411:9411 openzipkin/zipkin
- sletuh+streaming+zipkin

## docker 
### docker  compose
- mvn clean package && docker-compose  build && docker-compose  up
- mvn clean package && docker-compose -f docker-compose-consul.yml build && docker-compose -f docker-compose-consul.yml up
