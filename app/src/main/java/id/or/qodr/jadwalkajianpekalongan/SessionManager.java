package id.or.qodr.jadwalkajianpekalongan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by adul on 17/02/17.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "login";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "uname";

    // User name (make variable public to access from outside)
    public static final String KEY_ID= "id";

    // User name (make variable public to access from outside)
    public static final String KEY_FULNAME = "fulname";

    // Password (make variable public to access from outside)
    public static final String KEY_PASS = "upass";

    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String id, String fulname, String name, String pass) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_ID, id);

        // Storing name in pref
        editor.putString(KEY_FULNAME, fulname);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing pass in pref
        editor.putString(KEY_PASS, pass);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // id
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // full name
        user.put(KEY_FULNAME, pref.getString(KEY_FULNAME, null));

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user pass
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}