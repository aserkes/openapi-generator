#!/usr/bin/bash

set -e

chmod +x $HOME/.sdkman/bin/sdkman-init.sh
. $HOME/.sdkman/bin/sdkman-init.sh
sdk use java 11-jdk-oracle

cd ~/IdeaProjects/openapi-generator
mvn clean install -DskipTests=true -Dmaven.javadoc.skip=true

rm -r /home/aserkes/IdeaProjects/openapi-generator/samples/server/petstore/java-helidon-server/*

./bin/generate-samples.sh bin/configs/java-helidon-*

cd samples/server/petstore/java-helidon-server/se
mvn clean package

cd ../mp
mvn clean package
