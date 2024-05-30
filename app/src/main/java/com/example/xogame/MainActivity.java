package com.example.xogame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    RadioGroup rgSelect;
    RadioButton rbHvsH , rbHvsP;
    TextView tvX ,tvO;
    EditText etNameX , etNameO;
    Button btStart , btAbout;
    final String File_Name="Checked";
    String SaveX="",SaveO="",turn="X";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgSelect=findViewById(R.id.rgSelect);
        rbHvsH=findViewById(R.id.rbHvsH);
        rbHvsP=findViewById(R.id.rbHvsP);
        tvX=findViewById(R.id.tvX);
        tvO=findViewById(R.id.tvO);
        etNameX=findViewById(R.id.etNameX);
        etNameO=findViewById(R.id.etNameO);
        btStart=findViewById(R.id.btStart);
        btAbout=findViewById(R.id.btAbout);


        //בחירת השחקן אם עם המחשב אן בן אדם
        rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbHvsH) {
                    etNameO.setText("");
                    etNameO.setEnabled(true);
                }
                else {

                    etNameO.setText("bot");
                    etNameO.setEnabled(false);
                }
            }
        });

       // it is showing the dialog of the about
        btAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Creators: ");
                alertDialog.setMessage("Game XO Made by Sundos Tamimi & Kareem shehada ");

                alertDialog.show();
            }
        });
        //this method is start the game and show us the second windows
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SaveXLen=etNameX.getText().toString();
                String SaveOLen =etNameO.getText().toString();
                Intent intent=intent =new Intent();

                if (SaveXLen.length() > 0 && SaveOLen.length() > 0) {
                    if (rbHvsH.isChecked()){
                        SaveX+=etNameX.getText();
                        SaveO+=etNameO.getText();

                    }
                    else {
                        SaveO += "bot";
                        SaveX += etNameX.getText();
                        etNameO.setText(SaveO);
                        etNameO.setEnabled(false);
                    }
                    if(rbHvsH.isChecked()){
                        intent =new Intent(MainActivity.this,Main4Activity.class);
                    }
                    else
                        intent =new Intent(MainActivity.this,MainActivity2.class);

                    intent.putExtra("key_X",SaveX);
                    intent.putExtra("key_O",SaveO);
                    intent.putExtra("key_turn","turn: "+turn);

                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "You need to full all of the fields right", Toast.LENGTH_LONG).show();

                SaveDetalis();


            }
        });
        LoadDetalis();  // קריאה לפונקצית טעינת הנתונים השמורים בזיכרון
    }
    //Function to save data in shared PerFerences file
    public void SaveDetalis(){
        SharedPreferences sp = getSharedPreferences(File_Name,MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();

        edit.putInt("key_Player", rgSelect.getCheckedRadioButtonId());
        edit.putString("key_PlayerXName",etNameX.getText().toString());
        edit.putString("key_PlayerOName",etNameO.getText().toString());


        edit.apply();
    }
    //  this func is used to load the data to the other window
    public void LoadDetalis(){
        SharedPreferences sp = getSharedPreferences(File_Name,MODE_PRIVATE);

        etNameX.setText(sp.getString("key_X",SaveX));
        etNameO.setText(sp.getString("key_O",SaveO));


    }




}
