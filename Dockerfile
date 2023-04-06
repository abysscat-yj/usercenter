# 该镜像需要依赖的JDK基础镜像
FROM openjdk:8

# 指定维护者名称
MAINTAINER abysscat yuanjie397@163.com

# 将targer目录下的jar包复制到docker容器/home/springboot目录下面目录下面
ADD user-center-app/target/user-center-app-0.0.1-SNAPSHOT.jar /home/springboot/user-center-app-0.0.1-SNAPSHOT.jar

# 声明服务运行在8080端口
EXPOSE 8080
# 执行命令
CMD ["java","-jar", "-Xms1g","-Xmx1g", "-Xss320K", "-XX:+UseConcMarkSweepGC", "-XX:+PrintGC", "-verbose:gc",
"-Xloggc:log/gc.log", "-XX:+PrintGCDetails", "-XX:+PrintGCDateStamps", "/home/springboot/core242-0.0.1-SNAPSHOT.jar"]