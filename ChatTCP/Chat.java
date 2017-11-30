
package servertcp;

public class Chat {
    
    private int idChat;
    private String nomeChat;

    public Chat(int idChat, String nomeChat, boolean isPrivata) {
        this.idChat = idChat;
        this.nomeChat = nomeChat;
    }
    
    public Chat(){}

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getNomeChat() {
        return nomeChat;
    }

    public void setNomeChat(String nomeChat) {
        this.nomeChat = nomeChat;
    }
}
