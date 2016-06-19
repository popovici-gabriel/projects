package com.rx.samples.using.rx;

import java.util.List;

public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(List<String> recipients, String fromEmail, String subject, String text) {
        System.out.println();
        System.out.println("-----------------------------------------------------------");
        System.out.println("Sending Email >>");
        System.out.println("-----------------------------------------------------------");

        System.out.print("To      :");
        for (String nextEmail : recipients) {
            System.out.print(nextEmail);
            System.out.print(";");
        }
        System.out.println();

        System.out.println("From    :" + fromEmail);
        System.out.println("Subject :" + subject);
        System.out.println();
        System.out.println("Text    :" + text);
        System.out.println();
        System.out.println("-----------------------------------------------------------");

    }
}
