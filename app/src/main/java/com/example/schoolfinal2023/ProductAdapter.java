package com.example.schoolfinal2023;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ProductItem> productList;
    private Context context;

    public ProductAdapter(List<ProductItem> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem product = productList.get(position);
        holder.titleTextView.setText(product.getTitle());
        holder.barcodeTextView.setText(product.getDescription());
        Picasso.get().load(product.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView titleTextView;
//        TextView barcodeTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageView);
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//            barcodeTextView = itemView.findViewById(R.id.barcodeTextView);
//        }
//    }
}
