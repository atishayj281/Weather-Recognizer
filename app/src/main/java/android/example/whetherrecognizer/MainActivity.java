package android.example.whetherrecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void recognize(View view){
        Context c = this;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        EditText cty = findViewById(R.id.City);
        String city = String.valueOf(cty.getText());
        String url ="http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=c010de4c8a17bd32b86cbd9fc8698345";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("main");
                            JSONObject wind = response.getJSONObject("wind");
                            String temp;
                            temp = main.getString("temp");
                            String windSpeed = wind.getString("speed");
                            String pressure = main.getString("pressure");
                            String humidity = main.getString("humidity");

                            TextView temperature = findViewById(R.id.temp);
                            TextView speed = findViewById(R.id.wind_speed);
                            TextView hum = findViewById(R.id.humidity);
                            TextView press = findViewById(R.id.pressure);

                            temperature.setText(String.format("Current Temperature is : %s *C", temp));
                            speed.setText(String.format("Wind Speed: %sm/sec", windSpeed));
                            hum.setText(String.format("Humidity: %s%%", humidity));
                            press.setText(String.format("Pressure: %s hPa", pressure));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // textView.setText("That didn't work!");
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

}