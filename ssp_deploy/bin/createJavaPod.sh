#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_java_app.yaml

namespace=ssp
helm install --generate-name ../java-app-k8 --values=../java-app-k8/values-dev.yaml --namespace  $namespace --disable-openapi-validation
