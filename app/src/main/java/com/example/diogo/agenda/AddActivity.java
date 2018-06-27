package com.example.diogo.agenda;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.diogo.agenda.Uteis.Uteis;
import com.example.diogo.agenda.model.EstadoCivilModel;
import com.example.diogo.agenda.model.PessoaModel;
import com.example.diogo.agenda.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    EditText editTextNome;
    EditText editTextEndereco;
    RadioButton radioButtonMasculino;
    RadioButton radioButtonFeminino;
    RadioGroup radioGroupSexo;
    EditText editTextDataNascimento;
    Spinner spinnerEstadoCivil;
    CheckBox checkBoxRegistroAtivo;
    Button buttonSalvar;
    Button buttonVoltar;
    DatePickerDialog datePickerDialogDataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.Localizacao();
        this.CriarComponentes();
        this.CriarEventos();
        this.CarregaEstadosCivis();
    }
    protected  void CriarComponentes(){

        editTextNome           = (EditText) this.findViewById(R.id.editTextNome);
        editTextEndereco       = (EditText) this.findViewById(R.id.editTextEndereco);
        radioButtonMasculino = (RadioButton) this.findViewById(R.id.radioButtonMasculino);
        radioButtonFeminino = (RadioButton) this.findViewById(R.id.radioButtonFeminino);
        radioGroupSexo = (RadioGroup) this.findViewById(R.id.radioGroupSexo);
        editTextDataNascimento = (EditText)this.findViewById(R.id.editTextDataNascimento);
        spinnerEstadoCivil = (Spinner)this.findViewById(R.id.spinnerEstadoCivil);
        checkBoxRegistroAtivo = (CheckBox)this.findViewById(R.id.checkBoxRegistroAtivo);
        buttonSalvar = (Button) this.findViewById(R.id.buttonSalvar);
        buttonVoltar = (Button) this.findViewById(R.id.buttonVoltar);

    }
    protected  void CriarEventos(){


        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual   = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual   = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual   = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        datePickerDialogDataNascimento = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {

                String mes = (String.valueOf((mesSelecionado + 1)).length() == 1 ? "0" + (mesSelecionado + 1 ): String.valueOf(mesSelecionado));

                editTextDataNascimento.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);

            }

        }, anoAtual, mesAtual, diaAtual);



        editTextDataNascimento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialogDataNascimento.show();
            }
        });

        editTextDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                datePickerDialogDataNascimento.show();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Salvar_onClick();
            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });
    }

    protected  void Salvar_onClick(){

        if(editTextNome.getText().toString().trim().equals("")){
            Uteis.Alert(this, "Nome obrigatório");
            editTextNome.requestFocus();
        }
        else if(editTextEndereco.getText().toString().trim().equals("")){
            Uteis.Alert(this, "Endereço obrigatório");
            editTextEndereco.requestFocus();
        }
        else if(!radioButtonMasculino.isChecked() && !radioButtonFeminino.isChecked()){
            Uteis.Alert(this, "Sexo obrigatório");
        }
        else if(editTextDataNascimento.getText().toString().trim().equals("")){
            Uteis.Alert(this, "Data obrigatória");
            editTextDataNascimento.requestFocus();
        }
        else{
            PessoaModel pessoaModel = new PessoaModel();
            pessoaModel.setNome(editTextNome.getText().toString().trim());
            pessoaModel.setEndereco(editTextEndereco.getText().toString().trim());
            if(radioButtonMasculino.isChecked())
                pessoaModel.setSexo("M");
            else
                pessoaModel.setSexo("F");

            pessoaModel.setDataNascimento(editTextDataNascimento.getText().toString().trim());
            EstadoCivilModel estadoCivilModel = (EstadoCivilModel)spinnerEstadoCivil.getSelectedItem();
            pessoaModel.setEstadoCivil(estadoCivilModel.getCodigo());
            pessoaModel.setRegistroAtivo((byte)0);
            if(checkBoxRegistroAtivo.isChecked())
                pessoaModel.setRegistroAtivo((byte)1);
            new PessoaRepository(this).Salvar(pessoaModel);
            Uteis.Alert(this,"Sucesso");
            LimparCampos();
        }
    }

    protected void LimparCampos(){
        editTextNome.setText(null);
        editTextEndereco.setText(null);
        radioGroupSexo.clearCheck();
        editTextDataNascimento.setText(null);
        checkBoxRegistroAtivo.setChecked(false);
    }
    protected  void Localizacao(){
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
    }

    protected  void CarregaEstadosCivis(){

        ArrayAdapter<EstadoCivilModel> arrayAdapter;

        List<EstadoCivilModel> itens =  new ArrayList<EstadoCivilModel>();

        itens.add(new EstadoCivilModel("S", "Solteiro(a)"));
        itens.add(new EstadoCivilModel("C", "Casado(a)"));
        itens.add(new EstadoCivilModel("V", "Viuvo(a)"));
        itens.add(new EstadoCivilModel("D", "Divorciado(a)"));

        arrayAdapter = new ArrayAdapter<EstadoCivilModel>(this, android.R.layout.simple_spinner_item,itens);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEstadoCivil.setAdapter(arrayAdapter);
    }
}
