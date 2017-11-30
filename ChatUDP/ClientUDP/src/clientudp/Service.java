/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

/**
 *
 * @author simone.pasutti
 */
public class Service {

    private Sender send;

    Service() {
        send = new Sender();
        new Thread(new Receiver()).start();
    }


    void sendMessage(String messaggio,String nomeUtente) {
        send.run(new Messaggio(nomeUtente, messaggio));
    }
    
    static void messageRecived(Messaggio msg){
        Client.setMessage(msg.getNomeUtente(),(String)msg.getMessaggio());
    }
}
