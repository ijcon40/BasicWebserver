import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class PacketSender {

    private InetAddress ip;
    private int port;
    private String ip_redirect;
    private int port_redirect;
    private String dump_path;
    private boolean open;
    private Socket connection;
    private InputStream inputStream;
    private OutputStream outputStream;


    public PacketSender(InetAddress ip, int port, String ip_redirect, int port_redirect, String dump_path){
        this.ip = ip;
        this.port = port;
        this.ip_redirect = ip_redirect;
        this.port_redirect = port_redirect;
        this.dump_path = dump_path!=null?dump_path:"packet_log.txt";
        try {
            this.connection = new Socket(ip, port);
            this.inputStream = connection.getInputStream();
            this.outputStream = connection.getOutputStream();
        }
        catch(IOException e){}
        initializeListener();

    }

    public void initializeListener(){
        Thread responseListener = new Thread(responseHandler);
        responseListener.start();
    }


    public void send_message(String message){
        PrintWriter send_message = new PrintWriter(this.outputStream, true);
        send_message.println(message);
        send_message.flush();
    }

    private Runnable responseHandler = ()->{
      BufferedReader read = new BufferedReader(new InputStreamReader(this.inputStream));
      while(true){
          try {
              String response = read.readLine();
              System.out.println("server: "+response);
          }
          catch(IOException e){
              System.out.println(e.getMessage());
          }
      }
    };
}
