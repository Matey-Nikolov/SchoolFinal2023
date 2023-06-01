package com.example.schoolfinal2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

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

    private RecyclerView recyclerView;
    private List<ProductItem> productList;
    private ProductAdapter productAdapter;

    private SwipeRefreshLayout swipeRefresh;
    TextView FullNameAPI;
    TextView emailAPI;

    TextView numberInput;
    String numberString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//--------------------------Set toolbar-------------------------------------------------------------
        ImageView rightIcon = findViewById(R.id.right_icon);

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
//--------------------------------------------------------------------------------------------------

//-------------------------Set current information--------------------------------------------------
        TextView userName = findViewById(R.id.textView1);

        String text = getIntent().getStringExtra("username");
        userName.setText(text);
//--------------------------------------------------------------------------------------------------

//-------------------------Set recyclerView---------------------------------------------------------
        recyclerView();
//--------------------------------------------------------------------------------------------------

//-------------------------Set swipeRefresh---------------------------------------------------------
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
//--------------------------------------------------------------------------------------------------


//-------------------------Set API data - https://reqres.in/----------------------------------------
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
//--------------------------------------------------------------------------------------------------
    }

    private void refreshItems(){
        productList.add(new ProductItem("https://images.openfoodfacts.org/images/products/544/900/021/4911/front_en.119.400.jpg", "Coca-cola - 330 mL", "5449000214911"));
        productList.add(new ProductItem("https://images.openfoodfacts.org/images/products/871/570/001/7006/front_en.163.400.jpg", "Tomato Ketchup - Heinz - 500ml", "8715700017006"));
        productList.add(new ProductItem("https://images.openfoodfacts.org/images/products/339/246/048/0827/front_en.72.400.jpg", "Biscottes heudebert", "3392460480827"));

        Toast.makeText(Detail.this, "Refresh items", Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
    }

    private void  recyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productList.add(new ProductItem("https://varna.parkmart.bg/wp-content/uploads/2021/02/0000001765.jpg", "Банкя", "3800202530056"));
        productList.add(new ProductItem("https://th.bing.com/th/id/OIP.vuavZx37OQ-ZmvE6nbfxpgHaLH?w=204&h=306&c=7&r=0&o=5&pid=1.7", "Thai peanut noodle", "737628064502"));
        productList.add(new ProductItem("https://images.openfoodfacts.org/images/products/505/399/015/6009/front_fr.179.400.jpg", "Pringles Original", "5053990156009"));

        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);
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
                if(item.getItemId() == R.id.settings){
                    Toast.makeText(Detail.this, "You clicked settings", Toast.LENGTH_SHORT).show();
                    openSettings();
                } else if (item.getItemId() == R.id.barCodeReader){
                    Toast.makeText(Detail.this, "You clicked product code", Toast.LENGTH_SHORT).show();
                    openProduct();
                }

                return true;
            }
        });

        popupMenu.show();
    }

    protected void openProduct(){
        Intent intent = new Intent(this, ProductDatabase.class);
        startActivity(intent);
    }

    protected void openSettings(){
        Intent intent = new Intent(this, Setting.class);
        //intent.putExtra("username", usernameString);
        startActivity(intent);
    }
}
