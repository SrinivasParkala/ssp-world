namespace=ssp

echo helm uninstall $(helm list -n $namespace --short | grep tomcat) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep tomcat) -n $namespace

kubectl delete pv ssp-pv-app
kubectl delete pv ssp-pv-app-config

