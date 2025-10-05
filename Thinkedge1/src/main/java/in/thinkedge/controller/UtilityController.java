 package in.thinkedge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import in.thinkedge.service.UtilityService;

@RestController
public class UtilityController {

    @Autowired
    private UtilityService utilityService;

    @GetMapping("/generate-otp")
    public String generateOtp() {
        return "Generated OTP: " + utilityService.getGeneratedOtp();
    }

    @GetMapping("/generate-product-id")
    public String generateProductId() {
        return "Generated Product ID: " + utilityService.getGeneratedProductId();
    }
}
