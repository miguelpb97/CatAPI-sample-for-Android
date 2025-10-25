package com.mapb.catapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button buttonSaveApiKey = findViewById(R.id.button_save_current_api_key);
        EditText etApikey = findViewById(R.id.edit_text_current_api_key);

        String apiKey = "";

        if (ApiKeyManager.ApiKeyFileExists(getApplicationContext())) {
            apiKey = ApiKeyManager.readApiKeyFile(getApplicationContext());
            etApikey.setText(apiKey);
        } else {
            Toast.makeText(getApplicationContext(), "Error on getting stored API key.", Toast.LENGTH_LONG).show();
        }

        buttonSaveApiKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateApiKey(etApikey.getText().toString())) {
                    ApiKeyManager.writeApiKeyFile(getApplicationContext(), etApikey.getText().toString());
                    Toast.makeText(getApplicationContext(), "API Key saved correctly.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SettingsActivity.this, CatListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid API Key format.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Boolean validateApiKey(String text) {
        boolean esValido = false;
        Pattern pattern = Pattern.compile("^live_[A-Za-z0-9]{30,80}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        esValido = matcher.find();
        return esValido;
    }


}