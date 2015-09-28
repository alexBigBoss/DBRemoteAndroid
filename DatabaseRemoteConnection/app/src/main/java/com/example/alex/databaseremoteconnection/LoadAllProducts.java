package com.example.alex.databaseremoteconnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadAllProducts extends AsyncTask<String, String, String> {

    private Activity activity;

    // Progress Dialog
    private ProgressDialog pDialog;

    // url to get all products list
    private final String url_all_empresas = "http://dbremote.esy.es/connect/Controller.php";

    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_PRODUCTS = "empresas";
    private final String TAG_ID = "id";
    private final String TAG_NOMBRE = "nombre";

    ArrayList<HashMap<String, String>> empresaList;
    ListView lista;

    public LoadAllProducts(Activity activity) {
        this.activity = activity;
        empresaList = new ArrayList<HashMap<String, String>>();
        lista = (ListView) activity.findViewById(R.id.listAllProducts);
    }

    /**
     * @private Before to start the background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Cargando comercios. Por favor espere...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * @private Get all products
     */
    protected String doInBackground(String... args) {

        JSONArray products = null;
        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.makeHttpRequestGet(url_all_empresas);
        JSONObject c;
        String id;
        String name;
        HashMap map;

        try {
            final int SUCCESS = json.getInt(TAG_SUCCESS);
            if (SUCCESS == 1) {
                products = json.getJSONArray(TAG_PRODUCTS);         // Getting Array of Products

                for (int i = 0; i < products.length(); i++) {
                    c = products.getJSONObject(i);

                    id = c.getString(TAG_ID);
                    name = c.getString(TAG_NOMBRE);

                    map = new HashMap();
                    map.put(TAG_ID, id);
                    map.put(TAG_NOMBRE, name);

                    empresaList.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @private Updating UI from Background Thread and parsed JSON data into ListView
     * @param file_url
     */
    @Override
    protected void onPostExecute(String file_url) {
        pDialog.dismiss();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                ListAdapter adapter = new SimpleAdapter(
                        activity,
                        empresaList,
                        R.layout.single_post,
                        new String[]{
                                TAG_ID,
                                TAG_NOMBRE,
                        },
                        new int[]{
                                R.id.single_post_tv_id,
                                R.id.single_post_tv_nombre,
                        });
                lista.setAdapter(adapter);
            }
        });
    }
}