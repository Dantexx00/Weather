package compumovil.udea.edu.co.weather;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView description;
    TextView temp;
    Button button;
    String findCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city=(EditText)findViewById(R.id.cityText);
        description=(TextView)findViewById(R.id.condDescr);
        temp=(TextView)findViewById(R.id.temp);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                WeatherTask task=new WeatherTask();
                findCity=city.getText().toString();
                task.execute(findCity);
            }
        });
    }
    private class WeatherTask extends AsyncTask<String,Void,Void> {
        private static final String TAG="WeatherTask";
        private String Error=null;
        private ProgressDialog Dialog=new ProgressDialog(MainActivity.this);
        String data="";

        @Override
        protected void onPreExecute() {
            Dialog.setMessage("Por favor espere");
            Dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try{
                data=((new WeatherHTTPClient()).getWeatherData(params[0]));
            }catch (Exception e){
                Error=e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Dialog.dismiss();
            if(Error!=null){

            }else{
                String OutputData="";
                Double celsius=0.0;
                JSONObject jsonResponse;
                try{
                    jsonResponse=new JSONObject(data);
                    OutputData=jsonResponse.optString("name");
                    city.setText(OutputData);
                    OutputData=jsonResponse.getJSONObject("main").optString("temp");
                    celsius=Double.parseDouble(OutputData)-273.15;
                    temp.setText(celsius+" °C");
                    OutputData=jsonResponse.getJSONArray("weather").getJSONObject(0).optString("main")+", "
                            +jsonResponse.getJSONArray("weather").getJSONObject(0).optString("description");
                    description.setText(OutputData);
                }catch (JSONException E){
                    E.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
