kind create cluster --config=kind-config.yml;
kind load docker-image postgres;
kind load docker-image bookstore-api;
kind load docker-image prom/prometheus:v2.21.0;
kubectl apply -f bookstore-api-deployment.yml;
kubectl apply -f postgres-deployment.yml;
kubectl apply -f prometheus-config-map.yml;
kubectl apply -f prometheus-deployment.yml;
