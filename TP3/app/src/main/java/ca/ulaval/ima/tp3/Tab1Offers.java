package ca.ulaval.ima.tp3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class Tab1Offers extends Fragment {
    String brands;
    ArrayList<HashMap<String, String>> brandList;

    static class ViewHolder {
        TextView brandName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_offers, container, false);
        brandList = new ArrayList<>();


        try {
            getBrands();


        } catch (Exception e) {
            System.out.println(e);
        }


        return rootView;
    }


    private void initList() {
        try {
            JSONObject jsonResponse = new JSONObject(brands);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("content");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String id = jsonChildNode.optString("id");
                String name = jsonChildNode.optString("name");
                HashMap<String, String> brand = new HashMap<>();
                brand.put("id", id);
                brand.put("name", name);
                brandList.add(brand);
            }
        } catch (JSONException e) {
            System.out.println("Error" + e.toString());
        }
    }


    private final OkHttpClient client = new OkHttpClient();

    public void getBrands() throws Exception {
        Request request = new Request.Builder()
                .url("http://159.203.62.253/api/v1/brand/")
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

                brands = response.body().string();
                initList();
                final ListView listView = (ListView) getView().findViewById(R.id.brandsListView);
                ViewHolder holder = new ViewHolder();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), brandList, R.layout.brand_list, new String[]{"name"}, new int[]{R.id.brand});
                        listView.setAdapter(simpleAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                HashMap itemValue = (HashMap) listView.getItemAtPosition(position);
                                String brand_id = itemValue.get("id").toString();
                                //Listview clicked item value
                                Intent myIntent = new Intent(view.getContext(), modelsActivity.class);
                                myIntent.putExtra("brand_id", brand_id);
                                startActivityForResult(myIntent, 0);
                            }

                        });

                    }
                });

            }
        });
    }
}