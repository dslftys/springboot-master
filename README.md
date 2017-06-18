# springboot-master

## 1.环境： 

Intellij idea 2017

jdk8

## 2.使用及功能：

  数据库在工程resource中的ssm-crud.sql中，直接导入到本地数据库即可

  使用Gradle构建springboot+mybatis项目，实现srud功能。

  前端暂时没有加入，可以使用Chrome浏览器查看返回的json数据
  
  添加redis和swagger

## 3.添加redis支持
  
  ### 3.1 启动redis
  
  windows下载redis放在盘中  cmd下进入redis文件夹运行：redis-server.exe redis.windows.conf
  
   ### 3.2 redis依赖包
  
  gradle中添加
  
         // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
         
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-data-redis', version: '1.5.2.RELEASE'

    
  ### 3.3 redis配置文件
  
    
  ### 3.4 redis配置
  ....
 
 
 
 


