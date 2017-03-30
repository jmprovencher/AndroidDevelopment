package ca.ulaval.ima.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfferActivity extends AppCompatActivity {

    String offer;
    String offer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Intent intent = getIntent();
        offer_id = intent.getExtras().getString("offer_id");
        try {
            getOffer(offer_id);


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private final OkHttpClient client = new OkHttpClient();

    public void getOffer(String offer_id) throws Exception {
        Request request = new Request.Builder()
                .url("http://159.203.62.253/api/v1/offer/" + offer_id)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                offer = response.body().string();
                System.out.println(offer);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonResponse = new JSONObject(offer);
                            JSONObject jsonOffer = jsonResponse.optJSONObject("content");
                            JSONObject jsonModel = jsonOffer.getJSONObject("model");
                            JSONObject jsonBrand = jsonModel.getJSONObject("brand");
                            String description = jsonOffer.optString("description");
                            String year = jsonOffer.optString("year");
                            String price = jsonOffer.optString("price");
                            String transmission = jsonOffer.optString("transmission");
                            TextView brand = (TextView) findViewById(R.id.brand);
                            TextView priceTV = (TextView) findViewById(R.id.price);
                            priceTV.setText(price+" $");
                            brand.setText(jsonBrand.optString("name")+" "+jsonModel.optString("name")+" "+year);
                            TextView descriptionText = (TextView) findViewById(R.id.description);
                            descriptionText.setText("Description de l'offre: "+description);

                            TextView transmissionText = (TextView) findViewById(R.id.transmission);
                            transmissionText.setText("Transmission: "+transmission);

                            Picasso
                                    .with(OfferActivity.this)
                                    .load(jsonOffer.optString("image"))
                                    .into((ImageView) findViewById(R.id.image));
                        }
                        catch (JSONException e) {
                            System.out.println("Error" + e.toString());
                        }

                    }

                });
            }
        });

    }


}


