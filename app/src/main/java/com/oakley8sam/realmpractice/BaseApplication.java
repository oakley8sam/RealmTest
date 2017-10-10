package com.oakley8sam.realmpractice;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by oakle on 10/9/2017.
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("mrrealm.realm").build();
        Realm.setDefaultConfiguration(config);
    }

}
