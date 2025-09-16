package ch.schuum.brewbazaar.repository;

import ch.schuum.brewbazaar.model.Beer;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class BeerRepository {
     @PersistenceContext(unitName="default")
     private EntityManager entityManager;

    public List<Beer> findAll() {
        return entityManager.createQuery("SELECT b FROM Beer b", Beer.class).getResultList();
    }
    
    public Optional<Beer> findById(int id) {
        Beer foundBeer = entityManager.find(Beer.class, id);
        return foundBeer != null ? Optional.of(foundBeer) : Optional.empty();
    }

    public Optional<Beer> save(Beer beer) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(beer);
            entityManager.getTransaction().commit();
            return Optional.of(beer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public int delete(Beer beer) {
        entityManager.remove(beer);
        return beer.getBeerId();
    }


}
