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
public class Event {
    private int id;
    private Artist artist;
    private String location;
    private Date date;
    private float price;
    private int capacity;
    private String Name;
    private String Description;
    private int reste;
    public Event() {
    }

    public Event(int id, Artist artist, String location, Date date, float price, int capacity, String Name, String Description) {
        this.id = id;
        this.artist = artist;
        this.location = location;
        this.date = date;
        this.price = price;
        this.capacity = capacity;
        this.Name = Name;
        this.Description = Description;
    }

    public Event(Artist artist, String location, Date date, float price, int capacity, String Name, String Description) {
        this.artist = artist;
        this.location = location;
        this.date = date;
        this.price = price;
        this.capacity = capacity;
        this.Name = Name;
        this.Description = Description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getReste() {
        return reste;
    }

    public void setReste(int reste) {
        this.reste = reste;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", artist=" + artist + ", location=" + location + ", date=" + date + ", price=" + price + ", capacity=" + capacity + ", Name=" + Name + ", Description=" + Description + '}';
    }

    
}
