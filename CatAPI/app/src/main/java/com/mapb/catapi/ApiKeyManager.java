package com.mapb.catapi;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class ApiKeyManager {
    private static final String FILE_NAME = "config.dat";

    public ApiKeyManager() {
    }

    public static void writeApiKeyFile(Context context, String text) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(context.getFilesDir(), FILE_NAME), true));
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException ioex) {
            Log.d("writeApiKeyFile", ioex.getMessage());
        }
    }

    public static String readApiKeyFile(Context context) {
        String linea = "";
        BufferedReader br1 = null;
        try {
            br1 = new BufferedReader(new FileReader(new File(context.getFilesDir(), FILE_NAME)));
            linea = br1.readLine();
        } catch (IOException ioex) {
            Log.d("readApiKeyFile", ioex.getMessage());
            return linea;
        }
        return linea;
    }

    public static Boolean ApiKeyFileExists(Context context) {
        boolean exists = false;
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (file.exists() && file.isFile()) {
            exists = true;
        }
        return exists;
    }
}