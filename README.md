MyBatis SQL Mapper Framework for Java[ Mybatis-3 中文注释]
=====================================

[![Build Status](https://travis-ci.org/mybatis/mybatis-3.svg?branch=master)](https://travis-ci.org/mybatis/mybatis-3)
[![Coverage Status](https://coveralls.io/repos/mybatis/mybatis-3/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/mybatis-3?branch=master)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/https/oss.sonatype.org/org.mybatis/mybatis.svg)](https://oss.sonatype.org/content/repositories/snapshots/org/mybatis/mybatis/)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Stack Overflow](http://img.shields.io/:stack%20overflow-mybatis-brightgreen.svg)](http://stackoverflow.com/questions/tagged/mybatis)
[![Project Stats](https://www.openhub.net/p/mybatis/widgets/project_thin_badge.gif)](https://www.openhub.net/p/mybatis)

Mybatis-3 中文注释![mybatis](http://mybatis.github.io/images/mybatis-logo.png)

###相关工具
####画图工具
Diagram Designer

-------------
Mybatis整体架构

|   `接口层`       |           sqlsession                    |
| -------------    | ------------------------------ |
| `核心处理层`     | `配置解析` `参数映射`  `SQL解析`         |
|                  | `SQL执行`  `结果集映射`  `插件`    |
| `基础支持层`     | `数据源模块`  `事务管理器模块`  `缓存模块` `Binding模块`    |
|                  | `反射模块` `类型转换` `日志模块` `资源加载` `解析器模块`      |

###基础支持层

####反射模块
可以学习Java强大的反射功能。
####类型转换模块
Mybatis为简化配置文件提供了别名机制，该机制是类型转换模块的主要功能之一。类型转换模块的另外一个功能是实现JDBC类型和Java类型之间的转换。
####日志模块
继承第三方日志框架
####资源加载模块
主要是对类加载器进行封装，确定类加载器的使用顺序，并提供了加载类文件以及其他资源文件的功能。
####解析器模块
1、对XPath进行封装，为MyBatis初始化时解析mybatis-config.xml配置文件以及映射配置文件提供支持；
2、为处理动态SQL语句中的占位符提供支持。
####数据源模块
数据源是实际开发中常用的组件之一。现在开源的数据源都提供了比较丰富的功能，例如，连接池功能、检测连接状态等，选择性能优秀的数据源组件对于提升ORM框架乃至整个应用的性能都是非常重要的。MyBatis自身提供了相应的数据源实现，当然MyBatis也提供了与第三方数据源集成的接口，这些功能都位于数据源模块之中。
####事务管理
MyBatis对数据库中的事务进行了抽象，其自身提供了相应的事务接口和简单实现。在很多场景中，MyBatis会与Spring框架集成，并由Spring框架管理事务。
####缓存模块
在优化系统性能时，优化数据库性能是非常重要的一个环节，而添加缓存则是优化数据库时最有效的手段之一。正确、合理地使用缓存可以将一部分数据库请求拦截在缓存这一层，如图1-4所示，这就能够减少相当一部分数据库的压力。
MyBatis中提供了一级缓存和二级缓存，而这两级缓存都是依赖于基础支持层中的缓存模块实现的。这里需要读者注意的是，MyBatis中自带的这两级缓存与MyBatis以及整个应用是运行在同一个JVM中的，共享同一块堆内存。如果这两级缓存中的数据量较大，则可能影响系统中其他功能的运行，所以当需要缓存大量数据时，优先考虑使用Redis、Memcache等缓存产品。
####Binding模块
，在调用SqlSession相应方法执行数据库操作时，需要指定映射文件中定义的SQL节点，如果出现拼写错误，我们只能在运行时才能发现相应的异常。为了尽早发现这种错误，MyBatis通过Binding模块将用户自定义的Mapper接口与映射配置文件关联起来，系统可以通过调用自定义Mapper接口中的方法执行相应的SQL语句完成数据库操作，从而避免上述问题。
值得读者注意的是，开发人员无须编写自定义Mapper接口的实现，MyBatis会自动为其创建动态代理对象。在有些场景中，自定义Mapper接口可以完全代替映射配置文件，但有的映射规则和SQL语句的定义还是写在映射配置文件中比较方便，例如动态SQL语句的定义。

###核心层
Drawing[核心接口层]

###接口层
接口层相对简单，其核心是SqlSession接口，该接口中定义了MyBatis暴露给应用程序调用的API，也就是上层应用与MyBatis交互的桥梁。
