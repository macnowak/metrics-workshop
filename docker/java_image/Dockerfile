FROM ubuntu:14.04

# Avoid ERROR: invoke-rc.d: policy-rc.d denied execution of start.
RUN		echo "#!/bin/sh\nexit 0" > /usr/sbin/policy-rc.d && \
# install `repository` tool - necessary to install oracle
		apt-get update && \
		apt-get install -y  software-properties-common && \
		add-apt-repository ppa:webupd8team/java && \
		apt-get update && \
# install oracle java
		echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
		apt-get install -y oracle-java8-set-default
