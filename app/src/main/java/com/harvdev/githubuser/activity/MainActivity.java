package com.harvdev.githubuser.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.harvdev.githubuser.R;
import com.harvdev.githubuser.adapter.UsersAdapter;
import com.harvdev.githubuser.api.ApiRequest;
import com.harvdev.githubuser.api.Retroserver;
import com.harvdev.githubuser.model.Items;
import com.harvdev.githubuser.model.ResponsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    boolean isLoading = false;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<Items> mItems = new ArrayList<>();
    private TextView hasilTV;
    private ProgressBar progressBar;
    private int page = 1;
    private String query1 = "";
    private FloatingSearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasilTV = findViewById(R.id.hasilTV);
        hasilTV.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressbar);

        mRecycler = findViewById(R.id.rvUsers);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        mAdapter = new UsersAdapter(MainActivity.this, mItems);
        mRecycler.setAdapter(mAdapter);

        mSearchView = findViewById(R.id.floating_search_view);


        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {

                mItems.clear();
                page = 1;
                tampilkanKelas(currentQuery, page);
                query1 = currentQuery;

            }
        });


        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mManager.getChildCount();
                totalItems = mManager.getItemCount();
                scrollOutItems = mManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    tampilkanKelas(query1, page);
                }
            }
        });

    }

    private void tampilkanKelas(String query, int page1) {
        page++;
        progressBar.setVisibility(View.VISIBLE);
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsModel> getdata = api.getUsers(query, page1);
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {


                ResponsModel list = response.body();
                if (list != null) {

                    mItems.addAll(list.getItems());

                } else {
                    Toast.makeText(MainActivity.this, "Upps something wrong, try again in 1 minute", Toast.LENGTH_SHORT).show();
                }


                if (mAdapter.getItemCount() == 0) {
                    hasilTV.setVisibility(View.VISIBLE);
                } else {
                    hasilTV.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("RETRO", t.getMessage());
            }
        });
    }
}