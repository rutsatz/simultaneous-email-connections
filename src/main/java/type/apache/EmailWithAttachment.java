package type.apache;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.net.MalformedURLException;
import java.net.URL;

public class EmailWithAttachment {

    public Email build() throws EmailException, MalformedURLException {

        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Apache logo");
        attachment.setName("Apache logo");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setSubject("The logo");
        email.setMsg("Here is Apache's logo");

        // add the attachment
        email.attach(attachment);

        return email;
    }

}
