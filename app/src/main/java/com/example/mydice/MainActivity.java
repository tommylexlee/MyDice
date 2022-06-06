package com.example.mydice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static boolean cheatMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (MotionEvent.ACTION_UP == action) {
            Random rand = new Random();
            rollDice();
            return true;
        }

        return false;
    }

    /**
     * Generates two random number, adds them together and check if the result is equals to the targetNum
     * If it is, then store the corresponding two random numbers in a list and return it
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void rollDice() {

        LocalDateTime date = LocalDateTime.now();
        int curSec = date.toLocalTime().toSecondOfDay();
        int timeValue = curSec % 12; //Generates a value from 0 - 11 (both inclusive)
        int targetNum = timeValue + 1; //Adds 1 so that the max value attainable is 12 instead of 11
        Log.d("rollDice", "timeValue: " + timeValue);
        Log.d("rollDice", "curSec: " + curSec);
        Log.d("rollDice", "targetNum: " + targetNum);

        Random rand = new Random();
        List<Integer> correctPair = new ArrayList<Integer>();

        int num1 = 0;
        int num2 = 0;
        int counter = 0;
        boolean found = false;

        while (!found) {
             counter++;
             num1 = rand.nextInt(6) + 1;
             num2 = rand.nextInt(6) + 1;

             if (!cheatMode || targetNum == 1 || (num1 + num2) == targetNum) {
                 found = true;
             }
        }
        Log.d("Counter", "counter: " + counter);

        displayDices(num1, num2);
    }

    private void displayDices(int num1, int num2) {
        Log.d("displayDices", "displayDices: start");
        ImageView iv = findViewById(R.id.diceTop);
        setDiceImage(num1, iv);
        iv = findViewById(R.id.diceBottom);
        setDiceImage(num2, iv);
    }

    public void beginRow(View view) {
        Button b = (Button) view;
        Log.d("View", "beginRow: " + b.getText());

        //rollDice(Integer.valueOf(b.getText().toString()));
    }

    private void setDiceImage(int num, ImageView iv) {
        Log.d("setDiceImage", "num: " + num);
        Log.d("setDiceImage", "iv: " + iv.toString());
        switch (num) {
            case 1:
                iv.setImageResource(R.drawable.dice1);
                break;
            case 2:
                iv.setImageResource(R.drawable.dice2);
                break;
            case 3:
                iv.setImageResource(R.drawable.dice3);
                break;
            case 4:
                iv.setImageResource(R.drawable.dice4);
                break;
            case 5:
                iv.setImageResource(R.drawable.dice5);
                break;
            case 6:
                iv.setImageResource(R.drawable.dice6);
                break;
        }
    }

    public void toggleCheatMode(View view) {
        cheatMode = !cheatMode;
        setInsText(cheatMode);

        Button b = (Button) view;
        Log.d("View", "beginRow: " + b.getText());

        //rollDice(Integer.valueOf(b.getText().toString()));

        Log.d("toggleCheatMode", "toggleCheatMode: " + cheatMode);
    }

    public static boolean isCheatMode() {
        return cheatMode;
    }

    public static void setCheatMode(boolean cheatMode) {
        MainActivity.cheatMode = cheatMode;
    }

    public void setInsText(boolean cheatMode){

        ViewGroup container = findViewById(R.id.layout_container_buttons);

        String text = null;

        if (cheatMode) {
            text = getResources().getString(R.string.ins_cheat_on);
        } else {
            text = getResources().getString(R.string.ins_cheat_off);
        }

        TextView insText = findViewById(R.id.insText);
        insText.setText(text);
    }

}