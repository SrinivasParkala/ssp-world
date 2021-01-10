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


