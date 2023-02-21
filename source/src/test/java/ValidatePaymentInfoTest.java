import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.model.payment.PaymentCreditCard;
import edu.hust.vn.model.payment.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatePaymentInfoTest {
    PaymentMethod paymentMethod;
    HashMap<String, String> info;

    @BeforeEach
    void setUp(){
        paymentMethod = new PaymentCreditCard();
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
        info.put("cardCode", cardCode);
        info.put("cardOwner", cardOwner);
        info.put("expDate", expDate);
        info.put("cvvCode", cardCvv);
        try{
            paymentMethod.createPaymentEntity(info);
            assertFalse(successIfException);
        }catch (InvalidPaymentInfoException e){
            assertTrue(successIfException);
        }
    }
}
