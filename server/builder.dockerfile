#
# Build stage
#
FROM 728647365976.dkr.ecr.us-east-2.amazonaws.com/virtus_way-maven AS builder

ARG MODULE

# Copying pom.xml for dependency resolution
COPY ./pom.xml /virtus-way/
COPY ./common/pom.xml /virtus-way/common/
COPY ./${MODULE}/pom.xml /virtus-way/${MODULE}/

# Removing unnecessary modules to the verify step from the pom.xml
RUN sed -r -i "/<module>(common|${MODULE})<\/module>/b; /<module>.*<\/module>/d" /virtus-way/pom.xml

ENV MAVEN_OPTS="-Dhttps.protocols=TLSv1.2 -Dmaven.repo.local=./m2_repo -Dorg.slf4j.simpleLogger.showDateTime=true -Djava.awt.headless=true"
ENV MAVEN_CLI_OPTS="--batch-mode --errors --fail-at-end --show-version"

RUN mvn $MAVEN_CLI_OPTS -f /virtus-way/pom.xml -DexcludeGroupIds=br.edu.ufcg.virtus dependency:go-offline

# This will override all the pom.xml with the original values
COPY . /virtus-way

RUN mvn -pl $MODULE -am $MAVEN_CLI_OPTS -f /virtus-way/pom.xml -Dinclude=br.edu.ufcg.virtus clean package -DskipTests