package com.example.gameset;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

public class Card {
    int fill, count, shape, color;
    private Paint p = new Paint();
    private RectF cardRect = new RectF();
    float x, y, width, height;
    boolean selected = false;

    public Card() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return fill == card.fill &&
                count == card.count &&
                shape == card.shape &&
                color == card.color;
    }

    @Override
    public String toString() {
        return "Card [" +
                "count = " + count +
                ", fill = " + fill +
                ", shape = " + shape +
                ", color = " + color + "]";
    }


    public boolean cardSelected(float touchX, float touchY) {
        return (touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height);
    }

    public void draw(Canvas c) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.DKGRAY);
        cardRect.set(x, y, x + width, y + height);
        c.drawRoundRect(cardRect, 20, 20, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        // Выделение карточки при нажатии
        if (selected) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(9);
            paint.setColor(Color.rgb(85, 255, 25));
            cardRect.set(x, y, x + width, y + height);
            c.drawRoundRect(cardRect, 20, 20, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(255, 255, 205));
            c.drawRoundRect(cardRect, 20, 20, paint);
        } else {
            paint.setColor(Color.WHITE);
            cardRect.set(x, y, x + width, y + height);
            c.drawRoundRect(cardRect, 20, 20, paint);
        }
        drawFigures(c);
    }

    // отвечает за отрисовку всех фигур

    public void drawFigures(Canvas c) {
        switch (color) {
            case 1:
                p.setColor(Color.rgb(255, 50, 0));
                break;
            case 2:
                p.setColor(Color.rgb(120, 180, 0));
                break;
            case 3:
                p.setColor(Color.rgb(0, 0, 180));
                break;
        }
            // задаём заливку
        switch (fill) {
            case 1:
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(5);
                break;
            case 2:
                p.setStyle(Paint.Style.FILL);
                break;
            case 3:
                p.setPathEffect(new DashPathEffect(new float[]{15, 10}, 0));
                p.setStrokeWidth(6);
                p.setStyle(Paint.Style.STROKE);
                break;
        }
            // рисуем фигуры в соотвествии с количеством
        if (count == 1) {
            switch (shape) {
                case 1:
                    c.drawRect(multiplierX(0.25), multiplierY(0.4), multiplierX(0.75), multiplierY(0.6), p);
                    break;
                case 2:
                    c.drawCircle(multiplierX(0.5), multiplierY(0.5), diameter(0.1), p);
                    break;
                case 3:
                    cardRect.set(multiplierX(0.25), multiplierY(0.15), multiplierX(0.75), multiplierY(0.6));
                    c.drawArc(cardRect, 45, 90, true, p);
                    break;
            }
        }

        if (count == 2) {
            switch (shape) {
                case 1:
                    c.drawRect(multiplierX(0.25), multiplierY(0.25), multiplierX(0.75), multiplierY(0.45), p);
                    c.drawRect(multiplierX(0.25), multiplierY(0.55), multiplierX(0.75), multiplierY(0.75), p);
                    break;
                case 2:
                    c.drawCircle(multiplierX(0.5), multiplierY(0.35), diameter(0.1), p);
                    c.drawCircle(multiplierX(0.5), multiplierY(0.65), diameter(0.1), p);
                    break;
                case 3:
                    cardRect.set(multiplierX(0.25), multiplierY(0.05), multiplierX(0.75), multiplierY(0.45));
                    c.drawArc(cardRect, 45, 90, true, p);
                    cardRect.set(multiplierX(0.25), multiplierY(0.35), multiplierX(0.75), multiplierY(0.75));
                    c.drawArc(cardRect, 45, 90, true, p);
                    break;
            }
        }
        if (count == 3) {
            switch (shape) {
                case 1:
                    c.drawRect(multiplierX(0.25), multiplierY(0.15), multiplierX(0.75), multiplierY(0.35), p);
                    c.drawRect(multiplierX(0.25), multiplierY(0.4), multiplierX(0.75), multiplierY(0.6), p);
                    c.drawRect(multiplierX(0.25), multiplierY(0.65), multiplierX(0.75), multiplierY(0.85), p);
                    break;
                case 2:
                    c.drawCircle(multiplierX(0.5), multiplierY(0.25), diameter(0.1), p);
                    c.drawCircle(multiplierX(0.5), multiplierY(0.5), diameter(0.1), p);
                    c.drawCircle(multiplierX(0.5), multiplierY(0.75), diameter(0.1), p);
                    break;
                case 3:
                    cardRect.set(multiplierX(0.25), multiplierY(-0.1), multiplierX(0.75), multiplierY(0.30));
                    c.drawArc(cardRect, 45, 90, true, p);
                    cardRect.set(multiplierX(0.25), multiplierY(0.2), multiplierX(0.75), multiplierY(0.60));
                    c.drawArc(cardRect, 45, 90, true, p);
                    cardRect.set(multiplierX(0.25), multiplierY(0.5), multiplierX(0.75), multiplierY(0.90));
                    c.drawArc(cardRect, 45, 90, true, p);
                    break;
            }
        }
    }

    /* специальные функции для управления координатами с помощью коэффициентов
       Умножаем на определённый множитель в процентном соотношении
     */


    public float multiplierX(double coefX) {
        return (float) ((width) * (coefX) + x);
    }

    public float multiplierY(double coefY) {
        return (float) ((height) * (coefY) + y);
    }

    public float diameter(double d) {
        return (float) ((height) * (d));
    }

    public Card getThird(Card c) {
        Card thirdCard = new Card();
        if (this.fill == c.fill) thirdCard.fill = c.fill;
        else thirdCard.fill = 6 - (this.fill + c.fill);
        if (this.count == c.count) thirdCard.count = c.count;
        else thirdCard.count = 6 - (this.count + c.count);
        if (this.shape == c.shape) thirdCard.shape = c.shape;
        else thirdCard.shape = 6 - (this.shape + c.shape);
        if (this.color == c.color) thirdCard.color = c.color;
        else thirdCard.color = 6 - (this.color + c.color);
        return thirdCard;
    }
}
