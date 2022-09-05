package com.yh.devicehelper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int NON_AUTOSTART_INTENT_FLAGS = 270532608;

    private SharedPreferences preferences;

    private Resources resources;
    private TextView lockStatusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        resources = getResources();

        Button lockBtn = findViewById(R.id.lockBtn);
        Button changePasswordBtn = findViewById(R.id.changePasswordBtn);

        Button startLockBtn = findViewById(R.id.startLockBtn);
        Button stopLockBtn = findViewById(R.id.stopLockBtn);

        lockStatusTV = findViewById(R.id.lockStatusTV);

        lockBtn.setOnClickListener(view -> {
            boolean lockStatus = preferences.getBoolean("lockStatus", false);
            if (lockStatus) {
                Intent lockScreenServiceIntent = new Intent(this, LockScreenService.class);
                startService(lockScreenServiceIntent);
            }
        });

        changePasswordBtn.setOnClickListener(view -> {
            Intent passwordActivityIntent = new Intent(this, ChangePasswordActivity.class);
            startActivity(passwordActivityIntent);
        });

        startLockBtn.setOnClickListener(view -> {
            preferencesEditor.putBoolean("lockStatus", true);
            preferencesEditor.apply();
            updateStatuses();

        });

        stopLockBtn.setOnClickListener(view -> {
            preferencesEditor.putBoolean("lockStatus", false);
            preferencesEditor.apply();
            updateStatuses();
        });

        updateStatuses();
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestPermissions();

        boolean lockStatus = preferences.getBoolean("lockStatus", false);
        if (lockStatus) {
            Intent lockScreenServiceIntent = new Intent(this, LockScreenService.class);
            startService(lockScreenServiceIntent);
        }

        Intent mainActivityIntent = getIntent();
        int mainActivityIntentFlags = mainActivityIntent.getFlags();

        if (mainActivityIntentFlags != NON_AUTOSTART_INTENT_FLAGS) {
            finish();
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
            }
        }
    }

    private void updateStatuses() {
        if (preferences.getBoolean("lockStatus", false)) {
            lockStatusTV.setText("Статус: ВКЛ.");
            lockStatusTV.setTextColor(resources.getColor(R.color.green));
        } else {
            lockStatusTV.setText("Статус: ВЫКЛ.");
            lockStatusTV.setTextColor(resources.getColor(R.color.red));
        }
    }
}