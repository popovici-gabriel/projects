package com.rx.samples.using.rx;

import java.util.List;

/**
 * Main Email Service
 */
public interface EmailService {

    /**
     * Send an email out.
     *
     * @param recipients List of recipients
     * @param fromEmail  from email address
     * @param subject    subject
     * @param text       of the email body
     */
    void sendEmail(List<String> recipients, String fromEmail, String subject, String text);
}
