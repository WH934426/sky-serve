# 苍穹外卖-后端项目

## 介绍

本项目是黑马苍穹外卖的后端工程。后端工程基于 maven 进行项目构建，并且进行分模块开发。

### 模块介绍

对工程的每个模块作用说明：

| **序号** | **名称**     | **说明**                                     |
|--------|------------|--------------------------------------------|
| 1      | sky-serve  | maven父工程，统一管理依赖版本，聚合其他子模块                  |
| 2      | sky-common | 子模块，存放公共类，例如：工具类、常量类、异常类等                  |
| 3      | sky-pojo   | 子模块，存放实体类、VO、DTO等                          |
| 4      | sky-server | 子模块，后端服务，存放配置文件、Controller、Service、Mapper等 |

子模块说明

**sky-common:** 模块中存放的是一些公共类，可以供其他模块使用

| 名称          | 说明                   |
|-------------|----------------------|
| constant    | 存放相关常量类              |
| context     | 存放上下文类               |
| enumeration | 项目的枚举类存储             |
| exception   | 存放自定义异常类             |
| json        | 处理json转换的类           |
| properties  | 存放SpringBoot相关的配置属性类 |
| result      | 返回结果类的封装             |
| utils       | 常用工具类                |

**sky-pojo:** 模块中存放的是一些 entity、DTO、VO

| **名称** | **说明**                 |
|--------|------------------------|
| Entity | 实体，通常和数据库中的表对应         |
| DTO    | 数据传输对象，通常用于程序中各层之间传递数据 |
| VO     | 视图对象，为前端展示数据提供的对象      |

**sky-server:** 模块中存放的是 配置文件、配置类、拦截器、controller、service、mapper、启动类等

| 名称                  | 说明             |
|---------------------|----------------|
| annotation          | 存放自定义注解类       |
| aspect              | 存放自定义切面类       |
| config              | 存放配置类          |
| controller          | 存放controller类  |
| handler             | 存放全局异常类        |
| interceptor         | 存放拦截器类         |
| mapper              | 存放mapper接口     |
| service             | 存放service类     |
| task                | 存放自定义定时任务类     |
| websocket           | 存放WebSocket服务类 |
| SkyServeApplication | 启动类            |

### 数据表介绍

![sky_take_out.png](https://s2.loli.net/2024/07/22/HElgPYyxjaKhN3V.png)

## 运行

1. 首先克隆本项目

```bash
https://github.com/WH934426/sky-serve.git
```

2. 导入数据库SQL文件：存放于 `sky-server/src/main/resources/db`的目录下
3. 配置数据源信息
4. 导入接口文档，使用 YAPI/Apifox导入 `sky-server/src/main/resources/api`目录下的接口文档json文件
5. 启动项目

















