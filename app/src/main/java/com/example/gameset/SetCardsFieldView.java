package com.example.gameset;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class SetCardsFieldView extends View {
    CardField cardField = new CardField();
    int width, height;

    public SetCardsFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cardField.cardsFieldView = SetCardsFieldView.this;
    }

    public SetCardsFieldView(Context context) {
        super(context);
    }


    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        cardField.drawCards(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cardField.cardTouched(x, y);
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void print(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
