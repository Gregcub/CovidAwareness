package com.gac5206.covidawareness.news.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gac5206.covidawareness.R;
import com.gac5206.covidawareness.news.fragments.SavedNewsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WebActivity2 extends AppCompatActivity {
    FloatingActionButton close;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        webView = findViewById(R.id.activity_web2);
        close = findViewById(R.id.close_news2);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        close.setOnClickListener(this::OnClick);
    }

    private void OnClick(View view) {

        switch(view.getId()){

            case R.id.close_news2:
                finish();
                Toast.makeText(this, "Closed news!", Toast.LENGTH_LONG).show();
                break;


        }
    }
}