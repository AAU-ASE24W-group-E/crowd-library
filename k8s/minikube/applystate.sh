#!/bin/sh

echo "Note: kubectl must be available, e.g. alias kubectl='minikube kubectl -- ' "

#pushd ../state
cd ../state

for f in *.yaml; do
  echo "Applying $f..."
  kubectl apply -f $f
done

echo "Done."

#popd
cd ../minikube

echo "Waiting for database to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=30s pod -l name=postgres-db

echo "Waiting for microservices to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=30s pod -l name=book-service

echo "Waiting for ingress to be ready..."
kubectl -n cl wait --for=jsonpath='{.status.loadBalancer.ingress}' ingress/cl-api

echo "Done."
kubectl -n cl get pod,svc,ingress -o wide
