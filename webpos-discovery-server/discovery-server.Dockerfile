# 使用 Maven 官方镜像作为基础镜像
FROM maven:latest AS build

# 设置工作目录
WORKDIR /app

# 复制 Maven 项目的 pom.xml 文件
COPY pom.xml .

# 下载 Maven 依赖并缓存，确保只有在 pom.xml 文件更改时才重新下载依赖
RUN mvn dependency:go-offline

# 复制整个项目到工作目录
COPY ./src ./src

# 构建项目
RUN mvn package -DskipTests

# 使用 OpenJDK 官方镜像作为基础镜像
FROM openjdk:17-slim

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

# 将构建后的 JAR 文件复制到容器中
COPY --from=build /app/target/webpos-discovery-server-0.0.1-SNAPSHOT.jar /app/webpos-discovery-server-0.0.1-SNAPSHOT.jar

# 设置容器启动命令
CMD /wait && java -jar /app/webpos-discovery-server-0.0.1-SNAPSHOT.jar