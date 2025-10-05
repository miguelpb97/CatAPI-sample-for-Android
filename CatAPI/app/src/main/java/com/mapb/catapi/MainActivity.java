package com.mapb.catapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSaveApiKey = findViewById(R.id.button_save_api_key);
        EditText etApikey = findViewById(R.id.edit_text_api_key);

        String apiKey = "";

        if (ApiKeyManager.ApiKeyFileExists(getApplicationContext())) {
            apiKey = ApiKeyManager.readApiKeyFile(getApplicationContext());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "API Key file didn't exists.", Toast.LENGTH_LONG);
            toast.show();
        }

        if (!apiKey.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, CatListActivity.class);
            startActivity(intent);
            finish();
        }

        buttonSaveApiKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateApiKey(etApikey.getText().toString())) {
                    ApiKeyManager.writeApiKeyFile(getApplicationContext(), etApikey.getText().toString());
                    Intent intent = new Intent(MainActivity.this, CatListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid API Key format.", Toast.LENGTH_LONG);
                    toast.show();
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