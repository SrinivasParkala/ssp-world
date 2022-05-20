namespace=ssp

echo helm uninstall $(helm list -n $namespace --short | grep spring-boot) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep spring-boot) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep python) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep mysql) -n $namespace
helm uninstall $(helm list -n $namespace --short | grep react) -n $namespace

kubectl delete pv ssp-pv-react
kubectl delete pv ssp-pv-rmxml
kubectl delete pv ssp-pv-oauth
kubectl delete pv ssp-pv-dbdata
kubectl delete pv ssp-pv-python
