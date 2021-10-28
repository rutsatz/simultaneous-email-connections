import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int SIMULTANEOUS_CONNECTIONS = 10;
    private static final ExecutorService POOL = Executors.newFixedThreadPool(SIMULTANEOUS_CONNECTIONS);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < SIMULTANEOUS_CONNECTIONS; i++) {
            POOL.submit(new EmailSenderThread());
        }
        POOL.shutdown();

        if (!POOL.awaitTermination(1L, TimeUnit.MINUTES)) {
            System.err.println("Timeout occurred when ending threads");
        }

    }
}

class EmailSenderThread implements Runnable {

    private static final String USER_EMAIL = "<add your microsoft email>";
    private static final String USER_PASSWORD = "<add email password>";
    private static final String EMAIL_RECIPIENT = "<add destination email>";

    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");

    @Override
    public void run() {
        try {

            int threadNumber = COUNTER.getAndIncrement();

            System.out.printf("%s (%d) - Starting thread%n", now(), threadNumber);

            Email email = new SimpleEmail();
            email.setHostName("smtp.office365.com");

            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(USER_EMAIL, USER_PASSWORD));
            email.setSSLOnConnect(false);
            email.setFrom(USER_EMAIL);
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo(EMAIL_RECIPIENT);

            email.setStartTLSRequired(true);
            email.setStartTLSEnabled(true);

            System.out.printf("%s (%d) - Sending email%n", now(), threadNumber);
            email.send();
            System.out.printf("%s (%d) - Email sent%n", now(), threadNumber);
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    private String now() {
        return LocalTime.now().format(formatter);
    }
}
