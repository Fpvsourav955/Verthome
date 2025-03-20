package com.sourav.verthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<String> title;
    private final ArrayList<String> address;
    private final ArrayList<String> host_id;
    private final ArrayList<String> id;
    Activity activity;


    public CustomAdapter(Activity activity ,Context context, ArrayList<String> id, ArrayList<String> title, ArrayList<String> address, ArrayList<String> hostid) {

        this.activity=activity;
        this.context = context;
        this.id = id;
        this.title = title;
        this.address = address;
        this.host_id = hostid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.Add_title.setText(title.get(position));
        holder.Add_address.setText(address.get(position));
        holder.Add_host.setText(host_id.get(position));
        holder.Add_id.setText(id.get(position));
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context,UnderHome.class);
            intent.putExtra("title", title.get(position));
            intent.putExtra("host_id", host_id.get(position));// Pass the correct host ID
            activity.startActivity(intent);

        });
    }
    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Add_title, Add_address, Add_host, Add_id;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Add_title = itemView.findViewById(R.id.Add_title);
            Add_address = itemView.findViewById(R.id.Add_address);
            Add_host = itemView.findViewById(R.id.Add_host);
            Add_id = itemView.findViewById(R.id.Add_id);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
