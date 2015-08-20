package net.aloogle.apps.teste;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.text.*;
import android.text.method.*;

public class MainActivity extends AppCompatActivity {
	String titulo;
	TextView numero, yesorno;
	SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000cc"))); // cor da toolbar
		findViewById(R.id.frame).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000cc"))); // cor da barra de status
		getSupportActionBar().setTitle("Teste");
		
		ViewCompat.setElevation(toolbar, 4); // sombra embaixo da toolbar

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		titulo = "Título da notificação";
		numero = (TextView)findViewById(R.id.numero);
		yesorno = (TextView)findViewById(R.id.yesorno);

		numero.setText(getNumber(preferences.getString("prefNumeros", "1")));
		yesorno.setText(String.valueOf(preferences.getBoolean("prefYesorno", true)));

		Button dialogbutton = (Button)findViewById(R.id.dialog);
		dialogbutton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.app_name)
						.setMessage("Deseja mudar o texto ali em cima?")
						.setPositiveButton("Sim", null)
						.setNegativeButton("Não", null)
						.create();

					dialog.setOnShowListener(new DialogInterface.OnShowListener() {
							@Override
							public void onShow(DialogInterface dialoginterface) {
								Button sim = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
								sim.setOnClickListener(new
									View.OnClickListener() {
										@Override
										public void onClick(View view) {
											TextView text = (TextView)findViewById(R.id.texto);
											text.setText("Olha eu sou um novo texto");
											dialog.dismiss();
										}
									});

								Button nao = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
								nao.setOnClickListener(new
									View.OnClickListener() {
										@Override
										public void onClick(View view) {
											dialog.dismiss();
										}
									});
							}
						});
					dialog.show();
				}
			});

		Button notification = (Button)findViewById(R.id.notification);
		notification.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
						.setSmallIcon(R.drawable.ic_launcher)
						.setTicker("Texto da notificação quando chega")
						.setContentTitle(titulo)
						.setContentText(getString(R.string.notifdescription)) // Note que eu usei três jeitos de colocar uma string aqui, você pode usar qual quiser
						.setAutoCancel(true)
						.setColor(0xFF0000FF) // No lollipop, cor de fundo do círculo da notificação
						.setLights(0xFFFF0000, 1500, 2500) // Em celulares com led colorido (não é o caso do Moto G, nele é sempre branco) você pode escolher a cor da luz de notificação quando a tela ta bloqueada
						.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
						.setPriority(NotificationCompat.PRIORITY_MAX) // isso é para no Lollipop aparecer o retângulo quando a notificação chega
						.setStyle(new BigTextStyle()
								  .setBigContentTitle("Título da notificação quando expandida")
								  .bigText("Descrição da notificação quando expandida")
								  .setSummaryText("Sumário da notificação quando expandida"));

					Intent resultIntent = new Intent(MainActivity.this, net.aloogle.apps.teste.MainActivity.class);
					resultIntent.putExtra("fromnotification", true);

					TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);

					stackBuilder.addParentStack(net.aloogle.apps.teste.MainActivity.class);

					stackBuilder.addNextIntent(resultIntent);
					PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
					mBuilder.setContentIntent(resultPendingIntent); // activity/intent (tela) que abre ao tocar na notificação
					NotificationManager mNotificationManager = (NotificationManager)MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

					mNotificationManager.notify(0, mBuilder.build());
				}
			});

		Button open = (Button)findViewById(R.id.openlink);
		open.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String url = "http://google.com.br";
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(url)); // Você também poderia colocar intent.setData(Uri.parse("http://google.com.br"));
					startActivity(intent);
				}
			});

		Button newactivity = (Button)findViewById(R.id.newactivity);
		newactivity.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, CardActivity.class);
					startActivity(intent);
				}
			});

		Button newwebview = (Button)findViewById(R.id.newactivitywebview);
		newwebview.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
					startActivity(intent);
				}
			});
    }

	public String getNumber(String s) {
		String numero = "";
		switch(s) {
			case "1":
				numero = "Um";
				break;
			case "2":
				numero = "Dois";
				break;
			case "tres":
				numero = "Três";
				break;
			case "4":
				numero = "Quatro";
				break;
			case "cinco5":
				numero = "Cinco";
				break;
		}
		return numero;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent);
				return true;
			case R.id.menu_about:
				final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.app_name)
					.setMessage(Html.fromHtml("Aplicativo desenvolvido por <a href=\"http://github.com/alefesouza\">Alefe Souza</a>."))
					.setPositiveButton("Ok", null)
					.create();
				dialog.show();
				// Fazer os links no diálogo funcionarem
				((TextView)dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
				return true;
			default:
				return
					super.onOptionsItemSelected(item);
		}
	}

	public void onResume() {
		numero.setText(getNumber(preferences.getString("prefNumeros", "1")));
		yesorno.setText(String.valueOf(preferences.getBoolean("prefYesorno", true)));
		super.onResume();
	}
}
