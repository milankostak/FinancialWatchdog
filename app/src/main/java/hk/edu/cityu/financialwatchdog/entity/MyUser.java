package hk.edu.cityu.financialwatchdog.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.util.Iterator;

/**
 * Created by Milan on 27.7.2016.
 */
@Table
public class MyUser {
    private String username;
    private String password;
    private int age;
    @Ignore
    private String bio; //this will be ignored by SugarORM

    public MyUser() { }

    public MyUser(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", bio='" + bio + '\'' +
                '}';
    }

    public static Iterator<MyUser> findAll() {
        return SugarRecord.findAll(MyUser.class);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}