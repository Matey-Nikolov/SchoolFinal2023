package com.example.schoolfinal2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Product extends AppCompatActivity {

    // Use new Api
    interface RequestProduct{
        @GET("api/v0/product/{uid}")
        Call<ProductData> getUser(@Path("uid") String uid);
    }

    TextView productNameAPI;
    TextView descriptionAPI;

    TextView numberInput;
    String numberString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Set toolbar
        ImageView leftIcon = findViewById(R.id.left_icon);

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnDetail();
            }
        });

        productNameAPI = findViewById(R.id.productName);
        descriptionAPI = findViewById(R.id.description);

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
                .baseUrl("https://world.openfoodfacts.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestProduct product = retrofit.create(RequestProduct.class);

        product.getUser(numberId).enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                System.out.println(response.body());
                // Work
                productNameAPI.setText(response.body().data.categories);
                //descriptionAPI.setText(response.body().data.email);
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                productNameAPI.setText("Not found!");
                descriptionAPI.setText("Not found!");
            }
        });
    }

    protected void returnDetail(){
        Intent intent = new Intent(this, Detail.class);
        startActivity(intent);
    }
}

