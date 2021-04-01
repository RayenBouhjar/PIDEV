/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author Sadok Laouissi
 */
public class Participation {
    private int id;
    private User user;
    private Event event;
    private Date date;

    public Participation() {
    }

    public Participation(User user, Event event, Date date) {
        this.user = user;
        this.event = event;
        this.date = date;
    }

    public Participation(int id, User user, Event event, Date date) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Participation{" + "id=" + id + ", user=" + user + ", event=" + event + ", date=" + date + '}';
    }
    
    
}
