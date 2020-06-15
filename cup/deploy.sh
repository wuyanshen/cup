#!/bin/bash

echo '获取当前工作分支:'
curr_branch=`git symbolic-ref --short -q HEAD`
echo '当前工作分支 => '${curr_branch}'\n'

echo '拉取最新代码'
git pull
echo '\n'

echo '打包'
mvn clean package -Dmaven.test.skip=true
echo '\n'

echo '将jar和Dockerfile上传到服务器'
ssh root@49.233.209.183 "cd /home; rm -rf cup-project; mkdir cup-project; cd cup-project; mkdir target"
scp Dockerfile root@49.233.209.183:/home/cup-project
cd ./target
scp cup-1.0.jar root@49.233.209.183:/home/cup-project/target
echo '\n'

ssh root@49.233.209.183 "

echo '删除旧的docker镜像';
docker rm -f cup
docker rmi cup:1.0;
echo '\n';

echo '构建docker镜像';
cd /home/cup-project;
docker ps;
docker build -t cup:1.0 .;
docker images ;
echo '\n';

echo '启动docker镜像';
docker run -d -p 3001:3001 --name cup cup:1.0
docker ps;
echo '\n';

echo '部署成功'
"





