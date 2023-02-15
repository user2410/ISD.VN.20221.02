package edu.hust.vn.controller.strategy.deposit_calculator;

public class DepositCalculator1 implements DepositCalculatingStrategy {
    @Override
    public int getDeposit(int price) {
        return ((int) (price*0.4));
    }
}
