package com.example.caloriyat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    EditText weight;
    EditText height;
    RadioButton male;
    RadioButton female;
    RadioGroup genders;
    Spinner ages;
    Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        ages = findViewById(R.id.spinner);
        genders = findViewById(R.id.radioGroup);
        myIntent = new Intent(this, MainActivity3.class);
    }
    public void checkFit(View view){
        if(TextUtils.isEmpty(weight.getText().toString()) || TextUtils.isEmpty(height.getText().toString())){
            if(TextUtils.isEmpty(weight.getText().toString())) weight.setError("Missing Weight");
            if(TextUtils.isEmpty(height.getText().toString())) height.setError("Missing Height");
        }
        else{
            int weightValue = Integer.parseInt(weight.getText().toString());
            double heightValue = Double.parseDouble(height.getText().toString()) / 100;
            double bmi = weightValue / Math.pow(heightValue, 2);
            if (bmi < 16) {
                Toast.makeText(this, String.format(Locale.getDefault(), "Your BMI is %.2f. You are severely underweight.", bmi), Toast.LENGTH_LONG).show();
            } else if (bmi >= 16 && bmi < 18.5) {
                Toast.makeText(this, String.format(Locale.getDefault(),"Your BMI is %.2f. You are underweight!", bmi), Toast.LENGTH_LONG).show();
            } else if (bmi >= 18.5 && bmi < 25) {
                Toast.makeText(this, String.format(Locale.getDefault(),"Your BMI is %.2f. You are normal.", bmi), Toast.LENGTH_LONG).show();
            } else if (bmi >= 25 && bmi < 30) {
                Toast.makeText(this, String.format(Locale.getDefault(),"Your BMI is %.2f. You are overweight!", bmi), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, String.format(Locale.getDefault(),"Your BMI is %.2f. You are severely overweight!", bmi), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void howMuchEat(View view){
        if(TextUtils.isEmpty(weight.getText().toString()) || TextUtils.isEmpty(height.getText().toString())){
            if(TextUtils.isEmpty(weight.getText().toString())) weight.setError("Missing Weight");
            if(TextUtils.isEmpty(height.getText().toString())) height.setError("Missing Height");
        }else{
            int quantity = 0;
            int weightValue = Integer.parseInt(weight.getText().toString());
            int heightValue = Integer.parseInt(height.getText().toString());
            if(male.isChecked()){
                quantity = (int) (665 + (6.3 * kgToPound(weightValue)) + (12.9 * cmToInches(heightValue)) - (6.8 * 24));
            }
            if(female.isChecked()){
                int quantityAbove24 = (int) (455 + (4.3 * kgToPound(weightValue)) + (4.7 * cmToInches(heightValue)) - (4.7 * 24));
                int quantityBelow24 = (int) (655 + (4.3 * kgToPound(weightValue)) + (4.7 * cmToInches(heightValue)) - (4.7 * 24));
                quantity = ((ages.getSelectedItem().equals("Above 24"))?quantityAbove24:quantityBelow24);
            }
            myIntent.putExtra("quantity",quantity);
            startActivity(myIntent);
        }

    }

    private double kgToPound(Integer weight){
        return weight * 2.2046;
    }

    private double cmToInches(Integer height){
        return height/2.5;
    }
}