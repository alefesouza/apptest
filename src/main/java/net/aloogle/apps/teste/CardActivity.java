package net.aloogle.apps.teste;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class CardActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00cc00")));
		findViewById(R.id.frame).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00cc00")));

		ViewCompat.setElevation(toolbar, 4);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); //fazer o ícone de voltar aparecer

		TextView text = (TextView)findViewById(R.id.textvalue);

		if(getIntent().hasExtra("text")) {
			getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
			text.setText(getIntent().getStringExtra("text"));
		} else {
			getSupportActionBar().setTitle("Um título");
			text.setText("Essa é uma activity sem valor, toca aqui");
			RelativeLayout content = (RelativeLayout)findViewById(R.id.conteudo);
			content.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View p1) {
						Intent intent = new Intent(CardActivity.this, CardActivity.class);
						intent.putExtra("title", "Outro título");
						intent.putExtra("text", "Olha agora ela tem valor, assim que se passa informações por activitys/intents");
						startActivity(intent);
					}
				});
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
