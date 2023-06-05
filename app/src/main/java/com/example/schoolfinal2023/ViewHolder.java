package com.example.schoolfinal2023;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView titleTextView;
    TextView barcodeTextView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        barcodeTextView = itemView.findViewById(R.id.barcodeTextView);

        itemView.findViewById(R.id.buttonCopy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(barcodeTextView.getText().toString());
            }
        });
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied barcode", text);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(itemView.getContext(), "Barcode copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}

