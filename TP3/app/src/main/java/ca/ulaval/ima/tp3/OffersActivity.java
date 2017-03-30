package ca.ulaval.ima.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class OffersActivity extends AppCompatActivity {

    String offers;
    String model_id;
    ArrayList<HashMap<String, String>> offerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        Intent intent = getIntent();
        model_id = intent.getExtras().getString("model_id");
        offerList = new ArrayList<>();
        try {
            getOffers();


        } catch (Exception e) {
            System.out.println(e);
        }

    }


    private void initList() {
        try {
            JSONObject jsonResponse = new JSONObject(offers);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("content");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonOffer = jsonMainNode.getJSONObject(i);
                JSONObject jsonModel = jsonOffer.getJSONObject("model");
                System.out.println(jsonModel.get("id").toString());
                System.out.println(model_id);
                if (jsonModel.get("id").toString().equals(model_id)) {
                    String id = jsonOffer.optString("id");
                    String price = jsonOffer.optString("price");
                    String year = jsonOffer.optString("year");
                    String photo_url = jsonOffer.optString("image");
                    String brand = jsonModel.getJSONObject("brand").optString("name");
                    String model = jsonModel.optString("name");
                    HashMap<String, String> offer = new HashMap<>();
                    offer.put("id", id);
                    offer.put("price", price);
                    offer.put("brand", brand);
                    offer.put("model", model);
                    offer.put("photo_url", photo_url);
                    offer.put("year", year);
                    offerList.add(offer);
                }
            }


        } catch (JSONException e) {
            System.out.println("Error" + e.toString());
        }
    }


    private final OkHttpClient client = new OkHttpClient();

    public void getOffers() throws Exception {
        Request request = new Request.Builder()
                .url("http://159.203.62.253/api/v1/offer/")
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

                offers = response.body().string();
                System.out.println(offers);
                initList();
                final ListView listView = (ListView) findViewById(R.id.modelsListView);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageListAdapter adapter = new
                                ImageListAdapter(OffersActivity.this, offerList);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                HashMap itemValue = (HashMap) listView.getItemAtPosition(position);
                                String offer_id = itemValue.get("id").toString();
                                //Listview clicked item value
                                Intent myIntent = new Intent(view.getContext(), OfferActivity.class);
                                myIntent.putExtra("offer_id", offer_id);
                                startActivityForResult(myIntent, 0);
                            }

                        });
                    }
                });

            }
        });
    }
}

