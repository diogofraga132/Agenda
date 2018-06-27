package com.example.diogo.agenda.Uteis;

import android.app.AlertDialog;
import android.content.Context;
import com.example.diogo.agenda.R;


public class Uteis {
    public static void Alert(Context context, String mensagem){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(mensagem);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();

    }
}
