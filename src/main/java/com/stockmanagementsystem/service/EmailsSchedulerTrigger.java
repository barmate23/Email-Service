package com.stockmanagementsystem.service;

import com.stockmanagementsystem.repository.EmailsRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.stockmanagementsystem.utils.Constant.SAVE_DAILY_REPORT_CRON_EXPRESSION;

@EnableAsync
@EnableScheduling
@Component
public class EmailsSchedulerTrigger {
    @Autowired
    private CreateAndSendEmailService createAndSendEmailService;
    @Autowired
    private EmailsRepository emailsRepository;
//    @Scheduled(cron = "*/60 * * * * ?")
//    @Scheduled(cron = "0 */1 * * * *")
//    @Scheduled(fixedRate = 1000)


    @Scheduled(cron = SAVE_DAILY_REPORT_CRON_EXPRESSION)
    private  void  dailyReport(){
        try {
           createAndSendEmailService.createEmail(emailsRepository.findByAttemptCountLessThanEqual(3));
        }catch(Exception e){

            throw e;
        }
    }
}
