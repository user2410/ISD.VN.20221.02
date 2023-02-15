import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.PaymentInfoReceiverController;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.controller.strategy.paymentinfo_validation.CardValidationStrategy;
import edu.hust.vn.controller.strategy.paymentinfo_validation.PaymentInfoValidationStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatePaymentInfoTest {
    PaymentInfoValidationStrategy strategy;
    HashMap<String, String> info;

    @BeforeAll
    void setUp(){
        strategy = new CardValidationStrategy();
        info = new HashMap<>();
    }

    @ParameterizedTest
    @CsvSource({
        "987125_group2_2023, Abc Def, 12/12/2023, 123, false",
        "987125_group2-2023, Abc Def, 12/12/2023, 123, true",
        "987125_group2_2023, Abc_Def, 12/12/2023, 123, true",
        "987125_group2_2023, Abc Def, 12/2/2023, 123, true",
        "987125_group2_2023, Abc Def, 12022023, 123, true",
        "987125_group2_2023, Abc Def, 12/02/2023, 1234, true",
    })
    public void test(String cardCode, String cardOwner, String expDate, String cardCvv, boolean successIfException){
        info.put("cardNumber", cardCode);
        info.put("cardOwner", cardOwner);
        info.put("expDate", expDate);
        info.put("cvvCode", cardCvv);
        try{
            strategy.validate(info);
            assertFalse(successIfException);
        }catch (InvalidPaymentInfoException e){
            assertTrue(successIfException);
        }
    }
}
