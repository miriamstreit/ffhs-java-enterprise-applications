# Dokumentation generieren

```shell
cd doc
```

Dokumentation generieren:

```shell
make all
```

Dokumentation [hier](/doc/main.pdf) öffnen.

Dokumentation aufräumen:

```shell
make clean
```

# Setup mit Docker

> Voraussetzung: Docker ist installiert und läuft

Es gibt die Möglichkeit, den Product-Service als Spring Boot Applikation oder als JakartaEE Applikation laufen zu lassen. JakartaEE ist nicht mit allen Plattformen kompatibel,
daher bietet Spring Boot eine gute Alternative, damit die Applikation trotzdem getestet werden kann. Folgend sind beide Varianten dokumentiert.

#### Product-Service als JakartaEE

---

Datei [payara-resources.xml](/product-service/src/main/resources/META-INF/payara-resources.xml) Zeile anpassen zu:
`<property name="URL" value="jdbc:postgresql://bazaar-db:5432/bazaar"/>`

Datei [ProductService.java](/product-service/src/main/java/ch/schuum/brewbazaar/ProductService.java) Zeile anpassen zu:
`serverName = "bazaar-db",`

Datei [docker-compose.yaml](./docker-compose.yml) Zeile anpassen zu: `context: ./product-service`

#### Product-Service als Spring Boot

---

Datei [docker-compose.yaml](./docker-compose.yml) Zeile anpassen zu: `context: ./product-service-boot`

#### Sonstige benötigte Anpassungen

---

Datei [apiClient.js](/frontend/src/api/apiClient.js) Zeilen anpassen zu:

```
const productApiClient = axios.create({
  baseURL: "http://localhost:8081/",
});

const customerApiClient = axios.create({
  baseURL: "http://localhost:8082/",
});

const orderApiClient = axios.create({
  baseURL: "http://localhost:8083/",
});
```

Anschliessend Docker Build:

```shell
docker-compose build --no-cache
```

```shell
docker-compose up --force-recreate
```

Nun sind alle Services folgendermassen erreichbar:

- Frontend: http://localhost
- Product-Service: http://localhost:8081
- Order-Service: http://localhost:8083
- Customer-Service: http://localhost:8082

# Datenbank populieren

Mit den Zugangsdaten `jdbc:postgresql://bazaaradmin:iS3llBeer@localhost:5432/bazaar` auf die Datenbank verbinden und das Skript [seeder.sql](./k8s/database/seeder.sql) ausführen

# Setup mit Kubernetes

siehe [K8s-Readme](./k8s/README.md)
