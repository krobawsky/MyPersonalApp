package com.tecsup.ricardobq.mypersonalapp.datos;

import com.orm.SugarRecord;

import java.util.List;

public class UserRepository {

    public static List<User> list(){
        List<User> users = SugarRecord.listAll(User.class);
        return users;
    }

    public static User read(Long id){
        User user = SugarRecord.findById(User.class, id);
        return user;
    }

    public static void create(String name, int phone, String username, String password){
        User user = new User(name, phone, username, password);
        SugarRecord.save(user);
    }

    public static void update(String name, int phone, String username, String password, Long id){
        User user = SugarRecord.findById(User.class, id);
        user.setName(name);
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        SugarRecord.save(user);
    }

    public static void delete(Long id){
        User user = SugarRecord.findById(User.class, id);
        SugarRecord.delete(user);
    }

}
