package type.apache;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.net.MalformedURLException;
import java.net.URL;

public class HTMLFormattedEmail {

    public Email build() throws EmailException, MalformedURLException {


        // Create the email message
        HtmlEmail email = new HtmlEmail();

        email.setSubject("Test email with inline image");

        // embed the image and get the content id
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo");

        // set the html message
        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");


        return email;
    }

//    public Email build() throws EmailException, MalformedURLException {
//
//
//        // Create the email message
//        HtmlEmail email = new HtmlEmail();
//
//        email.setSubject("Test email with inline image");
//
//        // embed the image and get the content id
//        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
//        String cid = email.embed(url, "Apache logo");
//
//        // set the html message
//        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");
//
//        // set the alternative message
//        email.setTextMsg("Your email client does not support HTML messages");
//
//
//        return email;
//    }

}
