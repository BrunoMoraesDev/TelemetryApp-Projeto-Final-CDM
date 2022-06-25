package com.example.telemetryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OperadorPosicaoAdapter extends RecyclerView.Adapter<OperadorPosicaoAdapter.MyViewHolder>{
    private ArrayList<OperadorPosicao> listaOperadorPosicao;

    public OperadorPosicaoAdapter(ArrayList<OperadorPosicao> arrayOperadorPosicao){
        listaOperadorPosicao = arrayOperadorPosicao;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView latitudeTextView;
        private TextView longitudeTextView;

        public MyViewHolder(final View view){
            super(view);
            latitudeTextView = view.findViewById(R.id.latitudeEditView);
            longitudeTextView = view.findViewById(R.id.longitudeEditView);
        }
    }

    @NonNull
    @Override
    public OperadorPosicaoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operador_posicao, parent, false);
        return new OperadorPosicaoAdapter.MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull OperadorPosicaoAdapter.MyViewHolder holder, int position) {
        double latitude = listaOperadorPosicao.get(position).Latitude;
        double longitude = listaOperadorPosicao.get(position).Longitude;

        holder.latitudeTextView.setText(String.valueOf(latitude));
        holder.longitudeTextView.setText(String.valueOf(longitude));
    }

    @Override
    public int getItemCount() {
        return listaOperadorPosicao.size();
    }
}
