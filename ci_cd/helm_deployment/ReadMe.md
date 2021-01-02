K8 Tutorial-> https://kubernetes.io/docs/home/
Docker Tutorial -> https://docs.docker.com/get-started/

https://hub.docker.com/

Docker ID : Mob
Docker password: welcome2xxxxxx

Git ID: rash.srini@gmail.com
Git password: welcome2xxx

1. Setup Docker, minikube & helm3 , see script in docker_setup folder
2. Login to Docker, this will create /root/.docker/config.json file
3. Create docker id secret
    kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=/root/.docker/config.json \
    --type=kubernetes.io/dockerconfigjson
4. Run ./bin/install.sh , to install Tomcat and Mysql


Git Clone Steps
------------------
git clone https://github.com/SrinivasParkala/ssp-world.git

git checkout ssp_branch_aug20
git add .
git config --global user.email "rash.srini@gmail.com"
git commit -m "ssp service and client"
git push

git user: rash.srini@gmail.com
git password: welcome2ssp
