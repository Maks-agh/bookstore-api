###Documentation
You can use official k8s and kind documentation, they're greatly written and will provide you all necessary information:
https://kubernetes.io/docs/
https://kind.sigs.k8s.io/docs/user/quick-start/

###Requirements
To start working with k8s locally, you'll need `kubectl` and some tool that runs k8s locally - I chose `kind`. When you have these, you can follow below steps. Please note that running all below commands will take some time and resources(!) - check Docker resources config if you have some problems.

###Start k8s cluster with kind:
```
kind create cluster --config=k8s/kind-config.yml
```

###Load needed Docker images from local env:
```
kind load docker-image postgres;
kind load docker-image bookstore-api;
kind load docker-image prom/prometheus:v2.21.0;
kind load docker-image elasticsearch:7.14.2;
kind load docker-image kibana:7.14.2;
```

###Start applications (create deployments and services):
```
kubectl apply -f elasticsearch-deployment.yml;
kubectl apply -f kibana-deployment.yml;
kubectl apply -f bookstore-api-deployment.yml;
kubectl apply -f postgres-deployment.yml;
kubectl apply -f prometheus-config-map.yml;
kubectl apply -f prometheus-deployment.yml;
```

###k8s init script
If you're sure about having correct configs and environment, you can just run script `init-cluster.sh`
