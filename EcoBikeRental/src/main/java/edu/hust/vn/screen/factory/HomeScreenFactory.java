package edu.hust.vn.screen.factory;

import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.utils.Configs;

import java.io.IOException;

public class HomeScreenFactory {
    private static HomeScreenHandler homeScreenHandler;

    public static HomeScreenHandler getInstance() throws IOException {
        if(homeScreenHandler == null){
            synchronized (HomeScreenHandler.class){
                if(homeScreenHandler == null){
                    homeScreenHandler = new HomeScreenHandler();
                }
            }
        }
        return homeScreenHandler;
    }



}
