#!/bin/sh

echo "Note: kubectl must be available, e.g. alias kubectl='minikube kubectl -- ' "

pushd ../state

for f in *.yaml; do
  echo "Applying $f..."
  kubectl apply -f $f
done

echo "Done."
popd

echo "Waiting for pods to be ready..."
kubectl -n cl wait --for=condition=Ready --timeout=30s pod -l name=book-service
