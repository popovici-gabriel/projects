package com.rx.samples.using.rx;

/**
 * In the micro service architecture this is an EdgeService
 */
public class EventDrivenSample {
    public static void main(String[] args) throws InterruptedException {
        /* create the email service */
        EmailService emailService = new EmailServiceImpl();

        /* create the user service */
        UserService userService = new UserServiceImpl();

        /* create an email monitor */
        new EmailMonitorService(emailService, userService);

        /* add a user */
        userService.addUser("V662045", "gabriel.s.popovici@jpmchase.com");

        /* sleep for awhile */
        Thread.sleep(2000);

        /* exit */
        System.exit(0);
    }
}
