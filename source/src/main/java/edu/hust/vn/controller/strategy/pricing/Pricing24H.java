package edu.hust.vn.controller.strategy.pricing;

public class Pricing24H implements IPricing{
    @Override
    public int getPricing(int time) {
        int hours = time / 3600;
        int minutes = time / 60;
        int total = 200000;
        if ( time < 12 ){
            return total - (12 - hours)*10000;
        } else if( hours > 24 ) {
            return total + (minutes - 24*60)/15*2000;
        }else {
            return total;
        }
    }
}
