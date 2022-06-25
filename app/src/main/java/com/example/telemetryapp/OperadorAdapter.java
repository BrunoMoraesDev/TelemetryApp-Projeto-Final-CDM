package com.example.telemetryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OperadorAdapter extends RecyclerView.Adapter<OperadorAdapter.MyViewHolder>{
    private ArrayList<Operador> listaOperadores;
    private OperadorRecyclerViewClickListener operadorClickListener;

    public OperadorAdapter(ArrayList<Operador> arrayOperadores, OperadorRecyclerViewClickListener operadorClickListener){
        listaOperadores = arrayOperadores;
        this.operadorClickListener = operadorClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView emailTextView;
        private TextView dispositivoTextView;

        public MyViewHolder(final View view){
            super(view);
            emailTextView = view.findViewById(R.id.emailEditView);
            dispositivoTextView = view.findViewById(R.id.dispositivoEditView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            operadorClickListener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public OperadorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operador, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull OperadorAdapter.MyViewHolder holder, int position) {
        String email = listaOperadores.get(position).email;
        String dispositivo = listaOperadores.get(position).dispositivo;

        holder.emailTextView.setText(email);
        holder.dispositivoTextView.setText(dispositivo);
    }

    @Override
    public int getItemCount() {
        return listaOperadores.size();
    }

    public interface OperadorRecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
