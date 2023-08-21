package com.firstapp.mtix;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.mtix.Activities.LoginActivity;
import com.firstapp.mtix.Activities.Releve;
import com.firstapp.mtix.model.Client;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<Client> clientList;
    //private int position;

    public ClientAdapter(List<Client> clientList) {
        this.clientList = clientList;
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        public TextView client_ID;
        public TextView client_name;
        LinearLayout mainLayout;


        public ClientViewHolder(View itemView) {
            super(itemView);
            client_ID = itemView.findViewById(R.id.client_ID);
            client_name= itemView.findViewById(R.id.client_name);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_layout, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.client_ID.setText(String.valueOf(client.getId()));
        holder.client_name.setText(client.getNomPrenom());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Releve.class);
                intent.putExtra("user_id", String.valueOf(client.getId()));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void setClientList(List<Client> clients) {
        clientList.clear();
        clientList.addAll(clients);
        notifyDataSetChanged();
    }
}