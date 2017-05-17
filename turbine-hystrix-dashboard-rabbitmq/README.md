## 将turbine和hystrix-dashboard合并到一起
但是原生的Hystrix Dashboard依赖web的环境，而在我们一些场景下项目本身是没有web容器的，还好Spring Cloud提供了解决方案Spring Cloud Stream，让我们可以通过中间件将数据聚合展现到Hystrix Dashboard上面。

这里我采用RabbitMQ作为消息中间件传递Metric信息，在此之前Spring Cloud需要使用Netflix的Eureka来做服务发现（当然这个也是可选的）。
- 参看 http://www.jianshu.com/p/add65b345be7
- todo 目前使用rabbitmq 是否可以替换为kafka-