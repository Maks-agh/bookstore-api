kind create cluster --config=kind-config.yml;
kind load docker-image postgres;
kind load docker-image bookstore-api;
kubectl apply -f bookstore-api-deployment.yml;
kubectl apply -f postgres-deployment.yml ;
