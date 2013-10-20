!/bin/bash
clear

echo "*******************  COLLECTING DEPENDENCIES  *********************************"
mvn dependency:copy-dependencies
export CLASPATH=""
for file in `ls target/dependency`; do export CLASSPATH=$CLASSPATH:target/dependency/$file; done
export CLASSPATH=$CLASSPATH:target/classes

mvn package embedded-glassfish:run
