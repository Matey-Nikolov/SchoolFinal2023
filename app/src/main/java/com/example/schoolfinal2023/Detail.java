package com.example.schoolfinal2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Detail extends AppCompatActivity {

    interface RequestUser{
        @GET("/api/users/{uid}")
        Call<UserData> getUser(@Path("uid") String uid);
    }

    TextView FullNameAPI;
    TextView emailAPI;

    TextView numberInput;
    String numberString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Set toolbar
        ImageView leftIcon = findViewById(R.id.left_icon);
        ImageView rightIcon = findViewById(R.id.right_icon);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnLogin();
            }
        });

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        // Set current information
        TextView userName = findViewById(R.id.textView1);

        String text = getIntent().getStringExtra("username");
        userName.setText(text);

        //Set API data - https://reqres.in/
        FullNameAPI = findViewById(R.id.FullName);
        emailAPI = findViewById(R.id.email);

        int id = getResources().getIdentifier("buttonClick", "id", getPackageName());
        TextView currentBtn = findViewById(id);

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberInput = findViewById(R.id.numberInput);
                numberString = numberInput.getText().toString();

                if (!numberString.isEmpty()){
                    apiData(numberString);
                }
            }
        });
    }

    private void apiData(String numberId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in") //
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.getUser(numberId).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                FullNameAPI.setText(response.body().data.first_name + " " + response.body().data.last_name );
                emailAPI.setText(response.body().data.email);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                FullNameAPI.setText("Not found!");
                emailAPI.setText("Not found!");
            }
        });
    }

    private void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(Detail.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.one){
                    Toast.makeText(Detail.this, "You clicked item one", Toast.LENGTH_SHORT).show();
                    openSettings();
                } else if (item.getItemId() == R.id.two){
                    Toast.makeText(Detail.this, "You clicked item two", Toast.LENGTH_SHORT).show();
                    openProduct();
                }else if (item.getItemId() == R.id.three){
                    Toast.makeText(Detail.this, "You clicked item three", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        popupMenu.show();
    }

    protected void returnLogin(){
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("username", usernameString);
        startActivity(intent);
    }

    protected void openProduct(){
        Intent intent = new Intent(this, Product.class);
        startActivity(intent);
    }

    protected void openSettings(){
        Intent intent = new Intent(this, Setting.class);
        //intent.putExtra("username", usernameString);
        startActivity(intent);
    }
}
