import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class DateClient {
	
	/**
	 *start servr using:
	 * php -S localhost 8080 
	 */

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // server string
        
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = "DATA RECEIVED:";
        do {
            System.out.println(str);
            str = input.readLine();
        } while(str != null);
    }
}
