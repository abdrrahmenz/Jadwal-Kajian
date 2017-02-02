package id.or.qodr.jadwalkajianpekalongan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by adul on 24/01/17.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private ProgressDialog progressDialog;

    public void setToolbar(){
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }catch (Exception e){
            Log.e(TAG, "setToolbar: ", e);
        }
    }

    @Override protected void onStart() {
        super.onStart();
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
    }

    protected void showProgress(){
        if (!progressDialog.isShowing()){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
    }

    protected void hideProgress(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void setActiveFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void open(Class clazz){
        startActivity(new Intent(this, clazz));
    }
    public String getString(EditText editText){
        return editText.getText().toString();
    }


    public void showSnack(String message) {
        Snackbar.make(null, message, Snackbar.LENGTH_SHORT).show();
    }
}
