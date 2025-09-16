package ch.schuum.brewbazaar.repository;

import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends CrudRepository<Beer, Integer> {
}
