 package in.thinkedge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.thinkedge.utility.IDUtility;
import in.thinkedge.utility.OTPUtility;

@Service
public class UtilityService {

    @Autowired
    private OTPUtility otpUtility;

    public String getGeneratedOtp() {
        return otpUtility.generateOtp();
    }

    public String getGeneratedProductId() {
        return IDUtility.generateProdId();
    }
}
