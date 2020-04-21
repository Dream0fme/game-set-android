package com.example.gameset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText boxReg;
    String nameReg;
    int token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnReg);
        boxReg = findViewById(R.id.editReg);
    }

    public void regOnClick(View v) {
        nameReg = boxReg.getText().toString();
        if (nameReg.length() != 0) {
            Request req = Request.registerRequest(nameReg);
            SetServerTask task = new SetServerTask(this);
            task.execute(req);
        } else {
            Toast.makeText(getApplicationContext(), "Введите имя", Toast.LENGTH_SHORT).show();
        }
    }

    public void registration() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("name", nameReg);
        startActivity(intent);
    }
}
