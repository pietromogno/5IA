package chats;

public class Chat {

    public static void main(String args[]) {

        Server server = new Server();
        Client c1 = new Client();
        Client c2 = new Client();
        Client c3 = new Client();

        new Thread(server).start();

        new Thread(c1).start();
        new Thread(c3).start();
        new Thread(c2).start();
    }
}
