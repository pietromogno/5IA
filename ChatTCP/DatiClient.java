/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Piergiorgio
 */
class DatiClient {
    
    String username;
    int socketNumb;
    DatiClient d;
    DataInputStream in;
    DataOutputStream out;
    
    DatiClient(String username, int socketNumb, DataInputStream in, DataOutputStream out){
        setUsername(username);
        setSocket(socketNumb);
        setIn(in);
        setOut(out);
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public String getUsername() {
        return username;
    }

    public int getSocketNumb() {
        return socketNumb;
    }
  
    private void setUsername(String username) {
        this.username = username;
    }

    private void setSocket(int socketNumb) {
        this.socketNumb = socketNumb;
    }
    
    public DatiClient getDatiClient(){  
        d.setSocket(getSocketNumb());
        d.setUsername(getUsername());
        d.setIn(getIn());
        d.setOut(getOut());
        return d; 
    }
    
}
