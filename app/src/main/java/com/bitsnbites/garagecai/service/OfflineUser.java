package com.bitsnbites.garagecai.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

public class OfflineUser {


    public static void setUser(Context context, String key,
                                       Object value) {

        SharedPreferences sharedPreferences =  context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    public static Object getUser(Context context, String key) {

         SharedPreferences sharedPreferences = context.getSharedPreferences(
                           context.getPackageName(), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        Object obj = gson.fromJson(json, Object.class);
        return new Gson().fromJson(obj.toString(), User.class);
    }
}