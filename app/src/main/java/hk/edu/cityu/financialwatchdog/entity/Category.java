package hk.edu.cityu.financialwatchdog.entity;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Milan on 28.7.2016.
 */
public class Category extends SugarRecord {
    private String name;
    private String color;
    private int moneyLimit;

    public static void createMockCategories() {
        new Category("Food", "#00FF00", 5000).save();
        new Category("Transportation", "#FF0000", 2000).save();
        new Category("Fun", "#0000FF", 1000).save();
        new Category("Clothes", "#FFFF00", 1000).save();
        new Category("Culture", "#00FFFF", 500).save();
        new Category("Household", "#FF00FF", 1500).save();
    }

    public Category() {
    }

    public Category(String name, String color, int moneyLimit) {
        this.name = name;
        this.color = color;
        this.moneyLimit = moneyLimit;
    }

    public List<Item> getItems() {
        return Item.find(Item.class, "category = ?", new String[]{Long.toString(getId())} );
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", moneyLimit=" + moneyLimit +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
