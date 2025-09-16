package ch.schuum.brewbazaar.controller;

import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BeerController {
    private final BeerService beerService;


    @GetMapping
    public Iterable<Beer> getAllBeers() {
        return beerService.findAllBeers();
    }

    @GetMapping("/{id}")
    public Beer getBeerById(@PathVariable("id") int id) {
        return beerService.findBeerById(id).orElse(null);
    }

    @PostMapping
    public Beer saveBeer(@RequestBody Beer beer) {
        return beerService.createBeer(beer);
    }

    @PutMapping("/{id}")
    public Beer saveBeer(@PathVariable("id") int id, @RequestBody Beer beer) {
        return beerService.updateBeer(id, beer);
    }

    @DeleteMapping("/{id}")
    public int deleteBeer(@PathVariable("id") int id) {
        return beerService.deleteBeer(id);
    }
}
