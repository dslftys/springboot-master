# springboot-master

1.环境：

Intellij idea 2017

jdk8

2. 使用及功能：

  数据库在工程resource中的ssm-crud.sql中，直接导入到本地数据库即可

  使用Gradle构建springboot+mybatis项目，实现srud功能。

  前端暂时没有加入，可以使用Chrome浏览器查看返回的json数据

3.添加redis支持
  
  3.1 启动redis
  
  windows下载redis放在盘中  cmd下进入redis文件夹运行：redis-server.exe redis.windows.conf
  
  3.2 redis依赖包
  
  gradle中添加
  
         // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-data-redis', version: '1.5.2.RELEASE'

    
  3.3 redis配置文件
  
 在application.properties中添加如下
    # REDIS (RedisProperties)
    # Redis数据库索引（默认为0）
    spring.redis.database=0
    # Redis服务器地址
    spring.redis.host=localhost
    # Redis服务器连接端口
    spring.redis.port=6379
    # Redis服务器连接密码（默认为空）
    spring.redis.password=
    # 连接池最大连接数（使用负值表示没有限制）
    spring.redis.pool.max-active=8
    # 连接池最大阻塞等待时间（使用负值表示没有限制）
    spring.redis.pool.max-wait=-1
    # 连接池中的最大空闲连接
    spring.redis.pool.max-idle=8
    # 连接池中的最小空闲连接
    spring.redis.pool.min-idle=1
    # 连接超时时间（毫秒）
    spring.redis.timeout=100
    
 3.4 redis配置
 ....
 
 
 
 


