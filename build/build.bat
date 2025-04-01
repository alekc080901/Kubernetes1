call mvnw package -Dmaven.test.skip=true
call docker build -t server -f .\build\Dockerfile .
call kubectl apply -f .\build\ConfigMap.yaml
call kubectl apply -f .\build\Pod.yaml
call kubectl apply -f .\build\Deployment.yaml
call kubectl apply -f .\build\Service.yaml
call kubectl apply -f .\build\DaemonSet.yaml
call kubectl apply -f .\build\CronJob.yaml