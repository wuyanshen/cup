FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wuyanshen

RUN mkdir -p /cup

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

WORKDIR /cup

ADD ./target/cup.jar ./cup.jar

EXPOSE 3001

CMD java -jar -Xms128m -Xmx256m cup.jar
