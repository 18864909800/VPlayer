package com.ashin.vplayer.Okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ashin.vplayer.R;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "OkhttpActivity";
    private Button button1;
    private Button button2;
    private TextView textView1;
    private String url = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        button1 = findViewById(R.id.httpButton1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.httpButton2);
        button2.setOnClickListener(this);
        textView1 = findViewById(R.id.httpText);
    }

    public void getHttp(String url) {
        Log.d(TAG, "url: " + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = String.valueOf(response.body());
                Log.d(TAG, "onResponse: " + body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView1.setText(body);
                    }
                });
            }
        });
    }

    public void postHttp(String url) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                String body = String.valueOf(response.body());
                Log.d(TAG, "onResponse: " + body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView1.setText(body);
                    }
                });
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.httpButton1:
                Log.d(TAG, String.valueOf(view.getId()));
                getHttp(url);
                break;
            case R.id.httpButton2:
                postHttp(url);
                break;
        }
    }
}