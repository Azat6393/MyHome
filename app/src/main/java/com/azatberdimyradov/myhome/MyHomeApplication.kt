package com.azatberdimyradov.myhome

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class MyHomeApplication: Application(){

    override fun onCreate() {
        super.onCreate()
/*
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("my_home.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)*/
    }
}