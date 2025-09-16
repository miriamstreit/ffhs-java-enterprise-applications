package ch.schuum.brewbazaar.controller;

import ch.schuum.brewbazaar.model.Customer;
import ch.schuum.brewbazaar.service.CustomerService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Get
    public Iterable<Customer> getAll() {
        return customerService.findAll();
    }

    @Get("/{id}")
    public Customer getById(int id) {
        return customerService.findById(id).orElse(null);
    }

    @Post("/findOrCreateUser")
    public int findOrCreateUser(@Body Customer customer) {
        return customerService.findOrCreate(customer);
    }
}
