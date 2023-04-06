#!/usr/bin/env bash

# check mvn
[ -d mvnw ] && ([ -x mvnw ] || chmod 755 mvnw)
if [ $? -eq 0 ]; then
  ./mvnw --version >& /dev/null
  if [ $? -eq 0 ]; then
  MAVEN_BIN=./mvnw
  fi
fi

if [ -z "$MAVEN_BIN" -a -n "$MAVEN_HOME" -a -x "$MAVEN_HOME/bin/mvn" ]; then
  which mvn >& /dev/null || export PATH=$MAVEN_HOME/bin:$PATH
  MAVEN_BIN=mvn
fi

if [ -z "$MAVEN_BIN" ] && which mvn >& /dev/null; then
  MAVEN_BIN=$(which mvn)
fi

if [ -z "$MAVEN_BIN" ]; then
  echo "maven not found!"
  exit 1
fi

$MAVEN_BIN -version 2>&1 | awk '/Apache Maven/ {
    split($3, vv, ".");
    if (vv[1] >= 3 && vv[2] >= 2) {exit 0} else {exit 1}
}' >& /dev/null
if [ $? -ne 0 ]; then
  echo "maven version must newer than 3.2.0"
  exit 1
fi


PROFILE="DEBUG"
JVM_ARG=" -Xms1g -Xmx1g -Xss320K -XX:+UseConcMarkSweepGC -XX:+PrintGC -verbose:gc -Xloggc:log/gc.log
-XX:+PrintGCDetails -XX:+PrintGCDateStamps "

# 编译打包（skip test）
mvn clean package -Dmaven.test.skip=true

if [ $? -ne 0 ]; then
    echo "[WARNING] mvn package failed !!!"
fi

#执行jar包
java -jar user-center-app/target/user-center-app-0.0.1-SNAPSHOT.jar ${JVM_ARG} -Dspring.profiles.active=${PROFILE} >nohup.log 2>&1 &
