package hk.edu.cityu.financialwatchdog.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Milan on 28.7.2016.
 * Entity and object for accessing categories data
 */
public class Category extends SugarRecord {
    @Unique
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

    // DAO methods - Start
    public static Category findByName(String catName) {
        List<Category> categories = find(Category.class, "name = ?", catName);
        if (categories.size() > 0) {
            return categories.get(0);
        } else {
            return new Category();
        }
    }

    public static Iterator<Category> getAll() {
        Iterator<Category> categories = Category.findAll(Category.class);
        return categories;
    }

    public static List<String> getAllNames() {
        Iterator<Category> categories = getAll();
        List<String> names = new ArrayList<>();
        while (categories.hasNext()) {
            names.add(categories.next().name);
        }
        return names;
    }

    public List<Item> getItems() {
        return Item.find(Item.class, "category = ?", new String[]{Long.toString(getId())} );
    }

    // DAO methods - END

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
