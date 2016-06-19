package com.rx.samples.using.rx;

import rx.Observer;

import java.util.ArrayList;
import java.util.List;

public class EmailMonitorService {
    private final EmailService emailService;
    private final UserService userService;

    public EmailMonitorService(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;

        /* subscribe to User Events */
        userService.subsribeToUserEvents(new Observer<UserEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserEvent userEvent) {
                handleUserEvents(userEvent);
            }
        });
    }


    private void handleUserEvents(UserEvent userEvent) {
        System.out.println("<< handleUserEvents in thread:" + Thread.currentThread().getName());
        List<String> recipients = new ArrayList<>();
        recipients.add(userEvent.getEmailAddress());
        String text = "Hello ! This is a new rx java sample";
        emailService.sendEmail(recipients, "popovici.gabriel@gmail.com", "RxJava", text);
    }
}
