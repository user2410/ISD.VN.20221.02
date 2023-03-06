import edu.hust.vn.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertSame;

public class BarCodeValidationTest {
    @ParameterizedTest
    @CsvSource({
        "9780141026626, true",
        "9780141026627, false",
        "5901234123457, true",
        "7351234123458, true",
        "8712345123451, true",
        "5012345123455, true",
        "7612345123455, true",
        "4261234123453, true",
        "5701234123453, true",
        "8851234123452, true",
        "4012345123456, true",
        "6401234123451, true",
        "9251234123459, true",
        "2681234123451, true",
        "8192345123451, true",
        "3371234123450, true",
        "5471234123455, true",
        "9581234123457, true",
        "1832345123453, true",
        "7751234123456, true",
        "1061234123452, true",
        "3201234123450, true",
        "5021234123452, true",
        "6182345123454, true",
        "9431234123455, true",
        "1561234123457, true",
        "6591234123459, true",
        "7621234123452, true",
        "3782345123459, true",
        "8912345123455, true",
        "2141234123450, true",
        "7031234123459, true",
        "4851234123456, true",
        "1212345123453, true",
        "3561234123455, true",
        "7192345123452, true",
        "8301234123452, true",
        "2461234123459, true",
        "6312345123455, true",
        "9541234123451, true",
        "5821234123458, true",
        "4132345123451, true",
        "8871234123450, true",
        "2951234123455, true",
        "6781234123454, true",
        "9471234123451, true",
        "5101234123451, true",
        "123, false",
        "978014102662, false",
        "978-01410a6626, false",
        "97801410a6626, false",
    })
    void test(String barCode, boolean success){
        boolean actual = Utils.validateBarcode(barCode);
//        System.out.print(String.format("\"%s\", ", barCode.substring(0, 12)));
        assertSame(success, actual);
    }
}
