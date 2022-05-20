#create PV
kubectl apply -f ../cluster_setup/createPersistedVolume_react_app.yaml

namespace=ssp
helm install --generate-name ../ssp-react-client --values=../ssp-react-client/values-dev.yaml --namespace  $namespace --disable-openapi-validation
