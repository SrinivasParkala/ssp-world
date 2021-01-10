namespace=ssp

echo helm uninstall $(helm list -n $namespace --short | grep spring-boot) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep spring-boot) -n $namespace

kubectl delete pv ssp-pv-springboot-config
