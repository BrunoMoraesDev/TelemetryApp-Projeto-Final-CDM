package com.example.telemetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.telemetryapp.databinding.ActivityAdminBinding;
import com.example.telemetryapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private ArrayList<Operador> operadorArrayList;
    private RecyclerView recyclerView;
    private Button deslogarButton;
    private OperadorAdapter.OperadorRecyclerViewClickListener operadorClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = findViewById(R.id.listaOperadores);
        operadorArrayList = new ArrayList<>();
        deslogarButton = findViewById(R.id.btnDeslogar);

        deslogarButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        });

        OperadorAdapter adapter = incluiAdapter();

        OperadorDAO operadorDAO = new OperadorDAO();
        operadorDAO.adicionarListener(adapter, operadorArrayList);
    }

    private OperadorAdapter incluiAdapter() {
        definirClickListener();
        OperadorAdapter adapter = new OperadorAdapter(operadorArrayList, operadorClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return adapter;
    }

    private void definirClickListener() {
        operadorClickListener = new OperadorAdapter.OperadorRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), PosicoesActivity.class);
                intent.putExtra("operadorClicado", operadorArrayList.get(position).email);
                startActivity(intent);
            }
        };
    }
}