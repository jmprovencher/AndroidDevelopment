package ca.ulaval.ima.tp3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.password;

public class Tab2Sell extends Fragment {
    String brands;
    ArrayList<HashMap<String, String>> brandList;
    ArrayList<String> brandNameList;
    ArrayList<String> modelNameList;
    String models;
    String brand_id;
    String model_id;
    ArrayList<HashMap<String, String>> modelList;
    EditText editYear;
    EditText editPrice;
    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_sell, container, false);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.transmission_spinner);
        final Button button = (Button) rootView.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postOffer();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.transmission_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        try {
            getBrands();


        } catch (Exception e) {
            System.out.println(e);
        }


        return rootView;
    }


    private void postOffer() {
        editYear = (EditText) getView().findViewById(R.id.editYear);
        editPrice = (EditText) getView().findViewById(R.id.editPrice);
        Spinner transmissionSpinner = (Spinner) getView().findViewById(R.id.transmission_spinner);
        String transmissionString = transmissionSpinner.getSelectedItem().toString();
        if (transmissionString.equals("Automatique")) {
            transmissionString = "AT";
        } else if (transmissionString.equals("Manuelle")) {
            transmissionString = "MA";
        } else {
            transmissionString = "RB";
        }
        String year = editYear.getText().toString();
        String price = editPrice.getText().toString();
        boolean yearOK;
        boolean priceOK;
        if (year.equalsIgnoreCase("") || year.length() < 4) {
            editYear.setError("please enter year");//it gives user to info message //use any one //
            yearOK = false;
        } else {
            yearOK = true;
        }
        if (price.equalsIgnoreCase("")) {
            editPrice.setError("please enter price");//it gives user to info message //use any one //
            priceOK = false;
        } else {
            priceOK = true;
        }

        if (yearOK && priceOK) {
            int intYear = Integer.parseInt(year);
            int intPrice = Integer.parseInt(price);
            String seller = "JMPRO18";
            boolean offer = true;
            JSONObject offerToPost = new JSONObject();
            try {
                offerToPost.put("year", intYear);
                offerToPost.put("offer", offer);
                offerToPost.put("transmission", transmissionString);
                offerToPost.put("seller", seller);
                offerToPost.put("price", intPrice);
                offerToPost.put("model", model_id);

            } catch (JSONException e) {
                System.out.println("Error" + e.toString());
            }

            try {
                postOfferToApi("http://159.203.62.253/api/v1/offer/", offerToPost.toString());
            } catch (IOException e) {
                System.out.println("Error" + e.toString());
            }
        }


    }


    void postOfferToApi(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException(
                        "Unexpected code " + response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Offre envoyée", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }


    private void initList() {
        try {
            JSONObject jsonResponse = new JSONObject(brands);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("content");
            brandNameList = new ArrayList<String>();
            brandList = new ArrayList<>();


            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String id = jsonChildNode.optString("id");
                String name = jsonChildNode.optString("name");
                HashMap<String, String> brand = new HashMap<>();
                brand.put("id", id);
                brand.put("name", name);
                brandNameList.add(name);
                brandList.add(brand);
            }
        } catch (JSONException e) {
            System.out.println("Error" + e.toString());
        }
    }


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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Spinner mySpinner = (Spinner) getView().findViewById(R.id.brand_spinner);
                        mySpinner
                                .setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        brandNameList));
                        mySpinner
                                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0,
                                                               View arg1, int position, long arg3) {
                                        // TODO Auto-generated method stub
                                        // Locate the textviews in activity_main.xml
                                        Spinner modelSpinner = (Spinner) getView().findViewById(R.id.model_spinner);
                                        HashMap itemValue = brandList.get(position);
                                        brand_id = itemValue.get("id").toString();
                                        try {
                                            getModels();


                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });


                    }
                });

            }

        });
    }

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
                initModelList();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Spinner modelSpinner = (Spinner) getView().findViewById(R.id.model_spinner);
                        modelSpinner
                                .setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        modelNameList));
                        modelSpinner
                                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0,
                                                               View arg1, int position, long arg3) {
                                        // TODO Auto-generated method stub
                                        // Locate the textviews in activity_main.xml
                                        HashMap itemValue = modelList.get(position);
                                        model_id = itemValue.get("id").toString();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });

                    }
                });


            }
        });
    }

    private void initModelList() {
        try {
            JSONObject jsonResponse = new JSONObject(models);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("content");
            modelNameList = new ArrayList<String>();
            modelList = new ArrayList<>();


            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonModel = jsonMainNode.getJSONObject(i);
                JSONObject jsonBrand = jsonModel.getJSONObject("brand");
                if (jsonBrand.get("id").toString().equals(brand_id)) {
                    String id = jsonModel.optString("id");
                    String name = jsonModel.optString("name");
                    HashMap<String, String> model = new HashMap<>();
                    model.put("id", id);
                    model.put("name", name);
                    modelList.add(model);
                    modelNameList.add(name);
                }
            }


        } catch (JSONException e) {
            System.out.println("Error" + e.toString());
        }
    }
}



