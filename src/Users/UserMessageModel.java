/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

public class UserMessageModel {
    String reciever,sender,message;

    UserMessageModel (String reciever, String sender, String message) {
        this.reciever = reciever;
        this.sender = sender;
        this.message = message;
    }

    public String getReciever() {
        return reciever;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
