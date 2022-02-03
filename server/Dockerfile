#
# Build cache
#
ARG IMAGE_URI
FROM ${IMAGE_URI}:cache AS cache
#
# Package stage
#
FROM 728647365976.dkr.ecr.us-east-2.amazonaws.com/virtus_way-openjdk

ARG MODULE
ARG PORT
ARG TZ
ENV MODULE $MODULE

RUN apt-get update && apt install -y curl jq

COPY --from=cache /virtus-way/$MODULE/target/$MODULE.jar /usr/local/lib/virtus-way/$MODULE.jar
COPY --from=cache /virtus-way/${MODULE}/wso2/${MODULE}_swagger.yaml /usr/local/lib/virtus-way/swagger.yaml
COPY --from=cache /virtus-way/publisher-api-authentication-payload.json /usr/local/lib/virtus-way/
COPY --from=cache /virtus-way/publisher-api-import-api-payload.json /usr/local/lib/virtus-way/
COPY --from=cache /virtus-way/publisher-api-update.sh /usr/local/lib/virtus-way/
RUN chown root /usr/local/lib/virtus-way/publisher-api-update.sh
RUN chmod +x /usr/local/lib/virtus-way/publisher-api-update.sh

COPY ./common/src/main/resources/wso2carbon.jks /usr/local/lib/tmp/wso2carbon.jks

RUN sed -i -r "s/%MODULE%/${MODULE}/g" /usr/local/lib/virtus-way/publisher-api-update.sh
RUN sed -i -r "s/%WSO2_CALLBACK_URL%/${WSO2_CALLBACK_URL}/g" /usr/local/lib/virtus-way/publisher-api-authentication-payload.json
RUN sed -i -r "s/%MODULE%/${MODULE}/g" /usr/local/lib/virtus-way/publisher-api-import-api-payload.json
RUN sed -i -r "s/%PORT%/${PORT}/g" /usr/local/lib/virtus-way/publisher-api-import-api-payload.json
RUN sh -c "touch /usr/local/lib/virtus-way/${MODULE}.jar"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE $PORT
CMD /usr/local/lib/virtus-way/publisher-api-update.sh && java -jar /usr/local/lib/virtus-way/${MODULE}.jar --spring.profiles.active=${BUILD_ENV}