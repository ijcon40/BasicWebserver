import WebServer.WebServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class test {

    public static void main(String[] args) throws IOException {
        InetAddress Inet = InetAddress.getByName(null);//This sets up localhost, although in theory the packet sender could happen from any place
        PacketSender sender = new PacketSender(Inet, 3127, null, 0, null);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNext()) {
                sender.send_message(scanner.next());
            }
        }
    }
}
