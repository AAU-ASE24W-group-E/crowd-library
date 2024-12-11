#!/bin/sh

MKBCTL="minikube kubectl -- "

pushd ../state

for f in *.yaml; do
  echo "Applying $f..."
  $MKBCTL apply -f $f
done

echo "Done."
popd