package hk.edu.cityu.financialwatchdog.entity;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Milan on 28.7.2016.
 * Entity and object for accessing categories data
 */
public class Category extends SugarRecord {
    private String name;
    private int color;
    private long moneyLimit;

    public Category() {
    }

    public Category(String name, int color, long moneyLimit) {
        this.name = name;
        this.color = color;
        this.moneyLimit = moneyLimit;
    }

    public static Category findByName(String catName) {
        List<Category> categories = find(Category.class, "name = ?", catName);
        if (categories.size() > 0) {
            return categories.get(0);
        } else {
            return new Category();
        }
    }

    public static List<Category> listAll() {
        return listAll(Category.class);
    }

    public static List<String> getAllNames() {
        Iterator<Category> categories = findAll(Category.class);
        List<String> names = new ArrayList<>();
        while (categories.hasNext()) {
            names.add(categories.next().name);
        }
        return names;
    }

    public List<Item> getItems() {
        return Item.find(Item.class, "category = ?", new String[]{Long.toString(getId())} );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (color != category.color) return false;
        if (moneyLimit != category.moneyLimit) return false;
        return name != null ? name.equals(category.name) : category.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + color;
        result = 31 * result + (int) (moneyLimit ^ (moneyLimit >>> 32));
        return result;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(long moneyLimit) {
        this.moneyLimit = moneyLimit;
    }
}
