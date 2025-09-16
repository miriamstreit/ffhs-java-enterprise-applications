package ch.schuum.brewbazaar.controller;

import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.service.BeerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("/")
public class BeerController {

    private final BeerService beerService;

    @Inject
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GET
    public List<Beer> getAllBeers() {
        return beerService.findAllBeers();
    }

    @GET
    @Path("/{id}")
    public Beer getBeerById(@PathParam("id") int id) {
        return beerService.findBeerById(id).orElse(null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Optional<Beer> saveBeer(Beer beer) {
        return beerService.createBeer(beer);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Optional<Beer> saveBeer(@PathParam("id") int id, Beer beer) {
        return beerService.updateBeer(id, beer);
    }

    @DELETE
    @Path("/{id}")
    public int deleteBeer(@PathParam("id") int id) {
        return beerService.deleteBeer(id);
    }

}
