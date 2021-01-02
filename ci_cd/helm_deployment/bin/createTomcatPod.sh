#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_app.yaml

namespace=ssp
helm install --generate-name ../tomcat-deploy --values=../tomcat-deploy/values-dev.yaml --namespace  $namespace --disable-openapi-validation
