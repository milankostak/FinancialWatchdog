package hk.edu.cityu.financialwatchdog.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.Date;

/**
 * Created by Milan on 28.7.2016.
 */
public class Item extends SugarRecord {
    private String name;
    private Date time;
    private float latitude;
    private float longitude;
    private double price;
    private Category category;

    public Item() {
    }

    public Item(String name, Date time, float latitude, float longitude, double price, Category category) {
        this.name = name;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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
