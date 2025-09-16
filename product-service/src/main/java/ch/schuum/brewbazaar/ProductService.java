package ch.schuum.brewbazaar;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@DataSourceDefinition(
    name = "java:global/bazaar",
    className = "org.postgresql.ds.PGConnectionPoolDataSource",
    serverName = "bazaar-db",
    portNumber = 5432,
    databaseName = "bazaar",
    user = "bazaaradmin",
    password = "iS3llBeer"
)
@ApplicationPath("/")
public class ProductService extends Application {

}