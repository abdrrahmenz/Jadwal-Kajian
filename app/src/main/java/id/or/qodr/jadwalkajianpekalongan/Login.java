package id.or.qodr.jadwalkajianpekalongan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends BaseActivity {

    private TextInputEditText input_user, input_pass;
    private Button login;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        input_user = (TextInputEditText) findViewById(R.id.input_email);
        input_pass = (TextInputEditText) findViewById(R.id.input_password);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = input_user.getText().toString();
                String pasword = input_pass.getText().toString();

                if (username.trim().length() > 0 && pasword.trim().length() > 0){
                    if(username.equals("test") && pasword.equals("test")){

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("test", "test");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        //]    // username / password doesn't match
                        Toast.makeText(getApplicationContext(), "Login failed.. "+" Username/Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    Toast.makeText(getApplicationContext(), "Login failed.. "+" Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, DashboardActivity.class));
        finish();
    }
}
