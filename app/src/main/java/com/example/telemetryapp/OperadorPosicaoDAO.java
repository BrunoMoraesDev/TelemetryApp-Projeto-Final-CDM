package com.example.telemetryapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OperadorPosicaoDAO {
    private DatabaseReference databaseReference;
    private String emailOperador;

    public OperadorPosicaoDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(OperadorPosicao.class.getSimpleName());
    }

    public OperadorPosicaoDAO(String emailOperadorFiltrado){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(OperadorPosicao.class.getSimpleName());
        this.emailOperador = emailOperadorFiltrado;
    }

    public Task<Void> add(OperadorPosicao operadorPosicao){
        return databaseReference.push().setValue(operadorPosicao);
    }

    public void adicionarListener(OperadorPosicaoAdapter adapter, ArrayList<OperadorPosicao> operadorArrayList){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (emailOperador == null || emailOperador.isEmpty()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        OperadorPosicao operadorPosicao = dataSnapshot.getValue(OperadorPosicao.class);
                        operadorArrayList.add(operadorPosicao);
                    }
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        OperadorPosicao operadorPosicao = dataSnapshot.getValue(OperadorPosicao.class);
                        if (operadorPosicao.email == emailOperador){
                            operadorArrayList.add(operadorPosicao);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
