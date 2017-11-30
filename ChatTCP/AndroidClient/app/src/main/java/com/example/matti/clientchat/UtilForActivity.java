package com.example.matti.clientchat;

import java.io.Serializable;
import oggetti.Messaggio;

/**
 * Created by MATTI on 19/11/2017.
 */

public class UtilForActivity implements Serializable {

    private ServiceClass service;
    private Messaggio msg;

    UtilForActivity(ServiceClass service, Messaggio msg) {
        this.service = service;
        this.msg = msg;
    }

    protected ServiceClass doConnection() {
        return new ServiceClass();
    }

    protected Messaggio getMessaggio() {
        return this.msg;
    }
}
