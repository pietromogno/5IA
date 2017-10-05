import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class DateClient {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // server string
        
        Socket s = new Socket(serverAddress, 80);
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        dOut.write("GET /date.php HTTP/1.0\n\n".getBytes()); // GET di HTTP
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = "DATA RECEIVED:";
        do {
            System.out.println(str);
            str = input.readLine();
        } while(str != null);
    }
}
