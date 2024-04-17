## Websocket项目介绍文档

## 一、项目简介

github仓库链接：https://github.com/SmilingSea/im_project

本项目是根据西二五轮考核要求，基于springboot与websocket实现了实时通讯的聊天室。

## 二、项目实现

### 2.1 项目结构

```SQL
└─im_project
    ├─common        // 静态常量
    ├─config        // 配置类
    ├─controller    // 接口类
    ├─domain        // 实体类
    │  ├─dao        // 数据库实体
    │  ├─dto        // 数据传输实体
    │  └─enums      // 枚举类
    ├─filter        // 过滤器
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

## 三、项目亮点

1. ### 使用rabbitmq进行数据传输

在发送消息存消息记录时，将消息对象存入rabbitmq，防止因数据库操作耗时导致发送消息延迟。

1. ### 使用DFA算法实现敏感词过滤

通过树状结构存入敏感词并进行查找，在敏感词词库数量大时，相对于直接查找敏感词效率更高。

1. ### 代码风格

使用Restful风格代码，注释完善。

## 四、项目反思

1. 可以通过springsecurity框架实现鉴权功能，并实现群主、管理员等权力层级（但是时间有点点不够，springsecurity还没学透）
2. 感觉数据库的设计上还有点小漏洞

## 五、总结

通过这次任务我掌握了websocket的后台开发。这一轮对我最大的收获是我逐渐学会去网上看具体的文章和博客来解决具体的问题。没有只是看一个教学视频然后跟着做。这一轮锻炼了我上网查找资料看博客的学习效率，提升了根据需求独立解决问题的能力。