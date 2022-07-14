package com.uas.mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uas.mobile.R;
import com.uas.mobile.activity.MapsActivity;
import com.uas.mobile.local.data.entity.AddressBook;

import java.util.List;
import java.util.Map;

public class AdapterAddressBook extends RecyclerView.Adapter<AdapterAddressBook.ViewHolder> {

    private final Context context;
    private final List<AddressBook> list;

    public AdapterAddressBook(Context context, List<AddressBook> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterAddressBook.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_address_book, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddressBook.ViewHolder holder, int position) {

        holder.imgProfileAddress.setImageBitmap(list.get(position).getBitmap());
        holder.txtName.setText(list.get(position).getName());
        holder.txtCity.setText(list.get(position).getCity());

        holder.btnCall.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + list.get(position).getPhoneNumber());
            Intent intent = new Intent(Intent.ACTION_DIAL, number);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        String nim = MapsActivity.nim;
        String nama = MapsActivity.nama;

        holder.btnEmail.setOnClickListener(v -> {
            String email = list.get(position).getEmail();
            Uri uri = Uri.parse("mailto:" + email + "?subject=UAS&body=UAS Mobile Android " + nim + " " + nama);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgProfileAddress;
        private final TextView txtName;
        private final TextView txtCity;
        private final Button btnCall;
        private final Button btnEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfileAddress = itemView.findViewById(R.id.imgProfileAddress);
            txtName = itemView.findViewById(R.id.txtName);
            txtCity = itemView.findViewById(R.id.txtCity);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnEmail = itemView.findViewById(R.id.btnEmail);
        }
    }
}
