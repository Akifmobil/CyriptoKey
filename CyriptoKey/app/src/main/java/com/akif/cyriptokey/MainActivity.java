package com.akif.cyriptokey;



import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private TextView txtPassword, txtLength;
    private SeekBar seekBarLength;
    private Button btnGenerate, btnCopy;
    private int passwordLength = 12; // Varsayılan uzunluk

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Elemanlarını Tanımlama
        txtPassword = findViewById(R.id.txtPassword);
        txtLength = findViewById(R.id.txtLength);
        seekBarLength = findViewById(R.id.seekBarLength);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnCopy = findViewById(R.id.btnCopy);

        // SeekBar Değiştiğinde
        seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLength = Math.max(progress, 4); // Minimum 4 karakter
                txtLength.setText("Şifre Uzunluğu: " + passwordLength);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Şifre Üret Butonu
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String generatedPassword = generatePassword(passwordLength);
                txtPassword.setText(generatedPassword);
            }
        });

        // Kopyala Butonu
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(txtPassword.getText().toString());
            }
        });
    }

    // Şifre Üretme Fonksiyonu
    private String generatePassword(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    // Şifreyi Panoya Kopyalama Fonksiyonu
    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager).getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Generated Password", text);
        clipboard.setPrimaryClip(clip);
    }
}
