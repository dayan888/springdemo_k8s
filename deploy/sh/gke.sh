# Spring Boot demo application for Kubernetes

# For GKE environmnet

echo "Creating Spring Boot demo application for Kubernetes on GKE environment"

### Create configmap for nginx
kubectl create configmap nginx-conf --from-file=deploy/web/nginx.conf 
kubectl create configmap server-conf --from-file=deploy/web/server.conf 

### Create deployments and services

# redis
kubectl apply -f deploy/redis/deployment.yaml
kubectl apply -f deploy/redis/service.yaml

# db
kubectl apply -f deploy/db/statefulset.yaml
kubectl apply -f deploy/db/service.yaml

# nfs (For GKE environment)
kubectl apply -f deploy/nfs/nfs-server-gke-pv.yaml
kubectl apply -f deploy/nfs/nfs-server.yaml
kubectl apply -f deploy/nfs/nfs-server-service.yaml

NFSIP=""

while true; do
    NFSIP=$(kubectl get svc sbdemo-nfs-server-service -o jsonpath="{.spec.clusterIP}")
    if [ "${NFSIP}" == "" ]; then
        echo "waiting..."
        sleep 1
    else
        echo ${NFSIP}
        break;
    fi
done

cat deploy/nfs/nfs-pv.yaml | sed -e "s/{NFSIP}/${NFSIP}/g" | kubectl apply -f -
kubectl apply -f deploy/nfs/nfs-pvc.yaml

# apserver (For GKE environment)
kubectl apply -f deploy/app/deployment-gke.yaml
kubectl apply -f deploy/app/service.yaml

# apserver (For Local environment)
kubectl apply -f deploy/app/pvc.yaml
kubectl apply -f deploy/app/deployment.yaml
kubectl apply -f deploy/app/service.yaml

# web
kubectl apply -f deploy/web/deployment.yaml
kubectl apply -f deploy/web/service.yaml

# Ingress
kubectl run web2 --image=gcr.io/google-samples/hello-app:2.0 --port=8080
kubectl expose deployment web2 --target-port=8080 --type=NodePort

openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ~/tls.key -out ~/tls.crt -subj "/CN=sbdemo.example.com"
kubectl create secret tls --save-config tls-sbdemo --key ~/tls.key --cert ~/tls.crt
kubectl apply -f deploy/lb/nodeport.yaml
kubectl apply -f deploy/lb/ingress.yaml

INGRESS_IP=""
while true; do
    INGRESS_IP=$(kubectl get ing sbdemo-ingress -o jsonpath="{.status.loadBalancer.ingress[].ip}")
    if [ "${INGRESS_IP}" == "" ]; then
        echo "waiting..."
        sleep 10
    else
        echo ${INGRESS_IP}
        break;
    fi
done

curl -i https://${INGRESS_IP} -H "Host: sbdemo.example.com" --insecure

echo "Open your hosts file and enter and save:"
echo "${INGRESS_IP} sbdemo.example.com"

echo "If OK, then enter to proceed"
echo "Take care that after open browser, you may have to wait for long long time and reload the url."
read

# Open browser (if failed, open manually)
echo "open browser for https://sbdemo.example.com/login"
open https://sbdemo.example.com/login

# Log (stern must be installed)
echo "show log by stern"
stern -l app=sbdemo-apserver
