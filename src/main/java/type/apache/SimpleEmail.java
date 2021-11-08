package type.apache;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

public class SimpleEmail {

    public Email build() throws EmailException {
        Email email = new org.apache.commons.mail.SimpleEmail();

        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");

        return email;
    }

}
