package com.example.schoolfinal2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    TextView username = null;
    TextView password = null;

    String usernameString = "";
    String passwordString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int id = getResources().getIdentifier("button1", "id", getPackageName());
        TextView currentBtn = findViewById(id);

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = findViewById(R.id.editTextPersonName);
                password = findViewById(R.id.editTextPassword);

                usernameString = username.getText().toString();
                passwordString = password.getText().toString();

                openMain();
            }
        });

    }

    protected void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", usernameString);
        intent.putExtra("password", passwordString);

        startActivity(intent);

    }
}
