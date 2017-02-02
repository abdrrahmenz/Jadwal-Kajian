package id.or.qodr.jadwalkajianpekalongan;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_user = (TextInputEditText) findViewById(R.id.input_email);
        input_pass = (TextInputEditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    input_user.getText().toString().equals("admin");
                    input_pass.getText().toString().equals("admin");
                    open(AdminActivity.class);
                    finish();
                }else {
                    Toast.makeText(Login.this, "Wrong Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validate() {
        if (TextUtils.isEmpty(getString(input_user))){
            Toast.makeText(this, "Please input your email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(getString(input_pass))){
            Toast.makeText(this, "Please input your password.", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }
}
