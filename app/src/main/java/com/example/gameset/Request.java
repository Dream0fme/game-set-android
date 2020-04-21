package com.example.gameset;

import java.util.ArrayList;

class NewCard {
    int fill, count, shape, color;

    public NewCard(int fill, int count, int shape, int color) {
        this.fill = fill;
        this.count = count;
        this.shape = shape;
        this.color = color;
    }
}
public class Request {
    String action, nickname;
    int token;
    ArrayList<NewCard> cards;


    public static Request registerRequest(String nickname) {
        return new Request("register", nickname);
    }

    public static Request fetchСardsRequest(int token) {
        return new Request("fetch_cards", token);
    }

    public static Request takeSetRequest(int token, ArrayList<NewCard> cards) {
        return new Request("take_set", token, cards);
    }

    // Регистрация
    public Request(String action, String nickname) {
        this.action = action;
        this.nickname = nickname;
    }

    // получение набора карт
    public Request(String action, int token) {
        this.action = action;
        this.token = token;
    }

    // Выбор сета
    public Request(String action, int token, ArrayList<NewCard> cards) {
        this.action = action;
        this.cards = cards;
        this.token = token;
    }
    
}
