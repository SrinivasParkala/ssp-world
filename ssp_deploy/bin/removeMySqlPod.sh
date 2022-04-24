namespace=ssp

echo helm uninstall $(helm list -n $namespace --short | grep mysql) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep mysql) -n $namespace

kubectl delete pv ssp-pv-data
