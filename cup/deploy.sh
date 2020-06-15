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


echo '获取环境名'
env=''

if [ x$1 != x ];then
    env=$1
    echo 'sh deploy.sh的运行参数不为空，参数的值是：'${env}
else
    env='prod'
    echo 'sh deploy.sh的运行参数为空'
fi
echo '\n'

echo '读取配置文件'
deploy_host=`sed '/^'${env}_host'=/!d;s/.*=//' deploy.conf`
deploy_user=`sed '/^'${env}_user'=/!d;s/.*=//' deploy.conf`
deploy_path=`sed '/^'${env}_path'=/!d;s/.*=//' deploy.conf`
deploy_project_name=`sed '/^'${env}_name'=/!d;s/.*=//' deploy.conf`


echo '将jar和Dockerfile上传到服务器'
ssh ${deploy_user}@${deploy_host} "cd /home; rm -rf cup-project; mkdir cup-project; cd cup-project; mkdir target"
scp Dockerfile ${deploy_user}@${deploy_host}:${deploy_path}
cd ./target
scp ${deploy_project_name} ${deploy_user}@${deploy_host}:${deploy_path}/target
echo '\n'

ssh ${deploy_user}@${deploy_host} "

echo '删除旧的docker镜像';
docker rm -f cup
docker rmi cup:1.0;
echo '\n';

echo '构建docker镜像';
cd ${deploy_path};
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







