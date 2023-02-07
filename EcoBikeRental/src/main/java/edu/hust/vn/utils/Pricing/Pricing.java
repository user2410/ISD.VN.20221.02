package edu.hust.vn.utils.Pricing;

import edu.hust.vn.contract.IPricing;

public class Pricing implements IPricing {

    @Override
    public int getPricing(int time) {
        int minutes = time / 60;
        if (minutes <= 10){
            return 0;
        } else if (minutes > 10 && minutes < 40 ) {
            return 10000;
        }else {
            return 10000 + (minutes - 40) / 15 * 3000 + 3000;
        }
    }
}
