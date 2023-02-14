package edu.hust.vn.controller;

import edu.hust.vn.controller.strategy.pricing.IPricing;
import edu.hust.vn.controller.strategy.pricing.Pricing;

public class ViewBikeController extends BaseController {
    IPricing priceCalculatingStrategy;

    public ViewBikeController(){
        priceCalculatingStrategy = new Pricing();
    }

    public IPricing getPriceCalculatingStrategy() {
        return priceCalculatingStrategy;
    }

    public void setPriceCalculatingStrategy(IPricing priceCalculatingStrategy) {
        this.priceCalculatingStrategy = priceCalculatingStrategy;
    }
}
