package com.example.gameset;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class SetServerTask extends AsyncTask<Request, Void, Response> {
    GameActivity gameActivity;
    MainActivity mainActivity;
    String action;

    public SetServerTask(GameActivity gameActivity) {
        this.gameActivity = gameActivity;

    }

    public SetServerTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Response requestToSetServer(Request req) {
        action = req.action;
        Gson gson = new Gson();
        // TODO: указывайте нужный порт
        String API_URL = "http://194.176.114.21:8762";
        try {
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // setting POST method
            OutputStream out = urlConnection.getOutputStream();
            // сериализованный объект-запрос пишем в поток
            String outJSON = gson.toJson(req);
            Log.d("mytag", "out: " + outJSON);
            out.write(outJSON.getBytes());

            InputStream stream = urlConnection.getInputStream();
            return gson.fromJson(new InputStreamReader(stream), Response.class);

        } catch (ConnectException e) {
            Toast.makeText(gameActivity, "Подключение к серверу отсутсвтует", Toast.LENGTH_SHORT).show();
            Log.e("mytag", "out: " + e.toString());
        } catch (MalformedURLException e) {
            Toast.makeText(gameActivity, "Неверный url сервера", Toast.LENGTH_SHORT).show();
            Log.e("mytag", "out: " + e.toString());
        } catch (IOException e) {
            Log.d("mytag", "out: " + e.toString());
        }
        return null;
    }

    //ps axu | grep set-server-aurumov-8051.py
    @Override
    protected Response doInBackground(Request... requests) {
        Request r = requests[0];
        return requestToSetServer(r);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(final Response response) {
        switch (action) {
            case "register":
                Log.i("mytag", "token: " + response.token);
                mainActivity.token = response.token;
                mainActivity.registration();
                Log.i("mytag", "token in main: " + mainActivity.token);
                break;
            case "fetch_cards":
                Log.i("mytag", "Cards " + response.cards);
                gameActivity.fetchCardResponse(response.cards);
                break;
            case "take_set":
                Log.i("mytag", "status " + response.status);
                Log.i("mytag", "cardsleft " + response.cards_left);
                gameActivity.cardsLeft = response.cards_left;
                gameActivity.tvPoint.setText("Очки: " + response.points);
                gameActivity.updateCards();
                break;
        }
    }
}
