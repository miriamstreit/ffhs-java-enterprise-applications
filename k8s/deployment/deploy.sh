#! /bin/bash

# This script is used to deploy the application to the kubernetes cluster
# It will create the deployment and service for the application

minikube start
minikube addons enable ingress
minikube addons enable metrics-server

cd ../../
cd frontend
docker build --no-cache . -t frontend:latest
minikube image rm frontend:latest
minikube image load frontend:latest

cd ../customer-service
docker build --no-cache . -t customer-service:latest
minikube image rm customer-service:latest
minikube image load customer-service:latest

cd ../order-service
docker build --no-cache . -t order-service:latest
minikube image rm order-service:latest
minikube image load order-service:latest

cd ../product-service-boot
docker build --no-cache . -t product-service:latest
minikube image rm product-service:latest
minikube image load product-service:latest

cd ../k8s
kubectl apply -f ./frontend
kubectl apply -f ./customer-service
kubectl apply -f ./order-service
kubectl apply -f ./product-service
kubectl apply -f ./database

