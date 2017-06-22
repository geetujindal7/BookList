package com.example.hi.books;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hi on 29-04-2017.
 */
public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();
    private Utils()
    {
    }
    public static List<Book> fetchBooksInfo(String requestURL)
    {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG,"Problem making request",e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }
    private static URL createUrl(String requestURL)
        {
            URL url = null;
            try {
                url = new URL(requestURL);
            }
            catch (MalformedURLException e) {
            }
            return url;
        }
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = null;
        if (url==null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
           urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG,"ERROR RESPONCE CODE" + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Problem getting data",e);
        }
        finally {
            if(urlConnection!= null)
            {
                urlConnection.disconnect();
            }
            if (inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder value = new StringBuilder();
        if (inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null)
            {
                value.append(line);
                line = reader.readLine();
            }
        }
        return value.toString();
    }
    private static List<Book> extractFeatureFromJson(String booksJSON)
    {
        if(TextUtils.isEmpty(booksJSON)) {
            return null;
        }
    List<Book> books = new ArrayList<>();
    try {
        JSONObject baseJsonResponse = new JSONObject(booksJSON);
        JSONArray booksArray = baseJsonResponse.getJSONArray("items");
        for (int i = 0; i < booksArray.length(); i++) {
            JSONObject getBook = booksArray.optJSONObject(i);
            JSONObject properties = getBook.optJSONObject("volumeInfo");
            String author = properties.optString("authors");
            String publisher = properties.optString("publisher");
            String title = properties.optString("title");
            books.add(new Book(author, publisher, title));
        }
    }
        catch (JSONException e)
        {
         Log.e("utils","Problem parsing the result" , e);
        }
        return books;
    }
}