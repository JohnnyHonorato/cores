# ------------------------------------------------------------------------
#
# Copyright 2020 WSO2, Inc. (http://wso2.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License
#
# ------------------------------------------------------------------------

# set base Docker image to WSO2 API Manager Docker image with latest WSO2 Updates
FROM 728647365976.dkr.ecr.us-east-2.amazonaws.com/virtus-way-wso2am:3.2.0-ubuntu
LABEL maintainer="WSO2 Docker Maintainers <dev@wso2.org>"

# build arguments for external artifacts
ARG MYSQL_CONNECTOR_VERSION=8.0.17
ARG BUILD_ENV
ARG LDAP_USERNAME
ARG LDAP_PASSWORD
ENV DOMAIN_NAME=$DOMAIN_NAME

COPY ./resources/authenticationendpoint/ /home/wso2carbon/wso2am-3.2.0/repository/deployment/server/webapps/authenticationendpoint/

COPY --chown=wso2carbon:wso2 ./configs/Virtus.xml /home/wso2carbon/wso2am-3.2.0/repository/deployment/server/userstores/
RUN sed -i -r "s/LDAP_USERNAME/${LDAP_USERNAME}/g" /home/wso2carbon/wso2am-3.2.0/repository/deployment/server/userstores/Virtus.xml
RUN sed -i -r "s/LDAP_PASSWORD/${LDAP_PASSWORD}/g" /home/wso2carbon/wso2am-3.2.0/repository/deployment/server/userstores/Virtus.xml

COPY ./configs/External${BUILD_ENV:+.${BUILD_ENV}}.xml /home/wso2carbon/wso2am-3.2.0/repository/deployment/server/userstores/External.xml

# add MySQL JDBC connector to server home as a third party library
ADD --chown=wso2carbon:wso2 https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_CONNECTOR_VERSION}/mysql-connector-java-${MYSQL_CONNECTOR_VERSION}.jar ${WSO2_SERVER_HOME}/repository/components/dropins/

# add custom user store manager jar file
ADD --chown=wso2carbon:wso2 ./resources/db-plugins/org.wso2.custom.user.store.manager-1.0.jar ${WSO2_SERVER_HOME}/repository/components/dropins/

# add postgres driver
ADD --chown=wso2carbon:wso2 ./resources/db-plugins/postgresql-42.2.16.jar ${WSO2_SERVER_HOME}/repository/components/lib/
