package com.example.telemetryapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.telemetryapp.databinding.ActivityCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    private FirebaseAuth mAuth;

    private int FuncaoSelecionada = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaToolbar();

        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(v -> validaDados());
    }

    private void iniciaToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();
        int radioId = binding.radioGroupCriarConta.getCheckedRadioButtonId();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                if (radioId != -1){
                    binding.progressBar.setVisibility(View.VISIBLE);

                    criarContaFirebase(email, senha);
                } else {
                    Toast.makeText(this, "Selecione uma função! (Operador/Admin)", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Informe uma senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu e-mail.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_operador:
                if (checked)
                    FuncaoSelecionada = 1;
                    Toast.makeText(this, "OPERADOR", Toast.LENGTH_SHORT).show();
                    break;
            case R.id.radio_admin:
                if (checked)
                    FuncaoSelecionada = 2;
                    Toast.makeText(this, "ADMIN", Toast.LENGTH_SHORT).show();
                    break;
        }
    }

    private void criarContaFirebase(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (FuncaoSelecionada != 2){
                    OperadorDAO operadorDAO = new OperadorDAO();
                    Operador operador = criarOperador(email);
                    operadorDAO.add(operador);
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                    startActivity(new Intent(this, AdminActivity.class));
                }


            } else {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um erro.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Operador criarOperador(String email){
        Operador operador = new Operador();

        String nomeDispositivo = getDeviceName();

        operador.email = email;
        operador.dispositivo = nomeDispositivo;
        return operador;
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}