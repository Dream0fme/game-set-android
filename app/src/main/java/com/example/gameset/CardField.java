package com.example.gameset;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class CardField {
    ArrayList<Card> cards;
    ArrayList<Card> selectedCards = new ArrayList<>();
    SetCardsFieldView cardsFieldView;

    int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    float widthCard = displayWidth / 5;
    float heightCard = (displayWidth / 5) + 100;
    float cardX = widthCard / 3;
    float cardY = widthCard;

    public CardField() {
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void drawCards(Canvas canvas) {
        if (cards != null) {
            for (Card c : cards) {
                c.draw(canvas);
            }
        }
    }

    public void cardTouched(float x, float y) {
        if (cards != null) {
            if (selectedCards.size() <= 3) {
                for (Card c : cards) {
                    if (c.cardSelected(x, y)) {
                        if (selectedCards.size() == 3) {
                            if (c.selected) {
                                c.selected = false;
                                Log.i("mytag", "DELETE " + c);
                                selectedCards.remove(c);
                                break;
                            } else {
                                cardsFieldView.print("Сет не может быть больше 3-х карт!");
                                Log.i("mytag", "Сет не может быть больше 3-х карт!");
                                break;
                            }
                        } else {
                            c.selected = !c.selected;
                            if (c.selected) {
                                Log.i("mytag", "ADD " + c);
                                selectedCards.add(c);
                            } else {
                                Log.i("mytag", "DELETE " + c);
                                selectedCards.remove(c);
                            }
                            break;
                        }
                    }
                }
            }

        }
    }

    public ArrayList<Card> checkSet(ArrayList<Card> cardsToCheck) {
        Card thirdCard;
        Log.i("mytag", "карты на вход в функцию" + cardsToCheck);
        ArrayList<Card> cardSet = new ArrayList<>();
        outerLoop:
        for (int i = 0; i < cardsToCheck.size() - 1; i++) {
            for (int j = i + 1; j < cardsToCheck.size(); j++) {
                thirdCard = cardsToCheck.get(i).getThird(cardsToCheck.get(j));
                if (cardsToCheck.contains(thirdCard)) {
                    cardSet.add(cardsToCheck.get(i));
                    cardSet.add(cardsToCheck.get(j));
                    cardSet.add(cardsToCheck.get(cardsToCheck.indexOf(thirdCard)));
                    break outerLoop;
                }
            }
        }
        return cardSet;
    }

    public void showSet() {
        for (Card c : cards) {
            c.selected = false;
            selectedCards.clear();
        }
        ArrayList<Card> showSet;
        showSet = checkSet(cards);
        for (Card c : showSet) {
            c.selected = !c.selected;
            selectedCards.add(c);
        }
        cardsFieldView.invalidate();
    }


    public ArrayList<NewCard> isSelectedAreSet() {
        Card thirdCard;
        if (selectedCards.size() == 3) {
            thirdCard = selectedCards.get(0).getThird(selectedCards.get(1));
            if (thirdCard.equals(selectedCards.get(2))) {
                cardsFieldView.print("Это сет!");
            } else {
                cardsFieldView.print("Это не сет!");
                selectedCards.clear();
                for (Card c : cards) {
                    c.selected = false;
                }
                cardsFieldView.invalidate();
            }
        } else cardsFieldView.print("В сете не может быть меньше 3-х карт!");

        ArrayList<NewCard> newSet = new ArrayList<>();
        for (Card c : selectedCards) {
            NewCard clearCard = new NewCard(c.fill, c.count, c.shape, c.color);
            newSet.add(clearCard);
        }
        return newSet;
    }

    public void setSizes() {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).x = cardX;
            cards.get(i).y = cardY;
            cards.get(i).width = widthCard;
            cards.get(i).height = heightCard;
            cardX += widthCard + widthCard / 10;
            if (((i + 1) % 4) == 0) {
                cardX = widthCard / 3;
                cardY += widthCard + heightCard / 2;
            }
        }
        widthCard = displayWidth / 5;
        heightCard = (displayWidth / 5) + 100;
        cardX = widthCard / 3;
        cardY = widthCard;
    }


}
