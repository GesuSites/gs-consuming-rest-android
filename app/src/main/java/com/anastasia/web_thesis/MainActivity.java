package com.anastasia.web_thesis;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends Activity {

    Button buttonTulips;
    Button buttonTomattoes;
    TextView textView;

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Это обработчик нажатия на пукт меню action bar. Аction bar будет
        // автоматически обрабатывать нажатия Home/Up кнопки, до тех пор
        // пока вы вы определите их действия в родительском activity в AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.b1) {
            new HttpRequestTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // search view elements
        buttonTulips = findViewById(R.id.buttonTulips);
        buttonTomattoes = findViewById(R.id.buttonTomattoes);
        textView = findViewById(R.id.textView);

        // create touch
        OnClickListener oclBtnTulips = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                textView.setText(
                        "Была нажата кнопка \"Тюльпаны\"...\n\nЕсли вы видите это сообщение, то данные не были получены, а потому, пожалуйста, проверьте работоспособность сети и сервера."
                );
                new HttpRequestTask().execute();
            }
        };

        // присвоим обработчик кнопке OK (buttonTulips)
        buttonTulips.setOnClickListener(oclBtnTulips);
        // create touch
        OnClickListener oclBtnTomattoes = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                textView.setText(
                        "Была нажата кнопка \"Помидоры\"...\n\nЕсли вы видите это сообщение, то данные не были получены, а потому, пожалуйста, проверьте работоспособность сети и сервера."
                );
                new HttpRequestTask().execute();
            }
        };

        // присвоим обработчик кнопке OK (buttonTulips)
        buttonTomattoes.setOnClickListener(oclBtnTomattoes);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try { // https://stackoverflow.com/a/6310592/8175291
                final String url = "http://10.0.2.2:8080/greeting"; //"https://www.google.ru/"; //"http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                textView.setText(e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            TextView greetingIdText = findViewById(R.id.textView);
            //TextView greetingContentText = findViewById(R.id.content_value);
            greetingIdText.setText(
                    "{\"id\": " + greeting.getId() + ", \"content\": " + greeting.getContent() + "}"
            );
            //greetingContentText.setText(greeting.getContent());
        }

    }
}