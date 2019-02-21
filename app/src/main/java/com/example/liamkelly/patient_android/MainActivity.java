package com.example.liamkelly.patient_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liamkelly.patient_android.user.CurrentUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
    }

    private void initButton(int btnID, View.OnClickListener listener) {
        Button btn = (Button)findViewById(btnID);
        btn.setOnClickListener(listener);
    }

    private void initButtons() {
        initButton(R.id.sign_up, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Insert sign up here", Toast.LENGTH_SHORT).show();
            }
        });

        initButton(R.id.sign_in, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Incredibly Secure, Sophisticated Sign On");

                final EditText input = new EditText(MainActivity.this);
                input.setHint("enter your name");
                dialogBuilder.setView(input);
                dialogBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String entered = input.getText().toString();
                        if (entered.length() > 0) {
                            CurrentUser.getInstance().setName(input.getText().toString());
                            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter your login credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogBuilder.show();
            }
        });
    }
}
