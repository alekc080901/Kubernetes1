istioctl install
kubectl label namespace default istio-injection=enabled

kubectl apply -f "https://raw.githubusercontent.com/istio/istio/release-1.26/samples/addons/prometheus.yaml"
kubectl create -f https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/main/bundle.yaml

