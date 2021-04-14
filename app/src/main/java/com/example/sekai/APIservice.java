package com.example.sekai;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIservice {
    public static final String SEARCHING = "https://restcountries.eu/rest/v2/all";
    Context context;

    public APIservice(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(List<Country> countries);
    }

    public void getCountries(VolleyResponseListener volleyResponseListener) {
        String url = SEARCHING;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Country> countries = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Country country = new Country();
                        country.setName(jsonObject.getString("name"));
                        country.setAlpha2code(jsonObject.getString("alpha2Code"));
                        country.setCapital(jsonObject.getString("capital"));
                        country.setRegion(jsonObject.getString("region"));
                        country.setCallingCodes(jsonObject.getString("callingCodes"));
                        country.setPopulation(jsonObject.getInt("population"));
                        country.setLnglat(jsonObject.getString("latlng"));
                        country.setBorders(jsonObject.getString("borders"));
                        country.setFlag(jsonObject.getString("flag"));
                        country.setLanguages(getStringArray(jsonObject.getJSONArray("languages"), "name"));
                        countries.add(country);
                        country.setCurrencies(getStringArray(jsonObject.getJSONArray("currencies"), "name"));
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "", e);
                }

                volleyResponseListener.onResponse(countries);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something Wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private String getStringArray(JSONArray jsonArray, String name) {
        try {
            String stringResult = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject languageJSON = (JSONObject) jsonArray.getJSONObject(i);
                if(i == jsonArray.length() - 1){
                    stringResult = stringResult + languageJSON.getString(name);
                } else {
                    stringResult = stringResult + languageJSON.getString(name) + ", ";
                }
            }
            return stringResult;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
