package com.delfree.delfree_android;

import android.app.Application;

public class App extends Application {

    private String baseUrl = "";
    boolean login;
    User user;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
