package com.abg.machin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonStartGame;
    private Chronometer chronometer;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        chronometer = findViewById(R.id.chronometer);

        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                startGame(username);
            }
        });
    }


    private void startGame(String username) {
        // Insérez le pseudonyme dans la base de données
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pseudo", username);
        db.insert("Scores", null, values);
        db.close();

        // Démarrez le chronomètre
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void endGame() {
        // Arrêtez le chronomètre
        chronometer.stop();
        long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();

        // Récupérez le temps écoulé
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", elapsedTime);
        String selection = "pseudo=?";
        String[] selectionArgs = {editTextUsername.getText().toString()};
        db.update("Scores", values, selection, selectionArgs);
        db.close();
    }

    private void displayScores() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"pseudo", "time"};
        Cursor cursor = db.query("Scores", projection, null, null, null, null, "time ASC");
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("pseudo"));
            long time = cursor.getLong(cursor.getColumnIndexOrThrow("time"));
            Log.d("MainActivity", "Score: " + username + " - " + time);
        }
        cursor.close();
        db.close();
    }
}