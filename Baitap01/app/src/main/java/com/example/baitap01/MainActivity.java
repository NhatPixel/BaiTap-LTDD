package com.example.baitap01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button generateButton = findViewById(R.id.generateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> numberList = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < 100; i++) {
                    numberList.add(random.nextInt(1000));  // Sinh số ngẫu nhiên từ 0 đến 999
                }

                ArrayList<Integer> evenNumbers = new ArrayList<>();
                ArrayList<Integer> oddNumbers = new ArrayList<>();

                for (int number : numberList) {
                    if (number % 2 == 0) {
                        evenNumbers.add(number);
                    } else {
                        oddNumbers.add(number);
                    }
                }

                Log.d("Even Numbers", evenNumbers.toString());
                Log.d("Odd Numbers", oddNumbers.toString());
            }
        });

        EditText editText = findViewById(R.id.editText);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        // Cài đặt sự kiện khi người dùng nhấn nút "Convert String"
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = editText.getText().toString();
                String[] words = inputString.split(" ");
                StringBuilder reversedString = new StringBuilder();
                for (int i = words.length - 1; i >= 0; i--) {
                    reversedString.append(words[i]).append(" ");
                }
                String upperCaseString = reversedString.toString().toUpperCase().trim();
                resultTextView.setText(upperCaseString);
                Toast.makeText(MainActivity.this, upperCaseString, Toast.LENGTH_SHORT).show();
            }
        });
    }
}