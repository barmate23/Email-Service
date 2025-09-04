package com.stockmanagementsystem.utils;

public class Constant {

    public static final String PREFIX = "/api/EmailService/v1/";
    public static final String CONTROLLER_NAME="/emailDelivery";

    public static final String SAVE_DAILY_REPORT="/saveDailyReport";
    public static final String SAVE_WEEKLY_REPORT="/saveWeeklyReport";
    public static final String PENDING_TASK_REPORT="/pendingTasksReport";
    public static final String MILESTONE_ENDING="/milestoneEnding";
    public static final String MILESTONE_MISSED="/milestoneMissed";
    public static final String CREATE_AND_SEND_MAIL="/createAndSendMail";



    // // second, minute, hour, day of month, month, day(s) of week

        public static  final  String  SAVE_DAILY_REPORT_CRON_EXPRESSION = "0 */2 * * * *";
    public static  final  String  SAVE_WEEKLY_REPORT_CRON_EXPRESSION = "0 0 19 * * 5";
    public static  final  String  PENDING_TASK_REPORT_CRON_EXPRESSION = "0 0 9 * * 1-5";
    public static final String MILESTONE_ENDING_CRON_EXPRESSION ="0 0 11 * * *";
    public static final String MILESTONE_MISSED_CRON_EXPRESSION ="0 0 11 * * *";
    public static final String CREATE_AND_SEND_MAIL_CRON_EXPRESSION="0 0 18 * * *";
    public static final String USERS_CONTROLLER = "massage";


    //    public static  final  String  SAVE_DAILY_REPORT_CRON_EXPRESSION = "0 */1 * * * *";

    //    public static  final  String  SAVE_WEEKLY_REPORT_CRON_EXPRESSION = "* * * * * *";
//    public static  final  String  PENDING_TASK_REPORT_CRON_EXPRESSION = "* * * * * * ";
//    public static final String MILESTONE_ENDING_CRON_EXPRESSION ="*/8 * * * * *";
//    public static final String MILESTONE_MISSED_CRON_EXPRESSION ="*/8 * * * * *";
//    public static final String CREATE_AND_SEND_MAIL_CRON_EXPRESSION="*/8 *  * * * *";
//

}
