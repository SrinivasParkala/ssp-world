Steps to start working

1. Start minikube from /ci_cd/helm_deployment/bin/startMinikube.sh
2. https://localhost:30005/swagger-ui.html#/ 
3. cd /ssp-world/ssp_client

	npm start
	This will open the application at http://localhost:3000/
	
4. Start Proxy Server

	cd /ssp_client/src/proxy
	node httpsProxy.js
	
5. https://localhost:3000/

	Tenant:ssp_domain
	Username:vatika@ten_01
	Password:password
-----------------------------------------------------------------


K8 Tutorial-> https://kubernetes.io/docs/home/
Docker Tutorial -> https://docs.docker.com/get-started/

https://hub.docker.com/

Docker ID : Mob
Docker password: welcome2xxxxxx

Git ID: rash.srini@gmail.com
Git password: welcome2xxx

1. Setup Docker, minikube & helm3 , see script in docker_setup folder

	a. Follow steps in ci_cd/helm_deployment/docker_minikube/setupMinikube_ubuntu.sh to setup Docker and Minikue
	b. Follow below commands to setup Helm 3
	
		wget https://get.helm.sh/helm-v3.4.2-linux-amd64.tar.gz
		tar -xzvf helm-v3.4.2-linux-amd64.tar.gz
		sudo mv linux-amd64/helm /usr/local/bin/helm

2. Login to Docker, this will create /home/<user>/.docker/config.json file
   sudo docker login
   
3. Create docker id secret
    sudo kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=/home/<user>/.docker/config.json \
    --type=kubernetes.io/dockerconfigjson
    
4. create name space
   kubectl create namespace ssp
5. Run ./bin/install.sh , to install Tomcat and Mysql


Git Clone Steps
------------------
git clone https://github.com/SrinivasParkala/ssp-world.git

git checkout ssp_branch_aug20
git add .
git config --global user.email "rash.srini@gmail.com"
git commit -m "ssp service and client"
git push

git user: rash.srini@gmail.com
6. Create sspdb my sql database
7. Create mysql user ssp_db_admin and give required permission
8. Create required table by running createDB.sql
9. insert table users with user and password


