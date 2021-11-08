package type.amazon;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Message;

public class AmazonTextMessage {

    public Message build() {

        String TEXTBODY = "This email was sent through Amazon SES "
                + "using the AWS SDK for Java.";

          String SUBJECT = "Amazon SES test (AWS SDK for Java)";

        return new Message()
                .withBody(new Body()
                        .withText(new Content()
                                .withCharset("UTF-8").withData(TEXTBODY)))
                .withSubject(new Content()
                        .withCharset("UTF-8").withData(SUBJECT));
    }

}
