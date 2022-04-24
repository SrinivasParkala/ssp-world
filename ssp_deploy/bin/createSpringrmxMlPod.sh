#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_rmxml_app.yaml

namespace=ssp
helm install --generate-name ../spring-boot-rmxrest --values=../spring-boot-rmxrest/values-dev.yaml --namespace  $namespace --disable-openapi-validation
