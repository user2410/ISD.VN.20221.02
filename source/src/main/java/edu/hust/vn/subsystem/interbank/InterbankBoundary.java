package edu.hust.vn.subsystem.interbank;

import edu.hust.vn.common.exception.subsystem.interbank.UnrecognizedException;
import edu.hust.vn.utils.API;

public class InterbankBoundary {
    String query(String url, String data) {
        String response = null;
        try {
//            response = API.post(url, data);
        } catch (Exception e) {
            throw new UnrecognizedException();
        }
        return null;
    }
}
