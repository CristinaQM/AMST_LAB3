package com.example.amstapiapp6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnviarDataActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private EditText edtTxtTemperatura, edtTxtHumedad, edtTxtPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_data);
        mQueue = Volley.newRequestQueue(this);
        edtTxtTemperatura = findViewById(R.id.edtTxtTemperatura);
        edtTxtHumedad = findViewById(R.id.edtTextHumedad);
        edtTxtPeso = findViewById(R.id.edtTxtPeso);
    }

    public void cambiarData(View v) {

        final String temp = edtTxtTemperatura.getText().toString();
        final String hum = edtTxtHumedad.getText().toString();
        final String peso = edtTxtPeso.getText().toString();

        Map<String, Integer> params = new HashMap<>();

        try {
            params.put("temperatura", Integer.parseInt(temp));
            params.put("humedad", Integer.parseInt(hum));
            params.put("peso", Integer.parseInt(peso));
        } catch (NumberFormatException ex) {
            showAlert("Error", "Comprueba el formato");
        }

        final JSONObject parametros = new JSONObject(params);
        final String urlToSend = "https://amst-lab-api.herokuapp.com/api/sensores";

        JsonObjectRequest req = new JsonObjectRequest(
            Request.Method.POST,
            urlToSend,
            parametros,
            response -> {
                showAlert("Hecho!", "Se añadieron los datos!");
            },
            error -> {}
        );
        mQueue.add(req);
    }

    private void showAlert(String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(EnviarDataActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}