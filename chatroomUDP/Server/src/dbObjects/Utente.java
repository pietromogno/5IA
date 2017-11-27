/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbObjects;

import java.net.InetAddress;

/**
 *
 * @author Pagotto Emanuele
 */
public class Utente {
    
    private InetAddress address;
    private int PORT;
    private int id;
    private String name;

    public Utente(InetAddress address, int PORT, int id,String name) {
        this.address = address;
        this.PORT = PORT;
        this.id = id;
        this.name = name;
    }

    //Getters
    public InetAddress getAddress() {
        return address;
    }
    public int getPORT() {
        return PORT;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    
    //Setters
    public void setAddress(InetAddress address) {
        this.address = address;
    }
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
