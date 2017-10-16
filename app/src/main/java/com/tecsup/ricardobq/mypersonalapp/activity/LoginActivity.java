package com.tecsup.ricardobq.mypersonalapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tecsup.ricardobq.mypersonalapp.R;
import com.tecsup.ricardobq.mypersonalapp.datos.User;
import com.tecsup.ricardobq.mypersonalapp.datos.UserRepository;
import com.tecsup.ricardobq.mypersonalapp.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText inputUser;
    private EditText inputPass;
    private Button btnLogin;
    private Button btnLinkToRegister;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUser = (EditText) findViewById(R.id.email);
        inputPass = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Session manager
        //session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        /*
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        */

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUser.getText().toString().trim();
                String password = inputPass.getText().toString().trim();

                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(username, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Campos incompletos!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void checkLogin(final String username, final String password) {

        // Set Data Adapter to ReciclerView
        List<User> Users = UserRepository.list();

        for (User user : Users) {
            if (user.getUsername().equalsIgnoreCase(username)
                    && user.getPassword().equalsIgnoreCase(password)) {

                String usern = user.getUsername();
                String name = user.getName();
                int phone = user.getPhone();

                Toast.makeText(this, "Bienvenido "+ name, Toast.LENGTH_SHORT).show();

                //session.setLogin(true);

                // Launch main activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", usern);
                intent.putExtra("name", name);
                intent.putExtra("phone", String.valueOf(phone));
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Datos incorrectos.", Toast.LENGTH_SHORT).show();

            }
        }

    }

}
