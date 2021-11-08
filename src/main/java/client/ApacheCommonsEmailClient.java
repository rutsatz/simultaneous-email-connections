package client;

import credentials.MicrosoftCredentials;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import type.apache.EmailWithAttachment;
import type.apache.HTMLFormattedEmail;

import java.net.MalformedURLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class ApacheCommonsEmailClient {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");

    public void send(MicrosoftCredentials credentials) throws EmailException, MalformedURLException {

        int threadNumber = COUNTER.getAndIncrement();

        System.out.printf("%s (%d) - Starting thread%n", now(), threadNumber);

        // choose the type of email
//            Email email = new SimpleEmail().build();
//            Email email = new InlineImageEmail().build();
//        Email email = new HTMLFormattedEmail().build();
            Email email = new EmailWithAttachment().build();


        email.setHostName(credentials.getHostname());

        email.setSmtpPort(credentials.getSmtpPort());
        email.setAuthenticator(new DefaultAuthenticator(credentials.getUsername(), credentials.getUserPassword()));
        email.setSSLOnConnect(credentials.getSSLOnConnect());
        email.setFrom(credentials.getUserEmail());


        for (String emailStr : credentials.getEmailRecipientList()) {
            email.addTo(emailStr);
        }

        email.setStartTLSRequired(credentials.getStartTLSRequired());
        email.setStartTLSEnabled(credentials.getStartTLSEnabled());


        System.out.printf("%s (%d) - Sending email%n", now(), threadNumber);
        email.send();
        System.out.printf("%s (%d) - Email sent%n", now(), threadNumber);
    }

    private String now() {
        return LocalTime.now().format(formatter);
    }

}
