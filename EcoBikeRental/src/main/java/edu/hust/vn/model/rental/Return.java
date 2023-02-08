package edu.hust.vn.model.rental;

public class Return {
    private static Return instance;

    private Return(){

    }

    public static Return getInstance(){
        if(instance == null){
            synchronized (Rental.class){
                instance = new Return();
            }
        }
        return instance;
    }
}
