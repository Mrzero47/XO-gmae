package com.example.xogame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener{
    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;



    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    TextView turn;
    EditText etNX ,etNO;
    Button btBack;
    Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        // here we do enter all the buttons id to the matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "bt" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener((Main4Activity) this);
            }
        }

        turn=findViewById(R.id.turn);
        etNX=findViewById(R.id.etNX);
        etNO=findViewById(R.id.etNO);
        Intent intent=getIntent();
        if (intent.getExtras()!=null)
        {
            etNX.setText(intent.getStringExtra("key_X"));
            etNO.setText(intent.getStringExtra("key_O"));
            turn.setText(intent.getStringExtra("key_turn"));

        }

        buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener((Main4Activity) this);


        btBack=findViewById(R.id.btBack);
        btBack.setOnClickListener( (Main4Activity) this);



    }


    @Override
    public void onClick(View v) {
        //here we check if we click the back button and show the dialog if it is true
        if (v==btBack){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(Main4Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }

                }

            };
            AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
            builder.setMessage("Are you sure you want to back?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        //here we check if we (click the NEW Game) button and show the dialog if it is true
        if (v==buttonReset){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            player1Turn=true;
                            roundCount=0;
                            resetBoard();
                            turn.setText("turn:X");
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }

                }

            };
            AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
            builder.setMessage("Are you sure you wanna a new game?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        // here i check if the button that clicked is empty button or not
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        // here we put the X and O in when someone click a button
        if (player1Turn) {
            ((Button) v).setText("X");
            turn.setText("turn:O");
        } else {
            ((Button) v).setText("O");
            turn.setText("turn:X");
        }

        // here i count for rounds of play
        roundCount++;

        //here we check if there is a winner
        if (checkForWin()) {
            //if player1Turn is true so player one win
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        //here i check if we got 9 round
        } else if (roundCount == 9) {
            draw();
         //here we change the turn from player one to two
        } else {
            player1Turn = !player1Turn;
        }

    }
    // here we check if there is a winner
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        // here i enter all the buttons to the string to get checked later
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }
    // showing a dialog that the player one win and ask if you want to play more
    private void player1Wins() {


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        resetBoard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intent = new Intent(Main4Activity.this, MainActivity.class);

                        startActivity(intent);
                        finish();

                        break;
                }

            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
        builder.setMessage("Player one win,You wanna play more?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        roundCount=0;


    }
    // showing a dialog that the player two win and ask if you want to play more
    private void player2Wins() {


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        resetBoard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intent = new Intent(Main4Activity.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                        break;
                }

            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
        builder.setMessage("Player two win,You wanna play more?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        roundCount=0;
        player1Turn=true;

    }


    // if the player had draw it is showing the dialg
    private void draw() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        resetBoard();
                        turn.setText("turn:X");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intent = new Intent(Main4Activity.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                        break;
                }

            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
        builder.setMessage("It is a draw you wanna play more?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        roundCount=0;

    }

    // rest the button string and make it empty and give to play more
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

    }
}