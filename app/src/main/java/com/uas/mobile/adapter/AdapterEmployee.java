package com.uas.mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uas.mobile.R;
import com.uas.mobile.model.ModelEmployeeResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.ViewHolder> {

    private final Context context;
    private final List<ModelEmployeeResponse> list;
    private AdapterEmployee.OnItemClickListener onItemClickListener;

    public AdapterEmployee(Context context, List<ModelEmployeeResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterEmployee.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_all_employee, null);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterEmployee.ViewHolder holder, int position) {

        String imageUrl = list.get(position).getPicture().getLarge();

        Picasso.get()
                .load(imageUrl)
                .into(holder.imgEmployee);

        String firstName = list.get(position).getName().getFirst();
        String lastName = list.get(position).getName().getLast();
        holder.txtName.setText(firstName + " " + lastName);
        holder.txtCity.setText(list.get(position).getLocation().getCity() + ", " +
                list.get(position).getLocation().getCountry());
        holder.txtPhone.setText(list.get(position).getPhone() + " / " +
                list.get(position).getCell());

        String date = list.get(position).getRegistered().getDate();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dates != null) {
            @SuppressLint("SimpleDateFormat")
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            String newDate = dateFormat.format(dates);
            holder.txtMemberSince.setText(newDate);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdapterEmployee.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgEmployee;
        private final TextView txtName;
        private final TextView txtCity;
        private final TextView txtPhone;
        private final TextView txtMemberSince;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEmployee = itemView.findViewById(R.id.imgEmployee);
            txtName = itemView.findViewById(R.id.txtName);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtMemberSince = itemView.findViewById(R.id.txtMemberSince);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
