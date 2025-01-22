#!/bin/sh

echo "Waiting for database to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=60s pod -l name=postgres-db

echo "Waiting for book service to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=10s pod -l name=book-service

echo "Waiting for user service to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=10s pod -l name=user-service


echo "Waiting for ingress to be ready..."
kubectl -n cl wait --for=jsonpath='{.status.loadBalancer.ingress}' --timeout=60s ingress/cl-gateway