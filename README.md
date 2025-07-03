# hikvision-face-data

对接海康人脸通道设备数据，将刷取的身份证信息插入到数据库表中


1. 打包
2. 将海康sdk所需的库文件自行放在jar包同级的lib目录下  
   [下载地址](https://open.hikvision.com/download/5cda567cf47ae80dd41a54b3?type=10&id=5cda5902f47ae80dd41a54b7)
3. 启动


### 注意事项
pom文件中的jna依赖可从maven中央仓库下载，examples依赖可使用该项目lib文件夹中的jar包