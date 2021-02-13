sudo rm -rf /tmp/*
sudo swapoff -a
sudo sysctl fs.protected_regular=0
#sudo minikube start --vm-driver=none --kubernetes-version v1.20.1 --alsologtostderr --v=2
sudo minikube start --vm-driver=none --kubernetes-version v1.20.1
