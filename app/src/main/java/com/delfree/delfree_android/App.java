package com.delfree.delfree_android;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.delfree.delfree_android.Model.User;

import java.io.File;

/**
 * Created by phephen on 6/8/19.
 */

public class App extends Application {

    private String baseUrl = "";
    boolean login;
    User user;
    File imageFile;
    private Bitmap image;

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

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}


