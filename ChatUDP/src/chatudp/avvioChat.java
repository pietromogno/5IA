package chatudp;

public class avvioChat {

    public static void main(String args[]) {

        ServerMulticast server = new ServerMulticast();
        ClientUDP c1 = new ClientUDP();
        ClientUDP c2 = new ClientUDP();
        ClientUDP c3 = new ClientUDP();

        new Thread(server).start();

        new Thread(c1).start();
        new Thread(c3).start();
        new Thread(c2).start();
    }
}
