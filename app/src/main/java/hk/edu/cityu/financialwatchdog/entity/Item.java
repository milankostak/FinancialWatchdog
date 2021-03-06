package hk.edu.cityu.financialwatchdog.entity;

import android.location.Location;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Milan on 28.7.2016.
 * Entity and object for accessing items data
 */
public class Item extends SugarRecord {
    private String subject;
    private Date time;
    private double latitude;
    private double longitude;
    private double price;
    private Category category;

    public Item() {
    }

    public Item(String subject, Date time, double latitude, double longitude, double price, Category category) {
        this.subject = subject;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
    }

    public Item(String name, Date time, Location location, double price, Category category) {
        this(name, time, location == null ? 0 : location.getLatitude(), location == null ? 0 : location.getLongitude(), price, category);
    }

    public static List<Item> listAll() {
        return listAll(Item.class);
    }

    @Override
    public String toString() {
        return "Item{" +
                "subject='" + subject + '\'' +
                ", time=" + time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
