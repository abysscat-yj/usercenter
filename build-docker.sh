#!/usr/bin/env bash

PROFILE="DEBUG"

# 编译打包（skip test）
mvn clean package -Dmaven.test.skip=true

if [ $? -ne 0 ]; then
    echo "[WARNING] mvn package failed !!!"
fi

OUT_PUT_DIR="usercenter"

if [ "${OUT_PUT_DIR:0:1}" != "/" ]; then
    OUT_PUT_DIR= "`pwd`/$OUT_PUT_DIR"
    mkdir -p $OUT_PUT_DIR
fi

cp ./user-center-app/target/user-center-app-0.0.1-SNAPSHOT.jar ${OUT_PUT_DIR}
cp ./Dockerfile  ${OUT_PUT_DIR}
cd $OUT_PUT_DIR

echo "========== 生成镜像 =========="
docker build . -t user-center:v0.1

echo "========== 启动应用 =========="
docker run -d -p 8001:8001 --name user-center user-center:v0.1