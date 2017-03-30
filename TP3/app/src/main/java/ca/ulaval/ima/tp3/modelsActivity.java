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

public class modelsActivity extends AppCompatActivity {
    String models;
    String brand_id;
    ArrayList<HashMap<String, String>> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        Intent intent = getIntent();
        brand_id = intent.getExtras().getString("brand_id");
        modelList = new ArrayList<>();
        try {
            getModels();


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void initList() {
        try {
            JSONObject jsonResponse = new JSONObject(models);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("content");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonModel = jsonMainNode.getJSONObject(i);
                JSONObject jsonBrand = jsonModel.getJSONObject("brand");
                System.out.println(jsonBrand.get("id").toString());
                System.out.println(brand_id);
                if (jsonBrand.get("id").toString().equals(brand_id)) {
                    String id = jsonModel.optString("id");
                    String name = jsonModel.optString("name");
                    HashMap<String, String> model = new HashMap<>();
                    model.put("id", id);
                    model.put("name", name);
                    modelList.add(model);
                }
            }


        } catch (JSONException e) {
            System.out.println("Error" + e.toString());
        }
    }


    private final OkHttpClient client = new OkHttpClient();

    public void getModels() throws Exception {
        Request request = new Request.Builder()
                .url("http://159.203.62.253/api/v1/model/")
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

                models = response.body().string();
                System.out.println(models);
                initList();
                final ListView listView = (ListView) findViewById(R.id.modelsListView);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(modelsActivity.this, modelList, R.layout.model_list, new String[]{"name"}, new int[]{R.id.model});
                        listView.setAdapter(simpleAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                HashMap itemValue = (HashMap) listView.getItemAtPosition(position);
                                String model_id = itemValue.get("id").toString();
                                //Listview clicked item value
                                Intent myIntent = new Intent(view.getContext(), OffersActivity.class);
                                myIntent.putExtra("model_id", model_id);
                                startActivityForResult(myIntent, 0);
                            }

                        });
                    }
                });

            }
        });
    }
}


