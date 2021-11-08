package client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import credentials.AmazonSESCredentials;
import type.amazon.AmazonAttachmentMessage;
import type.amazon.AmazonHTMLMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class AmazonSESClient {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");

    public void send(AmazonSESCredentials credentials) throws MessagingException, IOException {

        int threadNumber = COUNTER.getAndIncrement();

        System.out.printf("%s (%d) - Starting thread%n", now(), threadNumber);

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(credentials.getAccessKey(),
                credentials.getSecretKey());

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.SA_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        System.out.printf("%s (%d) - Sending email%n", now(), threadNumber);

        // Choose the type of email
//        sendNormalEmail(credentials, client);
        sendAttachmentEmail(credentials, client);

        System.out.printf("%s (%d) - Email sent%n", now(), threadNumber);
    }

    private void sendAttachmentEmail(AmazonSESCredentials credentials, AmazonSimpleEmailService client) throws MessagingException, IOException {

        // choose the type of email
        SendRawEmailRequest rawEmailRequest = new AmazonAttachmentMessage().build(credentials);

        client.sendRawEmail(rawEmailRequest);
    }

    private void sendNormalEmail(AmazonSESCredentials credentials, AmazonSimpleEmailService client) {

        // choose the type of email
//        Message message = new AmazonTextMessage().build();
        Message message = new AmazonHTMLMessage().build();

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(credentials.getEmailRecipientList()))
                .withMessage(message)
                .withSource(credentials.getUserEmail());

        client.sendEmail(request);
    }

    private String now() {
        return LocalTime.now().format(formatter);
    }

}
