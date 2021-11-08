package type.amazon;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Message;

public class AmazonHTMLMessage {

    public Message build() {
        String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
                + "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
                + "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>"
                + "AWS SDK for Java</a>";


        String SUBJECT = "Amazon SES HTML test (AWS SDK for Java)";

        return new Message()
                .withBody(new Body()
                        .withHtml(new Content()
                                .withCharset("UTF-8").withData(HTMLBODY)))
                .withSubject(new Content()
                        .withCharset("UTF-8").withData(SUBJECT));
    }

}
