# SpringBoot+Websocket实现聊天室

## Websocket项目介绍文档

## 一、项目简介

github仓库链接：https://github.com/SmilingSea/im_project

本项目基于springboot与websocket实现了实时通讯的聊天室后台。

## 二、项目实现

### 2.1 项目结构

```SQL
└─im_project
    ├─annotation    // 自定义注解类
    ├─common        // 静态常量
    ├─config        // 配置类
    ├─controller    // 接口类
    ├─domain        // 实体类
    │  ├─dao        // 数据库实体
    │  ├─dto        // 数据传输实体
    │  └─enums      // 枚举类
    ├─exception     // 自定义异常类
    ├─filter        // 过滤器
    ├─handler       // 拦截器
    ├─mapper        // 数据库存储对象
    ├─mq            // 消息队列
    ├─service       // 服务层
    │  └─impl       // 服务实现类
    ├─socket        // socket配置
    └─utils         // 工具类
```

### 2.2 技术栈

springboot+mysql+redis+websocket+rabbitmq

### 2.3 项目完成情况

1. 完成了基本的模块和功能
2. 支持会话列表、文字图片交流、屏蔽用户功能

## 三、接口文档

参见:接口文档.md

## 四、如何部署

我的环境：Ubuntu:22.04 docker:24.0.7 docker-compose:v2.22.0

1. 下载Release

![1](https://github.com/SmilingSea/im_project/blob/master/assets/1.PNG)

2. 上传到服务器并解压

```Bash
sudo unzip im_project-1.0.zip
```

![](https://github.com/SmilingSea/im_project/blob/master/assets/2.PNG)

3. 创建该项目的镜像

```Bash
sudo docker build -t im_project:v1.0 .
```

![5f537eb3-7607-4acb-839b-10c53d1be938](https://github.com/SmilingSea/im_project/blob/master/assets/5f537eb3-7607-4acb-839b-10c53d1be938.png)

4. 运行docker-compose

```Bash
sudo docker-compose up -d
```

![63504c02-c455-493c-a719-33ae17a9f581](https://github.com/SmilingSea/im_project/blob/master/assets/63504c02-c455-493c-a719-33ae17a9f581.png)

5. 查看是否正常运行

```Bash
sudo docker ps
```

![0a735221-4540-4453-a4db-2572f5a80860](https://github.com/SmilingSea/im_project/blob/master/assets/0a735221-4540-4453-a4db-2572f5a80860.png)

6. 导入数据表结构（sql文件在项目地址里面）

![840ea886-818b-4ba9-aca0-f5a9122bd972](https://github.com/SmilingSea/im_project/blob/master/assets/840ea886-818b-4ba9-aca0-f5a9122bd972.png)

![](https://github.com/SmilingSea/im_project/blob/master/assets/656ca444-3444-4b8b-9776-b0efa2ad1a89.png)



我部署的项目地址：http://122.51.134.156:8080

## 五、项目亮点

### 1.使用rabbitmq进行数据传输

在发送消息存消息记录时，将消息对象存入rabbitmq，防止因数据库操作耗时导致发送消息延迟。

### 2.使用DFA算法实现敏感词过滤

通过树状结构存入敏感词并进行查找，在敏感词词库数量大时，相对于直接查找敏感词效率更高。

### 3.代码风格

使用Restful风格代码，注释完善。

### 4.自定义注解进行token验证

为了避免在每个需要验证token的接口中都写一遍验证token是否为空的具体实现，使用自定义注解来实现，在需要验证token的接口上添加这个注解。

## 四、项目反思

1. 可以通过springsecurity框架实现鉴权功能，并实现群主、管理员等权力层级（但是时间有点点不够，springsecurity还没学透）
2. 感觉数据库的设计上还有点小漏洞

## 五、总结

通过这次任务我掌握了websocket的后台开发。这一轮对我最大的收获是我逐渐学会去网上看具体的文章和博客来解决具体的问题。没有只是看一个教学视频然后跟着做。这一轮锻炼了我上网查找资料看博客的学习效率，提升了根据需求独立解决问题的能力。
