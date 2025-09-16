package ch.schuum.brewbazaar.service;

import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.repository.BeerRepository;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class BeerService {

    private final BeerRepository beerRepository;

    @Inject
    public BeerService(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public List<Beer> findAllBeers() {
        return beerRepository.findAll();
    }
    public Optional<Beer> findBeerById(int id) {
        return beerRepository.findById(id);
    }

    public Optional<Beer> createBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    public Optional<Beer> updateBeer(int id, Beer beer) {
        return beerRepository.save(beer);
    }

    public int deleteBeer(int id) {
        Optional<Beer> beerToBeDeleted = findBeerById(id);
        return beerToBeDeleted.map(beerRepository::delete).orElse(0);
    }
}
