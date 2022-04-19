# Docker 使用

`本文介绍一些常用的docker命令，以及后端（SpringBoot项目）.前端（Vue项目）打成docker image镜像后运行容器！`

## 修改远程镜像仓库

为了拉取镜像的时候，能够加速拉取，就需要配置远程的docker仓库镜像

阿里云镜像加速器：https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

![image-20220416233109290](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204162331456.png)

> 修改文件 `/etc/docker/daemon.json` ( 如果文件不存在，你可以直接创建它 ) 。

执行命令：

```bash
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://s9h3h4qc.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```



## 常用命令

> 1. 查看本地镜像

```bash
docker images
```



>2. 列出容器
>
>options包含：
>
>- **-a :**显示所有的容器，包括未运行的。
>- **-f :**根据条件过滤显示的内容。
>- **--format :**指定返回值的模板文件。
>- **-l :**显示最近创建的容器。
>- **-n :**列出最近创建的n个容器。
>- **--no-trunc :**不截断输出。
>- **-q :**静默模式，只显示容器编号。
>- **-s :**显示总的文件大小。

```bash
docker ps [options]
```



> 3. 创建一个新的容器并运行
>
> 如下命令，代表创建一个名字为`VueApp`的容器并运行，`-d`为后台运行，`-p`后面的参数表示端口的映射，`将本机的3000端口映射到容器的80端口`，`vue-docker-app`是镜像的名字。

```bash
docker run --name VueApp -d -p 3000:80 vue-docker-app
```



> 4. 使用 Dockerfile 创建镜像
>
> 如下命令，代表创建一个镜像，`-t`表示设置镜像的名字及标签为`vue-docker-app`,注意最后有一个`.`表示使用当前目录下的`Dockerfile`（详细再下文的项目镜像会解释Dockerfile的作用）

```bash
docker build -t vue-docker-app .
```



> 5. 停止一个容器的运行

```bash
docker stop 容器的id(使用 docker ps -a 查看)
```



> 6. 删除一个容器

```bash
docker container rm 容器名字
```



> 7. 删除一个本地镜像

```bash
docker rmi 镜像id（使用docker images 查看）
```



> 8. 拉取远程镜像

```bash
docker pull 镜像名字:版本tag
```



## 创建后端项目镜像

1. 使用 maven package 将后端项目打成包

2. 新建文件夹，将jar包拷贝到此

3. 新建Dockerfile文件

   > `Dockerfile`文件内容
   >
   > ```dockerfile
   > FROM java:8-alpine
   > COPY ./app.jar /tmp/app.jar
   > ENTRYPOINT java -jar /tmp/app.jar
   > ```
   >
   > 第一行：表示拉取 java 8 的镜像
   >
   > 第二行：表示将当前目录的 maven 打包出来的 jar 包 拷贝到容器的 `/tmp/app.jar`下
   >
   > 第三行：表示运行 app.jar 包

4. 运行`docker build -t xxxx` 命令就可以打成镜像了



## 创建前端项目镜像

1. npm run build 打包，项⽬根⽬录⽣成dist⽬录

2. 项⽬根⽬录下创建 nginx ⽂件夹，新建⽂件 default.conf

   > `default.conf` 文件的内容
   >
   > ```bash
   > server {
   > listen 80;
   > server_name localhost;
   > #charset koi8-r;
   > access_log /var/log/nginx/host.access.log main;
   > error_log /var/log/nginx/error.log error;
   > location / {
   >  root /usr/share/nginx/html;
   >  index index.html index.htm;
   > }
   > #error_page 404 /404.html;
   > # redirect server error pages to the static page /50x.html
   > #
   > error_page 500 502 503 504 /50x.html;
   > location = /50x.html {
   >  root /usr/share/nginx/html;
   >  }
   > }
   > ```
   >
   > 配置⽂件定义了⾸⻚为 /usr/share/nginx/html/index.html，所以可以⼀会把构建出 来的 index.html ⽂件和相关的静态资源放到 /usr/share/nginx/html ⽬录下。
   >
   > 

   

3. 项⽬根⽬录创建 Dockerfile ⽂件

   > `Dockerfile`文件内容:
   >
   > ```dockerfile
   > FROM nginx
   > COPY dist/ /usr/share/nginx/html/
   > COPY nginx/default.conf /etc/nginx/conf.d/default.conf
   > ```
   >
   > - `FROM nginx` 命令的意思该镜像是基于 `nginx:latest` 镜像⽽构建的。 
   > - `COPY dist/ /usr/share/nginx/html/ `命令的意思是将项⽬根⽬录下 `dist` ⽂件 夹下的所有⽂件复制到镜像中 `/usr/share/nginx/html/` ⽬录下。
   > - ` COPY nginx/default.conf /etc/nginx/conf.d/default.conf` 命令的意思是将 Nginx ⽬录下的 `default.conf` 复制到 `etc/nginx/conf.d/default.conf`，⽤本 地的 `default.conf` 配置来替换 Nginx 镜像⾥的默认配置。

4. 运行`docker build -t xxxx` 命令就可以打成镜像了