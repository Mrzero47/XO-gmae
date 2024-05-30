package com.example.xogame;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];




    private int roundCount;

    private Random ran=new Random();

    int row;
    int col;



    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    Button btBack;
    Button buttonReset;
    EditText etNO,etNX;
    TextView turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        turn=findViewById(R.id.turn);

        etNX= findViewById(R.id.etNX);
        etNO= findViewById(R.id.etNO);

        Intent intent=getIntent();
        if (intent.getExtras()!=null)
        {
            etNX.setText(intent.getStringExtra("key_X"));
            etNO.setText(intent.getStringExtra("key_O"));
            turn.setText(intent.getStringExtra("key_turn"));

        }
        // here i do enter all the buttons id to the matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(MainActivity2.this);
            }
        }

        buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener((MainActivity2) this);


        btBack=findViewById(R.id.btBack);

        btBack.setOnClickListener( (MainActivity2) this);

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
                            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }

                }

            };
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
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

                            roundCount=0;
                            resetBoard();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }

                }

            };
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setMessage("Are you sure you wanna a new game?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        // here we check if the button you click is clicked
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        ((Button) v).setText("X");

        // here i count for rounds of play
        roundCount++;

        if (checkForWin()) {
            player1Wins();
        } else if (roundCount == 5) {
            draw();
        } else {
            botMove();
        }



    }
    private void botMove(){
        String[][] field = new String[3][3];
        // enter all buttons to the string matrix to get check them later
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //check where is the empty button and make the bot move
        for (int i = 0; i < 18; i++) {
            row=ran.nextInt(3);
            col=ran.nextInt(3);
            if (field[row][col].equals("")){
                buttons[row][col].setText("O");
                break;
            }
        }
        //check if the there is a winner
        if (checkForWin()) {
            player2Wins();
            //check if we got 5 rounds so there is draw
        } else if (roundCount ==5) {
            draw();
        }
    }

    //here we check if there is a winner
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        // enter all buttons to the string matrix to get check them later
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

        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        resetBoard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                        break;
                }

            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("Player one win,You wanna play more?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    // showing a dialog that the player two win and ask if you want to play more
    private void player2Wins() {

        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        resetBoard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                        break;
                }

            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("Bot win,You wanna play more?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
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
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                        break;
                }

            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("It is a draw you wanna play more?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    // rest the button string and make it empty and give to play more
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;

    }

}