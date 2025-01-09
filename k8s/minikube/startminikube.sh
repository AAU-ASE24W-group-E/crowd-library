#!/bin/sh

mkdir -p /tmp/k8s-data

minikube start --cpus='4' --memory='4g' --disk-size='8g' \
  --driver='docker' \
  --mount=true --mount-string='/tmp/k8s-data:/tmp/k8s-data' \
  --ports='8080,8443,5432'

echo "Enabling addons..."
minikube addons enable ingress
#minikube addons enable ingress-dns

echo "Aliasing kubectl to minikube..."
alias kubectl='minikube kubectl -- '

kubectl get nodes