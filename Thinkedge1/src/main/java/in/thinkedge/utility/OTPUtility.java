 package in.thinkedge.utility;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class OTPUtility {

    public String generateOtp() {
        Random random = new Random();
        int randomnumber = random.nextInt(999999);
        String output = Integer.toString(randomnumber);
        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }
}
