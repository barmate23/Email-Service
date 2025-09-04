package com.stockmanagementsystem.controller;

import com.stockmanagementsystem.service.CreateAndSendEmailService;
import com.stockmanagementsystem.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({Constant.PREFIX+Constant.USERS_CONTROLLER})
public class GenerateOtpController {
    @Autowired
    CreateAndSendEmailService createAndSendEmailService;



    @GetMapping("/generateOtp")
    public Boolean generateOtp(@RequestParam String type,
                               @RequestParam int userId,
                               @RequestParam String subject,
                               @RequestParam int emailId,
                               @RequestParam int count,
                               @RequestParam String otp){
        return createAndSendEmailService.generateOtp(type,userId,subject,emailId, count, otp);
    }
}
