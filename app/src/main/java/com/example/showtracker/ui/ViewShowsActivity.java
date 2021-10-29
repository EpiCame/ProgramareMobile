package com.example.showtracker.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.showtracker.R;
import com.example.showtracker.domain.Show;
import com.example.showtracker.persistence.ShowRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewShowsActivity extends AppCompatActivity {

    private static final int ADD_SHOW_REQUEST_CODE = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShowRepo.createShows();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView showList = findViewById(R.id.showList);

        getData();

        showList.setLayoutManager(linearLayoutManager);
        showList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::onResume);

        FloatingActionButton fab = findViewById(R.id.addShow);
        fab.setOnClickListener(view -> {
            Intent addShowIntent = new Intent(getApplicationContext(), AddShowActivity.class);
            startActivityForResult(addShowIntent,ADD_SHOW_REQUEST_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        RecyclerView movieList = findViewById(R.id.showList);

        List<Show> shows = ShowRepo.shows;

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        ShowAdapter adapter = new ShowAdapter(shows);
        movieList.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        getData();
        swipeRefreshLayout.setRefreshing(false);
    }

}

