/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Enrico
 */
public class Chat {
    
    private int idChat;
    private String nomeChat;
    private boolean isPrivata;

    public Chat(int idChat, String nomeChat, boolean isPrivata) {
        this.idChat = idChat;
        this.nomeChat = nomeChat;
        this.isPrivata = isPrivata;
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

    public boolean isIsPrivata() {
        return isPrivata;
    }

    public void setIsPrivata(boolean isPrivata) {
        this.isPrivata = isPrivata;
    }
    
}
