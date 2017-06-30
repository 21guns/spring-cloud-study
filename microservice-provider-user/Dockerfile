FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /var/data/upload
VOLUME /var/logs
#启动脚本
#ADD entrypoint.sh /usr/local/bin/entrypoint.sh
RUN sh -c 'touch /usr/local/bin/entrypoint.sh'
RUN echo $'#!/bin/sh \n\
    java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar $SETUP_JAR\n'\
    >>/usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh
#时区
ENV TZ Asia/Shanghai

#设置启动脚本里的变量
#jvm 参数
#ENV JAVA_OPTS "-server \
#               -Xms1000M \
#               -Xmx1000M \
#               -Xss512k \
#               -XX:+AggressiveOpts \
#               -XX:+UseBiasedLocking \
#               -XX:+DisableExplicitGC \
#               -XX:+UseConcMarkSweepGC \
#               -XX:+UseParNewGC \
#               -XX:+CMSParallelRemarkEnabled \
#               -XX:+UseCMSCompactAtFullCollection \
#               -XX:LargePageSizeInBytes=128m \
#               -XX:+UseFastAccessorMethods \
#               -XX:+UseCMSInitiatingOccupancyOnly \
#               -Djava.awt.headless=true"
#jar
ADD target/microservice-provider-user-0.0.1-SNAPSHOT.jar /var/microservice-provider-user.jar
ENV SETUP_JAR /var/microservice-provider-user.jar

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
#CMD ["-Xms1M -Xmx1M"]