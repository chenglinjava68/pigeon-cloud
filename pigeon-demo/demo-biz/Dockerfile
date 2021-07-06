FROM registry.cn-beijing.aliyuncs.com/yiidii-hub/jre8:v1

MAINTAINER wangyiidii <wangyiidii@163.com>

# ARGs
ARG JVM_XMS
ARG JVM_XMX
ARG NACOS_CONFIG_SERVER_ADDR
ARG NACOS_CONFIG_NAMESPACE
ARG NACOS_DISCOVERY_SERVER_ADDR
ARG NACOS_DISCOVERY_NAMESPACE

ADD target/demo-biz.jar /usr/local/app/app.jar
RUN echo "java -Xms${JVM_XMS} -Xmx${JVM_XMX} -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF8 -Dsun.jnu.encoding=UTF8 -jar /usr/local/app/app.jar --server.servlet.encoding.charset=utf-8 --server.servlet.encoding.force=true --server.servlet.encoding.enabled=true --spring.cloud.nacos.config.server-addr=${NACOS_CONFIG_SERVER_ADDR} --spring.cloud.nacos.config.namespace=${NACOS_CONFIG_NAMESPACE} --spring.cloud.nacos.discovery.server-addr=${NACOS_DISCOVERY_SERVER_ADDR} --spring.cloud.nacos.discovery.namespace=${NACOS_DISCOVERY_NAMESPACE}" >> /opt/run.sh
RUN chmod 777 run.sh
ENV LANGUAGE=zh_CN.UTF-8  TZ=Asia/Shanghai

ENTRYPOINT ["sh",  "run.sh"]