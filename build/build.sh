mvnw package -Dmaven.test.skip=true
docker build -t server -f .\build\Dockerfile .
kubectl apply -f .\build\ConfigMap.yaml
kubectl apply -f .\build\Pod.yaml
kubectl apply -f .\build\Deployment.yaml
kubectl apply -f .\build\Service.yaml
kubectl apply -f .\build\DaemonSet.yaml
kubectl apply -f .\build\CronJob.yaml