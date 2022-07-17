package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StringBuilder display = new StringBuilder();
    private StringBuilder firstNumber = new StringBuilder();
    private StringBuilder secondNumber = new StringBuilder();
    private StringBuilder operator = new StringBuilder();
    private List<Button> buttons = new ArrayList<>();
    private List<Integer> statusList = new ArrayList<>();
    private TextView displayTextView;
    private int counterN1 = 0,counterN2 = 0,counterOp = 0,counterDis = 0;
    private Float result;
    boolean isFirstNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstNumber = true;
        initUI();

    }

    private void initUI(){
        buttons.add((Button) findViewById(R.id.button_0));
        buttons.add((Button) findViewById(R.id.button_1));
        buttons.add((Button) findViewById(R.id.button_2));
        buttons.add((Button) findViewById(R.id.button_3));
        buttons.add((Button) findViewById(R.id.button_4));
        buttons.add((Button) findViewById(R.id.button_5));
        buttons.add((Button) findViewById(R.id.button_6));
        buttons.add((Button) findViewById(R.id.button_7));
        buttons.add((Button) findViewById(R.id.button_8));
        buttons.add((Button) findViewById(R.id.button_9));
        buttons.add((Button) findViewById(R.id.button_point));
        buttons.add((Button) findViewById(R.id.button_add));
        buttons.add((Button) findViewById(R.id.button_sub));
        buttons.add((Button) findViewById(R.id.button_mul));
        buttons.add((Button) findViewById(R.id.button_div));
        buttons.add((Button) findViewById(R.id.button_delete));
        buttons.add((Button) findViewById(R.id.button_equal));
        displayTextView = findViewById(R.id.TextView_display);
        View.OnClickListener numListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.append(((Button) view).getText().toString());
                counterDis++;
                if (isFirstNumber){
                    firstNumber.append(((Button) view).getText().toString());
                    statusList.add(1);
                    counterN1++;
                }else {
                    secondNumber.append(((Button) view).getText().toString());
                    statusList.add(2);
                    counterN2++;
                }
                displayTextView.setText(display.toString());
            }
        };
        for (int i = 0;i < 11;i++){
            buttons.get(i).setOnClickListener(numListener);
        }
        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterDis == 0 && result != null){
                    firstNumber.append(result+"");
                    counterN1 = firstNumber.length();
                    counterDis = firstNumber.length();
                    display.append(result+"");
                    for (int i = 0;i < counterN1;i++){
                        statusList.add(1);
                    }
                }
                display.append(((Button) view).getText().toString());
                operator.append(((Button) view).getText().toString());
                counterDis++;
                counterOp++;
                statusList.add(3);
                displayTextView.setText(display.toString());
                isFirstNumber = false;
            }
        };
        for (int i = 11;i < 15;i++){
            buttons.get(i).setOnClickListener(operatorListener);
        }
        buttons.get(15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusList.size() != 0){
                    switch (statusList.get(statusList.size()-1)){
                        case 1:
                            display.deleteCharAt(counterDis-1);
                            counterDis--;
                            firstNumber.deleteCharAt(counterN1-1);
                            counterN1--;
                            displayTextView.setText(display.toString());
                            statusList.remove(statusList.size()-1);
                            break;
                        case 2:
                            display.deleteCharAt(counterDis-1);
                            counterDis--;
                            secondNumber.deleteCharAt(counterN2-1);
                            counterN2--;
                            displayTextView.setText(display.toString());
                            statusList.remove(statusList.size()-1);
                            break;
                        case 3:
                            display.deleteCharAt(counterDis-1);
                            counterDis--;
                            operator.deleteCharAt(counterOp-1);
                            counterOp--;
                            displayTextView.setText(display.toString());
                            statusList.remove(statusList.size()-1);
                            break;
                    }

                }
            }
        });
        buttons.get(16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if (operator.length() > 1 || firstNumber.length() == 0 || secondNumber.length() == 0){
                    error = true;
                }
//                for (int i = 0;i < statusList.size()-1;i++){
//                    if (statusList.get(i).equals(3)){
//                        if (statusList.get(i+1).equals(3)){
//                            error = true;
//                        }
//                    }
//                }
                for (int i = 0;i < firstNumber.length()-1;i++){
                    if (firstNumber.substring(i,i+1).equals(".")){
                        if (firstNumber.substring(i+1).contains(".")){
                            error = true;
                            break;
                        }
                    }
                }
                for (int i = 0;i < secondNumber.length()-1;i++){
                    if (secondNumber.substring(i,i+1).equals(".")){
                        if (secondNumber.substring(i+1).contains(".")){
                            error = true;
                            break;
                        }
                    }
                }
                if (error){
                    displayTextView.setText("ERROR");
                    display.delete(0,counterDis);
                    operator.delete(0,counterOp);
                    firstNumber.delete(0,counterN1);
                    secondNumber.delete(0,counterN2);
                    statusList.clear();
                    counterOp = 0;
                    counterDis = 0;
                    counterN1 = 0;
                    counterN2 = 0;
                    isFirstNumber = true;
                    result = null;
                }else {
                    float number1 = Float.parseFloat(firstNumber.toString());
                    float number2 = Float.parseFloat(secondNumber.toString());
                    switch (operator.toString())
                    {
                        case "+":
                            result = Functions.addition(number1,number2);
                            displayTextView.setText(result+"");
                            break;
                        case "-":
                            result = Functions.subtraction(number1,number2);
                            displayTextView.setText(result+"");
                            break;
                        case "*":
                            result = Functions.multiplication(number1,number2);
                            displayTextView.setText(result+"");
                            break;
                        case "/":
                            result = Functions.division(number1,number2);
                            displayTextView.setText(result+"");
                            break;
                        default:
                            Log.d("ERROR", "onClick: ERROR");
                            break;
                    }
                    display.delete(0,counterDis);
                    operator.delete(0,counterOp);
                    firstNumber.delete(0,counterN1);
                    secondNumber.delete(0,counterN2);
                    statusList.clear();
                    counterOp = 0;
                    counterDis = 0;
                    counterN1 = 0;
                    counterN2 = 0;
                    isFirstNumber = true;
                }

            }
        });
    }

}
