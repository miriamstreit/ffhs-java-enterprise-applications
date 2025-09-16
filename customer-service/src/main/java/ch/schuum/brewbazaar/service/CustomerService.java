package ch.schuum.brewbazaar.service;

import ch.schuum.brewbazaar.model.Customer;
import ch.schuum.brewbazaar.repository.CustomerRepository;
import io.micronaut.context.annotation.Prototype;

import java.util.Optional;

@Prototype
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public int findOrCreate(Customer customer) {
        var customerOptional = customerRepository.findByNameIgnoreCaseAndSurnameIgnoreCaseAndStreetIgnoreCase(
                customer.getName(),
                customer.getSurname(),
                customer.getStreet());

        if (customerOptional.isPresent())
            return customerOptional.get().getCustomerId();

        return customerRepository.save(customer).getCustomerId();
    }
}
