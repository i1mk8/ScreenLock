package com.yh.devicehelper;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class LockScreenService extends Service {
    private WindowManager windowManager;
    private ViewGroup passwordViews;

    private String tempPassword = "";

    private SharedPreferences preferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()  {
        super.onCreate();

        preferences = getSharedPreferences("data", MODE_PRIVATE);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        passwordViews = (ViewGroup) layoutInflater.inflate(R.layout.activity_password, null);

        // Кнопки для ввода пароля
        Button passwordBtn1 = passwordViews.findViewById(R.id.passwordBtn1);
        Button passwordBtn2 = passwordViews.findViewById(R.id.passwordBtn2);
        Button passwordBtn3 = passwordViews.findViewById(R.id.passwordBtn3);
        Button passwordBtn4 = passwordViews.findViewById(R.id.passwordBtn4);
        Button passwordBtn5 = passwordViews.findViewById(R.id.passwordBtn5);
        Button passwordBtn6 = passwordViews.findViewById(R.id.passwordBtn6);
        Button passwordBtn7 = passwordViews.findViewById(R.id.passwordBtn7);
        Button passwordBtn8 = passwordViews.findViewById(R.id.passwordBtn8);
        Button passwordBtn9 = passwordViews.findViewById(R.id.passwordBtn9);

        passwordBtn1.setOnClickListener(view -> onClickPasswordButton(1));
        passwordBtn2.setOnClickListener(view -> onClickPasswordButton(2));
        passwordBtn3.setOnClickListener(view -> onClickPasswordButton(3));
        passwordBtn4.setOnClickListener(view -> onClickPasswordButton(4));
        passwordBtn5.setOnClickListener(view -> onClickPasswordButton(5));
        passwordBtn6.setOnClickListener(view -> onClickPasswordButton(6));
        passwordBtn7.setOnClickListener(view -> onClickPasswordButton(7));
        passwordBtn8.setOnClickListener(view -> onClickPasswordButton(8));
        passwordBtn9.setOnClickListener(view -> onClickPasswordButton(9));

        Button cancelBtn = passwordViews.findViewById(R.id.cancelBtn);
        cancelBtn.setVisibility(View.INVISIBLE);

        int layoutType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutType = WindowManager.LayoutParams.TYPE_TOAST;
        }

        WindowManager.LayoutParams passwordWindowLayoutParams = new WindowManager.LayoutParams(
                displayMetrics.widthPixels,
                displayMetrics.heightPixels - 50,
                layoutType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        windowManager.addView(passwordViews, passwordWindowLayoutParams);
    }

    private void onClickPasswordButton(int id) {
        passwordViews.setBackgroundResource(R.color.grey);
        tempPassword += id;
        if (tempPassword.length() == 4) {
            if (tempPassword.equals(preferences.getString("password", "1111"))) {
                windowManager.removeView(passwordViews);
                stopService(new Intent(this, LockScreenService.class));
            } else {
                passwordViews.setBackgroundResource(R.color.red);
            }
            tempPassword = "";
        }
    }
}
