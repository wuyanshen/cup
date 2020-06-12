#!/bin/bash

#echo '获取当前工作分支:'
#curr_branch=`git symbolic-ref --short -q HEAD`
#echo '当前工作分支 => '${curr_branch}'\n'
#
#echo '拉取最新代码'
#git pull
#echo '\n'
#
#echo '打包'
##mvn clean package -Dmaven.test.skip=true
#echo '\n'
#
#echo '将jar上传到服务器'
#cd ./target
#scp cup-1.0.jar root@49.233.209.183:/home
#echo '\n'

echo '拉取最新代码'
ssh root@49.233.209.183 "cd /home/cup; ls; git pull"
echo '\n'

echo '打包docker镜像'
ssh root@49.233.209.183 "docker ps"
echo '\n'




echo '部署成功'
