import edu.hust.vn.controller.ViewDockController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardBike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BarCodeValidationTest {
    final String barCode = "853134a8-6c80-4ac9-9bb9-9899a01a4356";
    Bike bike;
    ArrayList<Dock> docks;
    Lock lock;
    ViewDockController ctl;

    @BeforeEach
    void setUp(){
        bike = new StandardBike();
        docks = new ArrayList<>(2);
        docks.add(new Dock());
        docks.add(new Dock());
        lock  = new Lock();
        lock.setBarCode(barCode);
        docks.get(0).setLocks(Arrays.asList(lock));
        ctl = new ViewDockController();
    }

    final String test0 = barCode+", 0, false";
    final String test1 = barCode+", 1, true";

    @ParameterizedTest
    @CsvSource({
        test0,
        test1,
        "123"+", 0, true",
        "853134a8-6c80-4ac9-9bb9-9899a01a435m"+", 0, true",
        "853134a8-6c80-4ac9-9bb9-9899a01a4352"+", 0, true",
    })
    void test(String barCode, int dockIdx, boolean successIfException){
        try{
            ctl.validateBarCode(docks.get(dockIdx), barCode);
            assertFalse(successIfException);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue(successIfException);
        }
    }

}
