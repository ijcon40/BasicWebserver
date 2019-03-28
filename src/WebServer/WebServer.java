package WebServer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WebServer implements Runnable {

    static final int port = 3127;
    private Socket client;
    static final boolean verbose = true;

    public WebServer(Socket client) {
        this.client = client;
        Thread responseListener = new Thread(responseHandler);
        responseListener.start();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverConnect = new ServerSocket(port);
        System.out.println("Server started.\nListening for connections on port : " + port + " ...\n");

        while (true) {
            WebServer server = new WebServer(serverConnect.accept());

            if (verbose) {
                System.out.println("Connection opened. (" + new Date() + ")");
            }
            Thread webserver = new Thread(server);
            webserver.start();

        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            out = new PrintWriter(client.getOutputStream());
//            dataOut = new BufferedOutputStream(client.getOutputStream());

            while (true) {
                String input = in.readLine();
                System.out.println("client: "+input);
            }
        } catch (IOException error) {
            System.out.println(error.getMessage());

        }
    }

    public Runnable responseHandler = () -> {
        Scanner console = new Scanner(System.in);
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            while (true) {
                if(console.hasNext()){
                    out.println(console.next());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    };
}
