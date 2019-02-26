package course.examples.networking.url;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class NetworkingURLActivity extends Activity {
	private TextView mTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		mTextView = (TextView) findViewById(R.id.textView1);

		final Button loadButton = (Button) findViewById(R.id.button1);
		final String USER_NAME = "aporter";
		final String URL = "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
				+ USER_NAME;
		loadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new GetResponseTask().execute(URL);
			}
		});
	}
	public class GetResponseTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... strings) {
			//this part only return raw response from url!

			String raw = GetHttpResponse(strings[0]);
			return  raw;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(getApplicationContext(),"No Connection!",Toast.LENGTH_SHORT).show();
			} else {
				mTextView.setText(result);
			}

		}

		// convert inputstream to String
		private String convertInputStreamToString(InputStream inputStream) throws IOException {
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
			String line = "";
			String result = "";
			while((line = bufferedReader.readLine()) != null)
				result += line;

			inputStream.close();
			return result;

		}

		protected String GetHttpResponse(String urlText){
			String raw = null;

			try {
				URL url = new URL(urlText);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				InputStream inputStream = connection.getInputStream();
				raw = convertInputStreamToString(inputStream);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return raw;
		}
	}
}