#!/bin/bash

echo '获取当前工作分支:'
curr_branch=`git symbolic-ref --short -q HEAD`
echo '当前工作分支 => '${curr_branch}'\n'


echo '获取环境名'
env='prod'

if [ x$1 != x ];then
    env=$1
else
    env='prod'
fi
echo '\n'


echo '读取配置文件'
deploy_host=`sed '/^'${env}_host'=/!d;s/.*=//' deploy.conf`
deploy_user=`sed '/^'${env}_user'=/!d;s/.*=//' deploy.conf`
deploy_path=`sed '/^'${env}_path'=/!d;s/.*=//' deploy.conf`
deploy_project_name=`sed '/^'${env}_name'=/!d;s/.*=//' deploy.conf`
echo '环境 => '${env}
echo '地址 => '${deploy_host}
echo '用户 => '${deploy_user}
echo '路径 => '${deploy_path}
echo '项目名 => '${deploy_project_name}
echo '\n'


echo '删除本地dist'
rm -rf ${deploy_project_name}
echo '\n'


echo '编译项目'
npm run build
echo '\n'


echo '删除旧版本'
ssh ${deploy_user}@${deploy_host} "rm -rf "${deploy_path}"/"${deploy_project_name}
echo '\n'


echo '上传新版本'
scp -r ${deploy_project_name} ${deploy_user}@${deploy_host}:${deploy_path}
echo '\n'


echo '部署成功'
