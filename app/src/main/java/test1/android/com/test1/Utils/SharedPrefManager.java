package test1.android.com.test1.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.sql.Timestamp;

import test1.android.com.test1.Models.AccessToken;

public class SharedPrefManager {
    public static final String SP_TEST1_APP = "spTest1App";
    public static final String SP_TOKEN = "spToken";

    public static final String SP_ID = "spID";
    public static final String SP_EMAIL = "spEmail";

    public static final String SP_LOGIN = "spLogin";


    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_TEST1_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveToken(String keyToken, String value){
        spEditor.putString(keyToken, value);
        spEditor.commit();
    }

    public void saveID(String keyID, String idToken){
        spEditor.putString(keyID, idToken);
        spEditor.commit();
    }

    /**
     * Clear token
     */
//    public void clearToken(){
//        spEditor.remove(SP_TOKEN).commit();
//    }

    public void clearToken(){
        //spEditor.clear().commit();
        spEditor.remove(SP_TOKEN).commit();
    }

    public void clearSession(){
        spEditor.clear();
        spEditor.commit();
    }

    public String getSPToken(){
        return sp.getString(SP_TOKEN, null);
    }
    public String getSPID(){
        return sp.getString(SP_ID, null);
    }


    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public AccessToken getToken(){
        AccessToken token = new AccessToken();
        token.setAccessToken(sp.getString("ACCESS_TOKEN", null));
        token.setRefreshToken(sp.getString("REFRESH_TOKEN", null));
        return token;
    }



}
