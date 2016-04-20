package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import http_requests.RegisterRequestTask;

/**
 * Created by miaodonghan on 4/10/16.
 */
public class RegisterActivity extends AppCompatActivity {
    EditText email;
    EditText pwd1;
    EditText pwd2;
    Button register_btn;
    String p1_r ="";
    String p2_r ="";
    String email_r ="";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        email = (EditText)findViewById(R.id.email_ET);
        pwd1 =(EditText)findViewById(R.id.pwd1);
        pwd2 =(EditText)findViewById(R.id.pwd2);
        register_btn = (Button)findViewById(R.id.register_btn);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);


        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //email_r = email.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.i("11111", email.getText().toString());
                email_r = email.getText().toString();
            }
        });
        pwd1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // p1_r = pwd1.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.i("11111", p1_r);
                p1_r = pwd1.getText().toString();
            }
        });

        pwd2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //p2_r = pwd2.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                p2_r = pwd2.getText().toString();
                Log.i("11111", p2_r);
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
//            String p1 = p1_r;
//            String p2 = pwd2.getText().toString();
//            String e_mail = email_r;


            @Override
            public void onClick(View v) {
                Log.i("p1:::::", p1_r + "p1p1p1rrrr");
                if (p1_r.length() == 0 || p2_r.length() == 0 || email_r.length() == 0) {
                    Log.i("p2:::::", p2_r + "p2p1p1");
                    Log.i("emmm:::::", email_r + "p1p1email");
                    Log.i("email:::::", "adfhjasdhflajk");
                    Toast.makeText(RegisterActivity.this, "please input email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!p1_r.equals(p2_r)) {
                    Toast.makeText(RegisterActivity.this, "please input password agin", Toast.LENGTH_SHORT).show();
                    return;
                }

                String ip = getString(R.string.ip_address);
                RegisterRequestTask registerRequestTask = new RegisterRequestTask(RegisterActivity.this,ip);

                Log.e("IPPPPPPPPL::", ip + "/api/auth/register");
               // (new RegisterRequestTask(RegisterActivity.this, email_r, p1_r, ip)).execute(ip + "/api/auth/register");
                registerRequestTask.execute(email_r, p1_r);

            }
        });






    }
}
