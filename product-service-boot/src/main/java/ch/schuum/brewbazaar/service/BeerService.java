package ch.schuum.brewbazaar.service;

import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;

   public Iterable<Beer> findAllBeers() {
        return beerRepository.findAll();
    }
    public Optional<Beer> findBeerById(int id) {
        return beerRepository.findById(id);
    }

    public Beer createBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    public Beer updateBeer(int id, Beer beer) {
        return beerRepository.save(beer);
    }

    public int deleteBeer(int id) {
        beerRepository.deleteById(id);
        return id;
    }


}
