package ch.schuum.brewbazaar.service;

import ch.schuum.brewbazaar.helpers.OrderPriceHelper;
import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.model.BeerOrder;
import ch.schuum.brewbazaar.model.Order;
import ch.schuum.brewbazaar.repository.BeerRepository;
import ch.schuum.brewbazaar.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BeerRepository beerRepository;

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        var beerIds = order.getBeerOrder()
                .stream()
                .map(BeerOrder::getBeerId)
                .toList();

        var beers = (List<Beer>) beerRepository.findAllById(beerIds);
        var price = OrderPriceHelper.getPriceForOrderWithoutTax(order, beers);
        order.setPrice(price);

        return orderRepository.save(order);
    }
    
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }


}
