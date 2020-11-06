#!/bin/bash

printf '获取当前工作分支:\n'
curr_branch=$(git symbolic-ref --short -q HEAD)
printf "当前工作分支 => %s\n" "${curr_branch}"
printf '拉取最新代码\n'
git pull
printf '\n'

printf '打包\n'
mvn clean package -Dmaven.test.skip=true
printf '\n'


printf '获取环境名\n'
env=''

if [ x"$1" != x ];then
    env=$1
    printf 'sh deploy.sh的运行参数不为空，参数的值是：%s\n'"${env}"
else
    env='prod'
    printf 'sh deploy.sh的运行参数为空\n'
fi
printf '\n'


printf '读取配置文件\n'
deploy_host=$(sed '/^'${env}_host'=/!d;s/.*=//' deploy.conf)
deploy_user=$(sed '/^'${env}_user'=/!d;s/.*=//' deploy.conf)
deploy_path=$(sed '/^'${env}_path'=/!d;s/.*=//' deploy.conf)
deploy_project_name=$(sed '/^'${env}_name'=/!d;s/.*=//' deploy.conf)


printf '将jar和Dockerfile上传到服务器\n'
ssh "${deploy_user}"@"${deploy_host}" "cd /home; rm -rf cup-project; mkdir cup-project; cd cup-project; mkdir target"
scp Dockerfile "${deploy_user}"@"${deploy_host}":"${deploy_path}"
cd ./target || exit
scp "${deploy_project_name}" "${deploy_user}"@"${deploy_host}":"${deploy_path}"/target
printf '\n'

ssh "${deploy_user}"@"${deploy_host}" "

printf '删除旧的docker镜像\n';
docker rm -f cup
docker rmi cup;
printf '\n';

printf '构建docker镜像\n';
cd ${deploy_path};
docker ps;
docker build -t cup .;
docker images ;
printf '\n';

printf '启动docker镜像\n';
docker run -d -p 3001:3001 --name cup cup
docker ps;
printf '\n';

printf '部署成功\n'
"







