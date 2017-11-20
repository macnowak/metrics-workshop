FROM java_image
MAINTAINER Maciej Nowak

RUN useradd -d /home/esuser -m esuser
WORKDIR /home/esuser

ENV DEBIAN_FRONTEND noninteractive

ENV LOGSTASH_VER=5.6.3

RUN \
  wget https://artifacts.elastic.co/downloads/logstash/logstash-${LOGSTASH_VER}.tar.gz && \
  tar xvzf logstash-${LOGSTASH_VER}.tar.gz && \
  rm -f logstash-${LOGSTASH_VER}.tar.gz && \
  chown -R esuser:esuser logstash-${LOGSTASH_VER}

RUN mkdir /etc/logstash/
RUN mkdir /etc/logstash/conf.d/

# logstash.conf and Dockerfile are on same location
ADD 01-input.conf /etc/logstash/conf.d/
ADD 11-filter.conf /etc/logstash/conf.d/
ADD 21-output.conf /etc/logstash/conf.d/

CMD logstash-${LOGSTASH_VER}/bin/logstash -f /etc/logstash/conf.d/
