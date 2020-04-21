package com.example.gameset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    SetCardsFieldView cardsFieldView;
    TextView tv, tvPoint;
    Button bSet, bFetch;
    int token;
    ArrayList<NewCard> selectCards = new ArrayList<>();
    int cardsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cardsFieldView = findViewById(R.id.view);
        Intent in = getIntent();
        String regName = in.getStringExtra("name");
        token = in.getIntExtra("token", 0);
        tv = findViewById(R.id.tvName);
        tvPoint = findViewById(R.id.tvPoint);
        bSet = findViewById(R.id.btnSet);
        bFetch = findViewById(R.id.btnFetch);
        tv.setText("Nickname: " + regName);
        fetchCards();
    }

    public void fetchCardsOnClick(View v) {
        updateCards();
        fetchCards();
    }

    public void updateCards() {
        selectCards.clear();
        cardsFieldView.cardField.selectedCards.clear();
        cardsFieldView.cardField.cards.clear();
        fetchCards();
    }


    public void fetchCards() {
        Request req = Request.fetchСardsRequest(token);
        SetServerTask task = new SetServerTask(this);
        cardsFieldView.invalidate();
        task.execute(req);

    }

    public void setCardsSet(View v) {
        selectCards = cardsFieldView.cardField.isSelectedAreSet();
        Log.i("mytag", "Выбранный сет " + selectCards);
        if (selectCards.size() == 3) {
            Request req = Request.takeSetRequest(token, selectCards);
            SetServerTask task = new SetServerTask(this);
            task.execute(req);
        } else print("Это не сет!");
    }

    public void print(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void showSetCards(View v) {
        cardsFieldView.cardField.showSet();
    }

    public void fetchCardResponse(ArrayList<Card> cards) {
        ArrayList<Card> testSet = cardsFieldView.cardField.checkSet(cards);
        Log.i("mytag", "РАЗМЕР СЕТА: " + testSet.size());
        if (cards.size() == 12 & testSet.size() == 3) {
            cardsFieldView.cardField.setCards(cards);
            cardsFieldView.cardField.setSizes();
            cardsFieldView.invalidate();
        } else if (cardsLeft >= 12 & testSet.size() == 3) {
            fetchCards();
        } else print("Карты закончились");
    }
}

