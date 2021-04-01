/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Sadok Laouissi
 */
public class SmtpAuth extends Authenticator{
    public SmtpAuth() {

    super();
}

@Override
public PasswordAuthentication getPasswordAuthentication() {
 String username = "trackhub22@gmail.com";
 String password = "pidevpidev";
    if ((username != null) && (username.length() > 0) && (password != null) 
      && (password.length   () > 0)) {

        return new PasswordAuthentication(username, password);
    }

    return null;}

}
