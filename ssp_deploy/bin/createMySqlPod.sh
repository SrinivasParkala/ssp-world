#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_db_data.yaml

namespace=ssp
helm install --generate-name ../mysql-deploy --values=../mysql-deploy/values-dev.yaml --namespace  $namespace --disable-openapi-validation
