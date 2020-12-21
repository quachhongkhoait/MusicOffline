package com.cj.musicoffline.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SHARED_PREFERENCES_NAME = "PREFERENCE_FILE_KEY";
    private static final String KEY_INSERT_FAVOURITE = "key_insert_favourite";

    private static final String KEY_SAVE_NAME = "key_save_name";
    private static final String KEY_SAVE_CHECK = "key_save_check";
    private static final String KEY_LOGIN = "islogin";
    private static final String KEY_ROLE = "key_role";
    private static final String KEY_UPDATE_PLAYLIST = "key_update_playlist";
    private static final String KEY_UPDATE_VOLUME = "key_update_volum";


    private static SessionManager sInstance;

    private SharedPreferences sharedPref;

    public synchronized static SessionManager getInstance() {//kiểm
        if (sInstance == null) {
            sInstance = new SessionManager();
        }
        return sInstance;
    }

    private SessionManager() {
        // no instance
    }

    public void init(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    //change volume
    public void setKeyUpdateVolume(Boolean b) {
        sharedPref.edit().putBoolean(KEY_UPDATE_VOLUME, b).apply();
    }

    public Boolean getKeyUpdateVolume() {
        return sharedPref.getBoolean(KEY_UPDATE_VOLUME, true);
    }

    //insert favourite
    public void setKeyUpdatePlaylist(Boolean b) {
        sharedPref.edit().putBoolean(KEY_UPDATE_PLAYLIST, b).apply();
    }

    public Boolean getKeyUpdatePlaylist() {
        return sharedPref.getBoolean(KEY_UPDATE_PLAYLIST, false);
    }

    //insert favourite
    public void setKeyInsertFavourite(Boolean b) {
        sharedPref.edit().putBoolean(KEY_INSERT_FAVOURITE, b).apply();
    }

    public Boolean getKeyInsertFavourite() {
        return sharedPref.getBoolean(KEY_INSERT_FAVOURITE, true);
    }

    //check chuc vu
    public void setKeyRole(String role) {
        sharedPref.edit().putString(KEY_ROLE, role).apply();
    }

    public String getKeyRole() {
        return sharedPref.getString(KEY_ROLE, "");
    }

    //check login
    public void setKeyLogin(boolean islogin) {
        sharedPref.edit().putBoolean(KEY_LOGIN, islogin).apply();
    }

    //    public void getKeyLogin(boolean islogin) {
//        sharedPref.edit().putBoolean(KEY_LOGIN,islogin).apply();
//    }
    public boolean CheckKeyLogin() {
        return sharedPref.getBoolean(KEY_LOGIN, false);
    }

    /**
     * Set key save name
     */
    public void setKeySaveName(String name) {
        sharedPref.edit().putString(KEY_SAVE_NAME, name).apply();
    }

    public String getKeySaveName() {
        return sharedPref.getString(KEY_SAVE_NAME, "");
    }

    /**
     * Set key save checkin/out
     */
    public void setKeySaveCheck(Boolean check) {
        sharedPref.edit().putBoolean(KEY_SAVE_CHECK, check).apply();
    }

    public Boolean getKeySaveCheck() {
        return sharedPref.getBoolean(KEY_SAVE_CHECK, false);
    }

    public boolean CheckKeyInOut() {
        return sharedPref.getBoolean(KEY_SAVE_CHECK, false);
    }

    /**
     * clear share
     */
    public void clear() {
        sharedPref.edit().clear().apply();
    }
}