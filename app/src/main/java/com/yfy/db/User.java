package com.yfy.db;

import org.greenrobot.greendao.annotation.*;

@Entity
public class User {

//            Entity note = schema.addEntity("User");
//        note.addIdProperty();
//        note.addStringProperty("session_key").notNull();
//        note.addIntProperty("class_id").notNull();
//        note.addIntProperty("fxt_id").notNull();
//        note.addStringProperty("head_pic").notNull();
//        note.addStringProperty("user_id").notNull();
//        note.addStringProperty("name").notNull();
//        note.addStringProperty("username").notNull();
//        note.addStringProperty("pass_word").notNull();
//        note.addStringProperty("user_type").notNull();
//        note.addDateProperty("date");
    @Id
    private Long id;
    @NotNull
    private String session_key;//性别
    @NotNull
    private int class_id;//性别
    @NotNull
    private int fxt_id;//性别
    @NotNull
    private String head_pic;//性别
    @NotNull
    private String user_id;//性别
    @NotNull
    private String name;//性别
    @NotNull
    private String username;//性别
    @NotNull
    private String pass_word;//性别
    @NotNull
    private String user_type;//性别

    @Generated(hash = 144744146)
    public User(Long id, @NotNull String session_key, int class_id, int fxt_id,
            @NotNull String head_pic, @NotNull String user_id, @NotNull String name,
            @NotNull String username, @NotNull String pass_word,
            @NotNull String user_type) {
        this.id = id;
        this.session_key = session_key;
        this.class_id = class_id;
        this.fxt_id = fxt_id;
        this.head_pic = head_pic;
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.pass_word = pass_word;
        this.user_type = user_type;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getFxt_id() {
        return fxt_id;
    }

    public void setFxt_id(int fxt_id) {
        this.fxt_id = fxt_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}