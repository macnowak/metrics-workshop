FROM java_image
MAINTAINER Maciej Nowak

RUN useradd -d /home/esuser -m esuser
WORKDIR /home/esuser

ENV DEBIAN_FRONTEND noninteractive
ENV ELASTIC_VER=5.6.3

RUN mkdir /data

RUN \
 wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-${ELASTIC_VER}.tar.gz && \
 tar xvzf elasticsearch-${ELASTIC_VER}.tar.gz && \
 rm -f elasticsearch-${ELASTIC_VER}.tar.gz && \
 mv elasticsearch-${ELASTIC_VER} elasticsearch && \
 chown -R esuser:esuser elasticsearch

# elasticsearch.yml and Dockerfile are on same location
ADD elasticsearch.yml /home/esuser/elasticsearch/config/elasticsearch.yml

#RUN elasticsearch/bin/elasticsearch-plugin install x-pack

ENTRYPOINT elasticsearch/bin/elasticsearch