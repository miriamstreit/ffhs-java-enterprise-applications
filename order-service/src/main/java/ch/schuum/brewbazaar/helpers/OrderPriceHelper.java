package ch.schuum.brewbazaar.helpers;

import ch.schuum.brewbazaar.model.Beer;
import ch.schuum.brewbazaar.model.BeerOrder;
import ch.schuum.brewbazaar.model.Order;

import java.util.List;

public class OrderPriceHelper {
    public static double getPriceForOrderWithoutTax(Order order, List<Beer> beers) {
        return beers.stream()
                .mapToDouble(beer -> {
                    double amount = order.getBeerOrder().stream()
                            .filter(x -> x.getBeerId() == beer.getBeerId())
                            .mapToDouble(BeerOrder::getAmount)
                            .findFirst()
                            .orElse(0.0);
                    return beer.getPrice() * amount;
                })
                .sum();
    }
}

