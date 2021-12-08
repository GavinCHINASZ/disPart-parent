# 工程简介
## 智慧农批平台

### 模块说明

新加服务时在列表中的新增端口，避免占用

| 模块                 | 说明               | 端口 |
| -------------------- | ------------------ | ---- |
| dispart-parent       | 父工程管理版本依赖 | 无   |
| ——dispart-auth       | 权限认证中心       | 8766 |
| ——dispart-base       | 基础服务           | 8761 |
| ——dispart-common     | 工具模块           | 无   |
| ——dispart-data-query | 查询服务           | 8762 |
| ——dispart-gateway    | 网关服务           | 8765 |
| ——dispart-model      | 实体模块           | 无   |
| ——dispart-order      | 订单服务           | 8763 |
| ——dispart-out-hsb    | 外联服务           | 8764 |
| ——dispart-user       | 用户服务           | 8767 |



## 快速开始

### 版本要求

```xml
<java.version>1.8</java.version>
<cloud.version>Hoxton.RELEASE</cloud.version>
<alibaba.version>2.2.0.RELEASE</alibaba.version>
<mybatis-plus.version>3.3.1</mybatis-plus.version>
<mysql.version>5.1.46</mysql.version>
<swagger.version>2.7.0</swagger.version>
<jwt.version>0.7.0</jwt.version>
<fastjson.version>1.2.29</fastjson.version>
<httpclient.version>4.5.1</httpclient.version>
<easyexcel.version>2.2.0-beta2</easyexcel.version>
<aliyun.version>4.1.1</aliyun.version>
<oss.version>3.9.1</oss.version>
<jodatime.version>2.10.1</jodatime.version>
<hutool.version>5.6.6</hutool.version>
<jasypt.version>2.1.0</jasypt.version>
```

### 安装教程

```
​```
jdk1.8

maven3.3.9

mysql5.7

nacos注册中心
参考延伸阅读里的资料

redis

idea19.2：idea安装插件lombok、MyBatisCodeHelperPlugin
```

nacos作为服务注册中心，修改数据库源为本地mysql

每个模块配置文件中的敏感信息加密处理，将密钥添加到启动环境中，不在配置文件中体现

#### MyBatisCodeHelperPlugin使用方式：

1、先使用dataSources链接上数据库

![](https://gitee.com/wujiele/image/raw/master/image-20210607150013535.png)

2、选择对应的库对应的表右键点击

![image-20210607150212196](https://gitee.com/wujiele/image/raw/master/image-20210607150212196.png)

3、弹出如下图，根据自己需要进行配置勾选，确定即可生成，model、service、service实现类、dao、xml等相应的java代码

![image-20210607150656168](https://gitee.com/wujiele/image/raw/master/image-20210607150656168.png)

### 启动步骤

1. 先启动nacos

   - ```bash
     windows：startup.cmd -m standalone
     Linux：sh startup.sh -m standalone
     ```

2. 启动zipkin

   - ```bash
     java -jar *.jar
     ```

3. 启动redis

   - ```shell
     windows：直接双击启动
     Linux：./redis-server &
     ```

4. 启动各个服务

## 测试

> **未登录前：不需要token的接口直接访问可以得到结果**

![image-20210604164949510](https://gitee.com/wujiele/image/raw/master/image-20210604164949510.png)

> **需要token的接口会被拒绝**

![image-20210604165050829](https://gitee.com/wujiele/image/raw/master/image-20210604165050829.png)

> > 登录后：
>
> 使用admin登录拥有所有权限

![image-20210604165230549](https://gitee.com/wujiele/image/raw/master/image-20210604165230549.png)

> 使用普通用户登录：

![image-20210604165618316](https://gitee.com/wujiele/image/raw/master/image-20210604165618316.png)

> 获取登录admin用户信息（a8a07dc6-ffb1-4284-85c0-309205aecd99）

![image-20210604165325362](https://gitee.com/wujiele/image/raw/master/image-20210604165325362.png)

> 获取登录普通用户信息（8687534c-60d5-41b7-abe8-debce28e5c62）

![image-20210604165711706](https://gitee.com/wujiele/image/raw/master/image-20210604165711706.png)

> 访问需要token的接口，无权限设置时，使用admin的token或者是普通用户的token都可以访问

![image-20210604165403315](https://gitee.com/wujiele/image/raw/master/image-20210604165403315.png)

> 访问需要token并且有admin权限的接口

> 使用admin的token访问：

![image-20210604165833045](https://gitee.com/wujiele/image/raw/master/image-20210604165833045.png)

> 使用普通用户的token：

![image-20210604165907012](https://gitee.com/wujiele/image/raw/master/image-20210604165907012.png)

> 使用普通用户token访问权限为user的接口

![image-20210604165942406](https://gitee.com/wujiele/image/raw/master/image-20210604165942406.png)

> 使用admin用户token访问权限为user的接口

![image-20210604170026342](https://gitee.com/wujiele/image/raw/master/image-20210604170026342.png)

##### 经过以上测试，我们可以看出，不需要token的接口可以不登录，需要token的必须要登录，且有的接口是设置了相应的角色权限，有的接口只能管理员可以访问，有的只能是用户权限可以访问。

# 延伸阅读

[SpringCloud Alibaba整合Oauth2，博文有点长耐心看完不说精通，至少从零搭建没问题了]: https://blog.csdn.net/u013783065/article/details/108528766
[SpringCloud Alibaba，博文有点长耐心看完不说精通，至少从零搭建没问题了]: https://blog.csdn.net/u013783065/article/details/108461828
[SpringCloud Hoxton SR8最新版，博文有点长耐心看完不说精通，至少从零搭建没问题了]: https://blog.csdn.net/u013783065/article/details/108419374

