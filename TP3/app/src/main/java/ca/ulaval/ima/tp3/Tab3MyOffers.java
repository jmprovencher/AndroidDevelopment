package ca.ulaval.ima.tp3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import okhttp3.RequestBody;
import okhttp3.Response;

import static ca.ulaval.ima.tp3.Tab2Sell.JSON;

public class Tab3MyOffers extends Fragment {
    String offers;
    ArrayList<HashMap<String, String>> offerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_my_offers, container, false);
        offerList = new ArrayList<>();
        try {
            getOffers();


        } catch (Exception e) {
            System.out.println(e);
        }
        return rootView;
    }

    private void initList() {
        try {
            JSONObject jsonResponse = new JSONObject(offers);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("content");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonOffer = jsonMainNode.getJSONObject(i);
                JSONObject jsonModel = jsonOffer.getJSONObject("model");
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


        } catch (JSONException e) {
            System.out.println("Error" + e.toString());
        }
    }

    private final OkHttpClient client = new OkHttpClient();

    public void getOffers() throws Exception {
        RequestBody body = RequestBody.create(JSON, "{\n" +
                "  \"seller\": \"JMPRO18\",\n" +
                "  \"offer\": true\n" +
                "}");
        Request request = new Request.Builder()
                .url("http://159.203.62.253/api/v1/offer/search/"
                )
                .post(body)
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
                initList();
                final ListView listView = (ListView) getView().findViewById(R.id.myOffersListView);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageListAdapter adapter = new
                                ImageListAdapter(getContext(), offerList);

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
