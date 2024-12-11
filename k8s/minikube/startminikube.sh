#!/bin/sh

minikube start --cpus='4' --memory='4g' --disk-size='8g' \
  --mount=true --mount-string='/tmp/k8s-data:/tmp/k8s-data' \
  --ports='80,8080,8443,5432' \
  --static-ip='192.168.200.200'

echo "Enabling addons..."
minikube addons enable ingress
minikube addons enable ingress-dns