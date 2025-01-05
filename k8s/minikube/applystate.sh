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

echo "Waiting for book service to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=30s pod -l name=book-service

echo "Waiting for user service to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=30s pod -l name=user-service


echo "Waiting for ingress to be ready..."
kubectl -n cl wait --for=jsonpath='{.status.loadBalancer.ingress}' --timeout=60s ingress/cl-gateway

echo "Done."
kubectl -n cl get pod,svc,ingress -o wide
