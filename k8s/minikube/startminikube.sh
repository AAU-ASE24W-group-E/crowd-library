#!/bin/sh

if [ -f .env ]; then
  source .env
else
  touch .env
  echo "Created empty .env file which you can use to override default settings."
fi

# you can override these variables in your .env file
DOCKER_CPUS=${DOCKER_CPUS:-4}
DOCKER_MAX_MEMORY=${DOCKER_MAX_MEMORY:-4g}
DOCKER_DISK_SIZE=${DOCKER_DISK_SIZE:-8g}
DOCKER_PORTS=${DOCKER_PORTS:-80,8080,8443,5432}

DATADIR="$(pwd)/tmp/k8s-data"
echo "Mounting data directory: $DATADIR"
mkdir -p $DATADIR

minikube start --cpus="$DOCKER_CPUS" --memory="$DOCKER_MAX_MEMORY" --disk-size="$DOCKER_DISK_SIZE" \
  --driver='docker' \
  --mount=true --mount-string="$DATADIR:/tmp/k8s-data" \
  --ports="$DOCKER_PORTS"

echo "Enabling addons..."
minikube addons enable ingress
minikube addons enable metrics-server
#minikube addons enable ingress-dns

echo "Aliasing kubectl to minikube..."
alias kubectl='minikube kubectl -- '

kubectl get nodes