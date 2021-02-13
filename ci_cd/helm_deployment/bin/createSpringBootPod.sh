#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_spring_config.yaml

namespace=ssp
helm install --generate-name ../spring-boot --values=../spring-boot/values-dev.yaml --namespace  $namespace --disable-openapi-validation