package eu.airaudio.airplay;

import eu.airaudio.airplay.auth.AirPlayAuth;
import eu.airaudio.airplay.auth.AuthUtils;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Martin on 08.05.2017.
 */
public class AirPlayAuthExample {

    // Generated via {@code AirPlayAuth.generateNewAuthToken()}
    private static final String STORED_AUTH_TOKEN = "qnn1i23srq5m8giv1nagsvojnk@302e020100300506032b657004220420eb92ab919f68cc716f7f85a609531c3de74f87c9f1c9007c35516b4f5ef1fa25";

    public static final String IP = "192.168.1.141";
    public static final int PORT = 7000;

    public static void main(String[] args) throws Exception {

        System.out.println("Used AuthKey: " + STORED_AUTH_TOKEN);
        AirPlayAuth airPlayAuth = new AirPlayAuth(new InetSocketAddress(IP, PORT), STORED_AUTH_TOKEN);
        Socket socket;
        try {
            socket = airPlayAuth.authenticate();
        } catch (Exception e) {
            System.out.println("Authentication failed - start pairing..");

            airPlayAuth.startPairing();

            System.out.println("Enter PIN:");
            Scanner scan = new Scanner(System.in);
            String pin = scan.nextLine().trim();

            airPlayAuth.doPairing(pin);

            socket = airPlayAuth.authenticate();
        }


        String content = "Content-Location: http://techslides.com/demos/sample-videos/small.mp4\r\n" +
                "Start-Position: 0.0\r\n";

        AuthUtils.postData(socket, "/play", "text/parameters", content.getBytes("UTF-8"));

        Thread.sleep(10000);

        socket.close();

    }


}
