package credentials;

import java.util.List;

public class AmazonSESCredentials {

    private final String userEmail = "add data";
    private final String username = "add data";
    private final String userPassword = "add data";
    private final Integer smtpPort = 587;
    private final Boolean SSLOnConnect = true;
    private final Boolean startTLSRequired = true;
    private final Boolean startTLSEnabled = true;
    private final List<String> emailRecipientList = List.of("add data");

    // Amazon Specific
    private final String accessKey = "add data";
    private final String secretKey = "add data";
    private final String hostname = "add data";

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getHostname() {
        return hostname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public Boolean getSSLOnConnect() {
        return SSLOnConnect;
    }

    public Boolean getStartTLSRequired() {
        return startTLSRequired;
    }

    public Boolean getStartTLSEnabled() {
        return startTLSEnabled;
    }

    public List<String> getEmailRecipientList() {
        return emailRecipientList;
    }

}
