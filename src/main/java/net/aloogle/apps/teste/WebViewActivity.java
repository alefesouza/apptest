package net.aloogle.apps.teste;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
	WebView webView;
	EditText edit;
	ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc0000")));
		findViewById(R.id.frame).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc0000")));

		ViewCompat.setElevation(toolbar, 4);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); //fazer o ícone de voltar aparecer

		getSupportActionBar().setTitle("WebView");

		progress = (ProgressBar)findViewById(R.id.progress);
		edit = (EditText)findViewById(R.id.editText1);
		edit.setText("http://google.com.br");

		Button go = (Button)findViewById(R.id.go);
		go.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					webView.loadUrl(edit.getText().toString());
					progress.setVisibility(View.VISIBLE);
				}
			});

		webView = (WebView)findViewById(R.id.webview);

		webView.setWebViewClient(new webViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setDisplayZoomControls(false);

		webView.loadUrl("file:///android_asset/exemplo.html");
    }

	public class webViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			getSupportActionBar().setTitle(webView.getTitle());
			progress.setVisibility(View.GONE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			String error = "" +
				"<p style=\"text-align: justify;\">Ops, houve um erro</p>\n" +
				"<ul>\n" +
				"<li>Tente novamente conectado &agrave; internet.</li>\n" +
				"</ul>\n";
			webView.loadData(error, "text/html; charset=utf-8", "UTF-8");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: // faz fechar a activity ao tocar no ícone de voltar
				finish();
				return true;
			default:
				return
					super.onOptionsItemSelected(item);
		}
	}
}
