package com.example.schoolfinal2023;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDatabase extends AppCompatActivity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

//--------------------------Set toolbar-------------------------------------------------------------
        ImageView leftIcon = findViewById(R.id.left_icon);

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnDetailPage();
            }
        });
//--------------------------------------------------------------------------------------------------

//--------------------------Set SQLiteDatabase------------------------------------------------------

        database = openOrCreateDatabase("barcode_info.db", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS products (code TEXT PRIMARY KEY, name TEXT, ingredients TEXT)");

        EditText barcodeEditText = findViewById(R.id.numberInput);
        Button searchButton = findViewById(R.id.buttonClick);
        TextView productNameTextView = findViewById(R.id.productName);
        TextView ingredientsTextView = findViewById(R.id.description);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = barcodeEditText.getText().toString();

                ProductData product = getProductFromDatabase(barcode);
                if (product != null) {
                    productNameTextView.setText(product.product_name);
                    ingredientsTextView.setText(product.ingredients_description);
                } else {
                    String url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject productObject = response.getJSONObject("product");
                                        String productName = productObject.getString("product_name");
                                        String ingredients = productObject.getString("ingredients_text");

                                        ContentValues values = new ContentValues();
                                        values.put("code", barcode);
                                        values.put("name", productName);
                                        values.put("ingredients", ingredients);
                                        database.insert("products", null, values);

                                        productNameTextView.setText(productName);
                                        ingredientsTextView.setText(ingredients);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(ProductDatabase.this, "Грешка при обработка на отговора от API-то.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ProductDatabase.this, "Грешка при заявка до API-то.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    Volley.newRequestQueue(ProductDatabase.this).add(request);
                }
            }
        });
//--------------------------------------------------------------------------------------------------
    }

    protected void returnDetailPage(){
        Intent intent = new Intent(this, Detail.class);
        startActivity(intent);
    }

    private ProductData getProductFromDatabase(String barcode) {
        String[] columns = {"code", "name", "ingredients"};
        String selection = "code=?";
        String[] selectionArgs = {barcode};
        Cursor cursor = database.query("products", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String ingredients = cursor.getString(cursor.getColumnIndex("ingredients"));
            return new ProductData(barcode, name, ingredients);
        }
        return null;
    }
}

/*
package com.example.schoolfinal2023;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
public class Product extends AppCompatActivity {
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        database = openOrCreateDatabase("barcode_info.db", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS products (code TEXT PRIMARY KEY, name TEXT, ingredients TEXT)");

        EditText barcodeEditText = findViewById(R.id.numberInput);
        Button searchButton = findViewById(R.id.buttonClick);
        TextView productNameTextView = findViewById(R.id.productName);
        TextView ingredientsTextView = findViewById(R.id.description);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = barcodeEditText.getText().toString();

                ProductData product = getProductFromDatabase(barcode);
                if (product != null) {
                    productNameTextView.setText(product.product_name);
                    ingredientsTextView.setText(product.ingredients_description);
                } else {
                    String url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject productObject = response.getJSONObject("product");
                                        String productName = productObject.getString("product_name");
                                        String ingredients = productObject.getString("ingredients_text");

                                        ContentValues values = new ContentValues();
                                        values.put("code", barcode);
                                        values.put("name", productName);
                                        values.put("ingredients", ingredients);
                                        database.insert("products", null, values);

                                        productNameTextView.setText(productName);
                                        ingredientsTextView.setText(ingredients);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(Product.this, "Грешка при обработка на отговора от API-то.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Product.this, "Грешка при заявка до API-то.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    Volley.newRequestQueue(Product.this).add(request);
                }
            }
        });
    }

    private ProductData getProductFromDatabase(String barcode) {
        String[] columns = {"code", "name", "ingredients"};
        String selection = "code=?";
        String[] selectionArgs = {barcode};
        Cursor cursor = database.query("products", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String ingredients = cursor.getString(cursor.getColumnIndex("ingredients"));
            return new ProductData(barcode, name, ingredients);
        }
        return null;
    }
}


 */