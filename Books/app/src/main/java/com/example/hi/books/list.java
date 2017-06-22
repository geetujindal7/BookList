package com.example.hi.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
public class list extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static  String REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=30";
    private BookAdapter adapter;
    private TextView Empty;
    private int load=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        adapter = new BookAdapter(this,new  ArrayList<Book>());
        Button button = (Button) findViewById(R.id.search_button);
        ListView listView1 = (ListView) findViewById(R.id.list);
        Empty = (TextView) findViewById(R.id.nobook);
        listView1.setAdapter(adapter);
        LoaderManager loaderManager = null;
        ConnectivityManager Cmanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netw = Cmanager.getActiveNetworkInfo();
        if(netw!=null&&netw.isConnected())
        {
            Empty.setVisibility(View.GONE);
            loaderManager = getLoaderManager();
            loaderManager.initLoader(load,null,this);
        }
        else
        {
            View Loading = findViewById(R.id.buffer);
            Loading.setVisibility(View.GONE);
            Empty.setText(getText(R.string.nointrnetconn).toString());

        }
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                load++;
                EditText edit = (EditText) findViewById(R.id.search);
                String search_book = edit.getText().toString();
                REQUEST_URL="https://www.googleapis.com/books/v1/volumes?q="+search_book+"&maxResults=30";
                LoaderManager loaderManager1 = null;
                ConnectivityManager ConnManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnManager.getActiveNetworkInfo();
                if(networkInfo!=null && networkInfo.isConnected())
                {
                    Empty.setVisibility(View.GONE);
                    loaderManager1 = getLoaderManager();
                    loaderManager1.initLoader(load,null,list.this);
                }
                else
                {
                    View Loading = findViewById(R.id.buffer);
                    Loading.setVisibility(View.GONE);
                    Empty.setText(getText(R.string.nointrnetconn).toString());

                }
            }
        });
    }
    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new Loading(this,REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
    Empty.setText(getText(R.string.empty).toString());
        adapter.clear();
        ProgressBar progressBar =(ProgressBar) findViewById(R.id.buffer);
        progressBar.setVisibility(View.GONE);
        if(data !=null && !data.isEmpty())
        {
            adapter.addAll(data);
        }
        else
        {
            Empty.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
    adapter.clear();
    }
}