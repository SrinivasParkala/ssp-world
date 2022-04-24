#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_oauth_app.yaml

namespace=ssp
helm install --generate-name ../spring-boot-oauth --values=../spring-boot-oauth/values-dev.yaml --namespace  $namespace --disable-openapi-validation
