package edu.hust.vn.controller;

import edu.hust.vn.contract.IPricing;
import edu.hust.vn.controller.Pricing.Pricing;
import edu.hust.vn.model.rental.Rental;

public class RentBikeController extends BaseController{
    private IPricing pricing = new Pricing();

    public IPricing getPricing() {
        return pricing;
    }

    public void setPricing(IPricing pricing) {
        this.pricing = pricing;
    }
}
