#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_python_app.yaml

namespace=ssp
helm install --generate-name ../python-rest --values=../python-rest/values-dev.yaml --namespace  $namespace --disable-openapi-validation
