package com.example.schoolfinal2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView username = null;
    TextView password = null;

    String usernameString = "";
    String passwordString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int id = getResources().getIdentifier("button1", "id", getPackageName());
        TextView currentBtn = findViewById(id);

        String textRegisterUsername = getIntent().getStringExtra("username");
        String textRegisterPassword = getIntent().getStringExtra("password");

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.editTextPersonName);
                password = findViewById(R.id.editTextPassword);

                usernameString = username.getText().toString();
                passwordString = password.getText().toString();



                System.out.println(username.getText());
                System.out.println(password.getText());

                if (usernameString.equals(textRegisterUsername) && passwordString.equals(textRegisterPassword)){
                    openDetails();
                }
            }
        });
    }

    protected void openDetails(){
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("username", usernameString);
        startActivity(intent);
    }
}