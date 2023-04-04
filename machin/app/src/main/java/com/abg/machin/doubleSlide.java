package com.abg.machin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class doubleSlide extends AppCompatActivity {

    private ArrayList<String> listMessages = new ArrayList<String>();
    private int indexMessages = 0;
    private static final String CHANNEL_ID = "Indice_hehe";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_slide);

        listMessages.add("Raté");
        listMessages.add("T'es nul");
        listMessages.add("Lis entre les lignes idiot");
        listMessages.add("Swish swish");
        listMessages.add("Swipe swipe");
        listMessages.add("Tu sais pas lire swipe ?");
        listMessages.add("Tchiiiiiip, swipe frr");
        listMessages.add("FRÈRE FAUT SWIPE À DEUX DOIGTS, T'ES TEUBÉ OU QUOI ?!");

        View v = findViewById(android.R.id.content).getRootView();

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();

                if(action == MotionEvent.ACTION_MOVE && motionEvent.getPointerCount() == 2) {

                    Toast.makeText(doubleSlide.this, "Bien joué", Toast.LENGTH_SHORT).show();
                    return true;
                }

                else {
                    showNotification(doubleSlide.this, "Indice", listMessages.get(indexMessages).toString());
                    indexMessages++;
                }

                return false;
            }
        });

    }

    public static void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a NotificationChannel (for API 26 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Un indice description");
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
