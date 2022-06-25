package com.example.telemetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class PosicoesActivity extends AppCompatActivity {
    private ArrayList<OperadorPosicao> operadorPosicaoArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posicoes);
        recyclerView = findViewById(R.id.listaPosicoes);
        TextView tituloTextView = findViewById(R.id.tituloPosicoesActivity);
        operadorPosicaoArrayList = new ArrayList<>();

        String email = "Operador não carregou ainda";

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            email = extras.getString("operadorClicado");
        }

        tituloTextView.setText(email);

        OperadorPosicaoAdapter adapter = incluiAdapter();

        OperadorPosicaoDAO operadorPosicaoDAO = new OperadorPosicaoDAO();
        operadorPosicaoDAO.adicionarListener(adapter, operadorPosicaoArrayList);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, AdminActivity.class));
    }

    private OperadorPosicaoAdapter incluiAdapter() {
        OperadorPosicaoAdapter adapter = new OperadorPosicaoAdapter(operadorPosicaoArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return adapter;
    }
}