package com.example.diogo.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.diogo.agenda.Uteis.AdapterPessoas;
import com.example.diogo.agenda.model.PessoaModel;
import com.example.diogo.agenda.repository.PessoaRepository;

import java.util.List;

public class ShowActivity extends AppCompatActivity {
    ListView listViewPessoas;

    Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        listViewPessoas = (ListView)this.findViewById(R.id.listViewPessoas);
        //buttonVoltar    = (Button)this.findViewById(R.id.buttonVoltar);

        this.CarregarPessoasCadastradas();
        this.CriarEvento();
    }
    protected  void CriarEvento(){

        /*buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });*/
    }
    protected  void CarregarPessoasCadastradas(){
        PessoaRepository pessoaRepository =  new PessoaRepository(this);
        List<PessoaModel> pessoas = pessoaRepository.SelecionarTodos();
        listViewPessoas.setAdapter(new AdapterPessoas(this, pessoas));
    }
}
