package com.tecsup.ricardobq.mypersonalapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tecsup.ricardobq.mypersonalapp.R;
import com.tecsup.ricardobq.mypersonalapp.datos.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText phoneInput;
    private EditText userInput;
    private EditText passInput, passInput2;

    private Button btnLinkToLogin;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = (EditText)findViewById(R.id.name);
        phoneInput = (EditText)findViewById(R.id.phone);
        userInput = (EditText)findViewById(R.id.user);
        passInput = (EditText)findViewById(R.id.pass);
        passInput2 = (EditText)findViewById(R.id.pass2);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void Registrar(View view){
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String user = userInput.getText().toString().trim();
        String pass = passInput.getText().toString().trim();
        String pass2 = passInput2.getText().toString().trim();

        if(name.isEmpty() || phone.isEmpty() || user.isEmpty() || pass.isEmpty() || pass2.isEmpty()){
            Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(pass.equalsIgnoreCase(pass2)){

            pDialog.setMessage("Registrando ...");
            showDialog();

            UserRepository.create(name, Integer.parseInt(phone), user, pass);

            hideDialog();

            Toast.makeText(getApplicationContext(), "Usuario registra correctamente, intente ingresar.", Toast.LENGTH_LONG).show();

            // Launch login activity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, "Contraseñas diferentes.", Toast.LENGTH_SHORT).show();

        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
