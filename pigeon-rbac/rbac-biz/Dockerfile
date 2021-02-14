FROM registry.cn-beijing.aliyuncs.com/yiidii-hub/jre8:v1

MAINTAINER wangyiidii <wangyiidii@163.com>

# VOLUME logs

ADD target/rbac-biz.jar /usr/local/app/app.jar

ENV LANGUAGE=zh_CN.UTF-8  TZ=Asia/Shanghai

ENTRYPOINT ["java",  "-Xms128m", "-Xmx256m", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF8", "-Dsun.jnu.encoding=UTF8", "-jar","/usr/local/app/app.jar", "--server.servlet.encoding.charset=utf-8", "--server.servlet.encoding.force=true", "--server.servlet.encoding.enabled=true","--spring.cloud.nacos.config.server-addr=172.17.0.1:8848", "--spring.cloud.nacos.config.namespace=pigeon-cloud-prod", "--spring.cloud.nacos.discovery.server-addr=172.17.0.1:8848","--spring.cloud.nacos.discovery.namespace=pigeon-cloud-prod"]
