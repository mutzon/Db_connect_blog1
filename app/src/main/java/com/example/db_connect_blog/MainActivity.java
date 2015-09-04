package com.example.db_connect_blog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button fetch;
	TextView text;
	EditText resultView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		resultView = (EditText) findViewById(R.id.resultView);
		fetch = (Button) findViewById(R.id.fetch);
		fetch.setOnClickListener( new Button.OnClickListener() {
					@Override
					public void onClick(View view) {
					new	Background().execute("http://martinutzon.dk/cloud/index.php");

					}
				}

		);

	}
	public void  setText(String result){
		resultView.setText(result);
		Log.e("log_tag", "message is:  " + result);

	}

public	class Background extends AsyncTask<String, Void, String> {



		@Override
		protected String doInBackground(String... params) {

			String result = "";
			InputStream isr = null;

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(params[0]);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
				resultView.setText("Couldn't connect to database");
			}

			//convert response to string

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				isr.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}


			try {

			} catch (Exception e) {
				Log.e("log_tag", "Couldn't set text, damnit.");
			}
			return result;
		}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.e("log_tag", "result is: " + result);
		setText(result);

	}
}
}






