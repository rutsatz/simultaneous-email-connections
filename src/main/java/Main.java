import client.AmazonSESClient;
import client.ApacheCommonsEmailClient;
import credentials.AmazonSESCredentials;
import credentials.MicrosoftCredentials;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int SIMULTANEOUS_CONNECTIONS = 1;
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



    // choose credentials
//        MicrosoftCredentials credentials = new MicrosoftCredentials();
    AmazonSESCredentials credentials = new AmazonSESCredentials();

    // choose client
//    ApacheCommonsEmailClient client = new ApacheCommonsEmailClient();
    AmazonSESClient client = new AmazonSESClient();

    @Override
    public void run() {
        try {

            client.send(credentials);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
