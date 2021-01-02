#Steps
#1-Install docker using link https://docs.docker.com/engine/install/ubuntu/
#2-Install minikube using link https://phoenixnap.com/kb/install-minikube-on-ubuntu - Skip step 3 to install VirtualBox Hypervisor
#3-Using this command to start minikube minikube start --vm-driver=none --kubernetes-version v1.15.5

#Install Docker

sudo apt-get update -y
sudo apt-get upgrade -y
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable test"

sudo apt-get install docker-ce docker-ce-cli containerd.io

#Install minikube

wget https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo cp minikube-linux-amd64 /usr/local/bin/minikube
sudo chmod 755 /usr/local/bin/minikube

minikube version

curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl

kubectl version -o json

minikube start --vm-driver=none --kubernetes-version v1.15.4

Note: Follow this link if docker info command shows error - https://stackoverflow.com/questions/48957195/how-to-fix-docker-got-permission-denied-issue
