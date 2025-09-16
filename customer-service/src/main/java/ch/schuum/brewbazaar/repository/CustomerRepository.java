package ch.schuum.brewbazaar.repository;

import ch.schuum.brewbazaar.model.Customer;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Optional<Customer> findByNameIgnoreCaseAndSurnameIgnoreCaseAndStreetIgnoreCase(@NotNull String name, @NotNull String surname, @NotNull String street);
}
