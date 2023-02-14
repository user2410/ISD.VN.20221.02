package edu.hust.vn.controller.strategy.pricing;

public class Pricing implements IPricing{
    @Override
    public int getPricing(int time) {
        int minutes = time;
        if (minutes <= 10){
            return 0;
        } else if (minutes > 10 && minutes < 40 ) {
            return 10000;
        }else {
            return 10000 + (minutes - 40) / 15 * 3000 + 3000;
        }
    }
}
