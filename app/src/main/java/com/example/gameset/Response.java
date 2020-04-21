package com.example.gameset;

import java.util.ArrayList;

public class Response {
    /* TODO: Дополнить класс полями согласно синтаксису возможных ответов сервера
     */
    String status;
    int token, points, cards_left;
    ArrayList<Card> cards;
}
