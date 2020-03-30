# cup后台管理系统

## 技术栈

基于RBAC权限模型设计，前后端分离

- Spring Boot2.0
- Spring Security
- Mybatis-Plus 2.3.0
- Vue

|技术|版本|
|:---:|:---:|
|Spring Boot|2.1.10.RELEASE|
|Spring Security|2.1.10.RELEASE|
|Mybatis-Plus|2.3.0|
|Vue|2.6.11|
|Vue-cli|4.2.0|

## 目录结构说明

    |--db   数据库文件
    |--src  项目源文件

## 快速开始

1.初始化数据库
- 执行db/schema.sql
- 执行db/cup.sql

2.启动cup后台
- 启动CupApp.java

3.启动cup前端
- 安装依赖 
```bash
cd /cup-vue
npm install
```
- 启动 
```bash
npm run serve
```

4.访问页面 

http://localhost:9999
