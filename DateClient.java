import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;

/*
 * start server using:
 * php -S localhost:8080
 */

public class DateClient {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // server string
        
        Socket s = new Socket(serverAddress, 9090);
        /*DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        dOut.writeBytes("GET /index.php HTTP/1.0\n\n"); // minimal GET request*/
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = "DATA RECEIVED:";
        do {
            System.out.println(str);
            str = input.readLine();
        } while(str != null);
    }
}
