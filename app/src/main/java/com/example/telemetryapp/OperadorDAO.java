package com.example.telemetryapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OperadorDAO {
    private DatabaseReference databaseReference;
    private boolean ehAdmin;

    public OperadorDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Operador.class.getSimpleName());
    }

    public Task<Void> add(Operador operador){
        return databaseReference.push().setValue(operador);
    }

    public void adicionarListener(OperadorAdapter adapter, ArrayList<Operador> operadorArrayList){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Operador operador = dataSnapshot.getValue(Operador.class);
                    operadorArrayList.add(operador);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean ehAdmin(String emailAdmin){
        ehAdmin = true;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Operador operador = dataSnapshot.getValue(Operador.class);
                    boolean emailsIguais = operador.email.equals(emailAdmin) ;
                    if (emailsIguais)
                    {
                        ehAdmin = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ehAdmin;
    }



}
