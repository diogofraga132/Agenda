package com.example.diogo.agenda.Uteis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


import com.example.diogo.agenda.EditActivity;
import com.example.diogo.agenda.ShowActivity;
import com.example.diogo.agenda.model.PessoaModel;
import com.example.diogo.agenda.repository.PessoaRepository;

import com.example.diogo.agenda.R;

public class AdapterPessoas extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    List<PessoaModel> pessoaModels =  new ArrayList<PessoaModel>();
    PessoaRepository pessoaRepository;
    private ShowActivity showActivity;
    public AdapterPessoas(ShowActivity consultarActivity, List<PessoaModel> pessoaModels) {

        this.pessoaModels       =  pessoaModels;
        this.showActivity  =  consultarActivity;
        this.layoutInflater     = (LayoutInflater) this.showActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pessoaRepository   = new PessoaRepository(consultarActivity);
    }
    @Override
    public int getCount(){

        return pessoaModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View viewLinhaLista = layoutInflater.inflate(R.layout.item_show,null);
        //TextView textViewCodigo = (TextView) viewLinhaLista.findViewById(R.id.textViewCodigo);
        TextView textViewNome = (TextView) viewLinhaLista.findViewById(R.id.textViewNome);
        TextView textViewEndereco = (TextView) viewLinhaLista.findViewById(R.id.textViewEndereco);
        TextView textViewSexo = (TextView) viewLinhaLista.findViewById(R.id.textViewSexo);
        TextView textViewEstadoCivil = (TextView) viewLinhaLista.findViewById(R.id.textViewEstadoCivil);
        TextView textViewNascimento = (TextView) viewLinhaLista.findViewById(R.id.textViewNascimento);
        TextView textViewRegsitroAtivo = (TextView) viewLinhaLista.findViewById(R.id.textViewRegistroAtivo);
        Button buttonExcluir = (Button)   viewLinhaLista.findViewById(R.id.buttonExcluir);
        Button buttonEditar = (Button)   viewLinhaLista.findViewById(R.id.buttonEditar);
        //textViewCodigo.setText(String.valueOf(pessoaModels.get(position).getCodigo()));
        textViewNome.setText(pessoaModels.get(position).getNome());
        textViewEndereco.setText(pessoaModels.get(position).getEndereco());

        if(pessoaModels.get(position).getSexo().toUpperCase().equals("M"))
            textViewSexo.setText("Masculino");
        else
            textViewSexo.setText("Feminino");
        textViewEstadoCivil.setText(this.GetEstadoCivil(pessoaModels.get(position).getEstadoCivil()));
        textViewNascimento.setText(pessoaModels.get(position).getDataNascimento());

        if(pessoaModels.get(position).getRegistroAtivo() == 1)
            textViewRegsitroAtivo.setText("Ativo:Sim");
        else
            textViewRegsitroAtivo.setText("Ativo:Não");


        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoaRepository.Excluir(pessoaModels.get(position).getCodigo());

                Toast.makeText(showActivity, "Registro excluido com sucesso!", Toast.LENGTH_LONG).show();
                AtualizarLista();

            }
        });

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intentRedirecionar = new Intent(showActivity, EditActivity.class);
                intentRedirecionar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentRedirecionar.putExtra("id_pessoa",pessoaModels.get(position).getCodigo());
                showActivity.startActivity(intentRedirecionar);
                showActivity.finish();
            }
        });
        return viewLinhaLista;
    }

    public String GetEstadoCivil(String codigoEstadoCivil){

        if(codigoEstadoCivil.equals("S"))
            return "Solteiro(a)";
        else if(codigoEstadoCivil.equals("C"))
            return "Casado(a)";
        else if(codigoEstadoCivil.equals("V"))
            return "Viuvo(a)";
        else
            return "Divorciado(a)";
    }

    public void AtualizarLista(){

        this.pessoaModels.clear();
        this.pessoaModels = pessoaRepository.SelecionarTodos();
        this.notifyDataSetChanged();
    }
}
