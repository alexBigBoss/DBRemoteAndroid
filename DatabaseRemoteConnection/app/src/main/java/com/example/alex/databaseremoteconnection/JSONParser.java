package com.example.alex.databaseremoteconnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class JSONParser {

    public final String POST = "POST";
    public final String GET = "GET";

    public JSONObject makeHttpRequest(String webUrl, String method, List<String> params) {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;
        String line = "";

        try {
            url = new URL(webUrl);
            connection = (HttpURLConnection) url.openConnection();

            if (method == GET) {
                connection.setRequestMethod(GET);
            } else if (method == POST) {
                DataOutputStream outputStream;
                connection.setRequestMethod(POST);

                // Send post request
                connection.setDoOutput(true);
                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(formatParams(params));
                outputStream.flush();
                outputStream.close();
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject makeHttpRequestGet(String webUrl) {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;
        String line = "";

        try {
            url = new URL(webUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject makeHttpRequestPost(String webUrl, List<String> params) {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;
        DataOutputStream outputStream;
        String line = "";

        try {
            url = new URL(webUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);

            // Send post request
            connection.setDoOutput(true);
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(formatParams(params));
            outputStream.flush();
            outputStream.close();


            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String formatParams(List<String> list) {
        String stringParam = "";
        boolean first = true;

        for (String l : list) {
            stringParam += first ? "" + l : "&" + l;
            stringParam += "=" + l;
            first = false;
        }

        return stringParam;
    }

}