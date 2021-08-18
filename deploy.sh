#!/bin/bash
mvn clean && mvn package
docker build -t "marcosomoraisdev/poc-redis:v1" .
docker push marcosomoraisdev/poc-redis:v1
kubectl delete deployment.apps/poc-redis
kubectl apply -f k8s/deployment.yaml