# Setup

> Voraussetzung: Minikube ist installiert.

```shell
minikube start
```

## Minikube dashboard

```shell
minikube dashboard
```

Browser sollte sich automatisch öffnen, sonst kann die URL mittels `minikube dashboard --url` ermittelt werden

## Erstmalige Einrichtung

### Ingress Controller aktivieren

gem. [https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/](https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/)

```shell
minikube addons enable ingress
```

### Projektcode anpassen

Es gibt die Möglichkeit, den Product-Service als Spring Boot Applikation oder als JakartaEE Applikation laufen zu lassen. JakartaEE ist nicht mit allen Plattformen kompatibel,
daher bietet Spring Boot eine gute Alternative, damit die Applikation trotzdem getestet werden kann. Folgend sind beide Varianten dokumentiert.

#### Product-Service als JakartaEE

---

Datei [payara-resources.xml](/product-service/src/main/resources/META-INF/payara-resources.xml) Zeile anpassen zu:
`<property name="URL" value="jdbc:postgresql://database.default.svc.cluster.local:5432/bazaar"/>`

Datei [ProductService.java](/product-service/src/main/java/ch/schuum/brewbazaar/ProductService.java) Zeile anpassen zu:
`serverName = "database.default.svc.cluster.local",`

Datei [deploy.sh](/k8s/deployment/deploy.sh) Zeile anpassen zu: `cd ../product-service`

#### Product-Service als Spring Boot

---

Datei [deploy.sh](/k8s/deployment/deploy.sh) Zeile anpassen zu: `cd ../product-service-boot`

#### Sonstige benötigte Anpassungen

---

Datei [apiClient.js](/frontend/src/api/apiClient.js) Zeilen anpassen zu:

```
const productApiClient = axios.create({
  baseURL: "/api/product",
});

const customerApiClient = axios.create({
  baseURL: "/api/customer",
});

const orderApiClient = axios.create({
  baseURL: "/api/order",
});
```

### Build und Deployment

Es kann entweder automatisiert deployed werden oder manuell. Die jeweiligen Anleitungen sind hier dokumentiert.

#### Automatisiert

```shell
cd k8s/deployment/

chmod +y deploy.sh

./deploy.sh
```

#### Manuelle Schritte

kann weggelassen werden, wenn das Deployment Skript ausgeführt wurde. Dient nur zur Nachvollziehbarkeit, falls das Deployment Skript nicht funktioniert haben sollte

##### Docker Images erstellen und pushen

Der Source Code von jedem Service muss einzeln in ein Docker Image verpackt werden. Dafür muss ins jeweilige Projekt navigiert werden. Als Image-Name wird der Name des Projektordners verwendet
(z.B. Customer-Service: `customer-service`). Als Tag wird `latest` verwendet.

1. mit `cd` ins jeweilige Unterprojekt (customer-service, frontend, order-service, product-service) navigieren
2. `docker build --no-cache . -t imagename:tag`
3. `minikube image rm imagename:tag` (falls bereits vorhanden)
4. `minikube image load imagename:tag`

Damit im jeweiligen Deployment das Image nicht stets angepasst werden muss, kann immer der gleiche Name verwendet werden. Dazu muss das Image immer von Minikube gelöscht und neu geladen werden.
Dass dies möglich ist, muss jeweils das Deployment gelöscht werden, da es sonst einen Fehler gibt, weil das Image in Gebrauch ist.

Quelle 3. Schritt: [https://stackoverflow.com/a/62303945](https://stackoverflow.com/a/62303945)

##### Workloads deployen

> Am besten wird die DB als erstes deployed, sonst schlagen die anderen Container alle fehl.
> Jeder Unterordner einzeln:

```
cd k8s
kubectl apply -f /projekt
```

Die URL, unter welcher die Applikation läuft, findet sich im Minikube Dashboard (siehe oben). Dazu navigiert man auf den Menupunkt "Services" und dann auf den Unterpunkt Ingresses. Dort werden die Ingresses angezeigt mit einer IP. Diese kann angeklickt werden, dann erscheint das Frontend.

## Alle weiteren Starts

`minikube start`, alle Workloads sollten erneut gestartet werden

## Codeanpassungen deployen

Zu Schritt "Docker Images erstellen und pushen", vorher alle Deployments löschen

# Datenbank populieren

Um die Datenbank zu befüllen, müssen folgende Befehle ausgeführt werden. Die Population soll manuell angestossen werden, damit der Benutzer selber entscheiden kann, wann er seine Datenbank zurücksetzen will.

> Achtung: Bei der Population der Datenbank werden alle Tabellen geleert, die alten Daten bleiben nicht übrig.

```shell
cd k8s/database
kubectl get pods
// Name des Datenbank-Pods kopieren und unten einfügen
kubectl exec -i database-66bd658795-pcx6g -- psql -d bazaar -U bazaaradmin < seeder.sql
```
