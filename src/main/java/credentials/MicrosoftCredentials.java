package credentials;

import java.util.List;

public class MicrosoftCredentials {

    private String hostname = "add data";
    private final String userEmail = "add data";
    private final String username = "add data";
    private final String userPassword = "add data";
    private final Integer smtpPort = 587;
    private final Boolean SSLOnConnect = false;
    private final Boolean startTLSRequired = true;
    private final Boolean startTLSEnabled = true;
    private final List<String> emailRecipientList = List.of("add data");

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
