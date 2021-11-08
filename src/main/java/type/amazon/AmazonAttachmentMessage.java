package type.amazon;

import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import credentials.AmazonSESCredentials;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Properties;

public class AmazonAttachmentMessage {

    public SendRawEmailRequest build(AmazonSESCredentials credentials) throws MessagingException, IOException {

        String SUBJECT = "Customer service contact info";

        String BODY_TEXT = "Hello,\r\n"
                + "Please see the attached file for a list "
                + "of customers to contact.";

        String BODY_HTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>Hello!</h1>"
                + "<p>Please see the attached file for a "
                + "list of customers to contact.</p>"
                + "</body>"
                + "</html>";

        String ATTACHMENT = "/Users/rutsatz/Downloads/4723250.jpg";

        Session session = Session.getDefaultInstance(new Properties());

        // Create a new MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Add subject, from and to lines.
        message.setSubject(SUBJECT, "UTF-8");
        message.setFrom(new InternetAddress(credentials.getUserEmail()));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(format(credentials.getEmailRecipientList())));

        // Create a multipart/alternative child container.
        MimeMultipart msg_body = new MimeMultipart("alternative");

        // Create a wrapper for the HTML and text parts.
        MimeBodyPart wrap = new MimeBodyPart();

        // Define the text part.
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(BODY_TEXT, "text/plain; charset=UTF-8");

        // Define the HTML part.
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(BODY_HTML, "text/html; charset=UTF-8");

        // Add the text and HTML parts to the child container.
        msg_body.addBodyPart(textPart);
        msg_body.addBodyPart(htmlPart);

        // Add the child container to the wrapper object.
        wrap.setContent(msg_body);


        // Create a multipart/mixed parent container.
        MimeMultipart msg = new MimeMultipart("mixed");

        // Add the parent container to the message.
        message.setContent(msg);

        // Add the multipart/alternative part to the message.
        msg.addBodyPart(wrap);

        // Define the attachment
        MimeBodyPart att = new MimeBodyPart();
        DataSource fds = new FileDataSource(ATTACHMENT);
        att.setDataHandler(new DataHandler(fds));
        att.setFileName(fds.getName());


//        String base64 = "add data";
//        String type = "image/png";
//        byte[] decodedArray = Base64.decodeBase64(base64);

//        MimeBodyPart att = new MimeBodyPart();
//        DataSource fds = new ByteArrayDataSource(decodedArray, type);
//        att.setDataHandler(new DataHandler(fds));
//        att.setFileName("photo.png");


        // Add the attachment to the message.
        msg.addBodyPart(att);

        // Try to send the email.
        try {
            System.out.println("Attempting to send an email through Amazon SES "
                    + "using the AWS SDK for Java...");


            // Print the raw email content on the console
            PrintStream out = System.out;
            message.writeTo(out);

            // Send the email.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage =
                    new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

            return new SendRawEmailRequest(rawMessage);
        } catch (Exception ex) {
            System.out.println("Email Failed");
            System.err.println("Error message: " + ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    private String format(List<String> emails) {
        if (emails.size() == 1) return emails.get(0);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < emails.size() - 1; i++) {
            sb.append(emails.get(i)).append(",");
        }

        sb.append(emails.get(emails.size() - 1));

        return sb.toString();
    }
}
