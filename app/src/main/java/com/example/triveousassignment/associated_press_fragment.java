package com.example.triveousassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class associated_press_fragment extends Fragment implements Article_adapter.ArticleClickInterface, SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    final static String BASE_URL = "https://newsapi.org/v2/top-headlines";
    final static String Q = "q";
    final static String SOURCES = "sources";
    final static String LANGUAGE = "language";
    final static String APIKEY="apiKey";
    private ArrayList<Article> mnews;
    private Article_adapter article_adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    public associated_press_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_fragment, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        //crate an universal adapter that represent each view inside the recycler view

        network_operation();
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onRefresh() {
        network_operation();
    }
    public void network_operation()
    {
        Uri myuri = this.builduri();
        String murl = myuri.toString();
        associated_press_fragment.HttpGetRequest getRequest = new associated_press_fragment.HttpGetRequest();
        getRequest.execute(murl);
        swipeRefreshLayout.setRefreshing(false);
    }
    public Uri builduri()
    {
        Uri builturi = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCES,"cnn")
                .appendQueryParameter(APIKEY,"eac013040a57494aa0402871994154e9").build();
        Log.d("[BusinessURI]:",""+builturi);
        return builturi;
    }

    @Override
    public void onArticleItemClick(Article article) {
        Log.d("msg","itemclicke");
        Intent intent = new Intent(getContext(),WebViewActivity.class);
        intent.putExtra("Articledata",article);
        startActivity(intent);
    }

    class HttpGetRequest extends AsyncTask<String, Void, List<Article>>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Article> doInBackground(String... strings)
        {
            mnews = new ArrayList<>();
            String stringUrl = strings[0];
            String result = null;
            String inputLine="";
            try
            {
                URL myUrl = new URL(stringUrl);
                HttpURLConnection connection =(HttpURLConnection)myUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                Log.i("background:","Task1 Completed");
                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
                JSONObject root =new JSONObject(result);
                JSONArray articles=root.getJSONArray("articles");;
                for(int i=0;i<articles.length();i++)
                {
                    JSONObject object=articles.getJSONObject(i);
                    JSONObject source = object.getJSONObject("source");
                    String publisher =source.getString("name");
                    String description=object.getString("description");
                    String url=object.getString("url");
                    String title  = object.getString("title");
                    String urltoimage = object.getString("urlToImage");
                    Article article=new Article(publisher,description,url,urltoimage,title);
                    mnews.add(article);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return mnews;
        }

        @Override
        protected void onPostExecute(List<Article> result)
        {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            article_adapter = new Article_adapter(getContext(),mnews, associated_press_fragment.this);
            recyclerView.setAdapter(article_adapter);
        }
    }

}