package com.example.showtracker.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.showtracker.R;
import com.example.showtracker.db.DatabaseHandler;
import com.example.showtracker.domain.Show;
import com.example.showtracker.persistence.ShowRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.newShow);
        setSupportActionBar(toolbar);

        TextView titleView = findViewById(R.id.titleAdd);
        TextView producerView = findViewById(R.id.producerAdd);
        TextView firstYearView = findViewById(R.id.firstYearAdd);
        TextView lastYearView = findViewById(R.id.lastYearAdd);
        TextView seasonsView = findViewById(R.id.seasonsAdd);
        RatingBar rb = findViewById(R.id.ratingBarAdd);
        rb.setRating(5);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);

        FloatingActionButton fab = findViewById(R.id.addShowButton);
        fab.setOnClickListener(view -> {

            if (titleView.getText().length() > 0 && producerView.getText().length() > 0 && firstYearView.getText().length() > 0
            && lastYearView.getText().length() > 0 && seasonsView.getText().length() > 0) {
                DatabaseHandler dbHandler = new DatabaseHandler(this);
                Show showToAdd = new Show(String.valueOf(titleView.getText()), String.valueOf(producerView.getText()), Integer.parseInt(String.valueOf(firstYearView.getText())),
                        Integer.parseInt(String.valueOf(lastYearView.getText())),Integer.parseInt(String.valueOf(seasonsView.getText())), rb.getRating());

                progressBar.setVisibility(View.VISIBLE);
                dbHandler.addShow(showToAdd);
                progressBar.setVisibility(View.INVISIBLE);
                finish();

            } else {
                Snackbar.make(view.getRootView(), R.string.fabErrorMessage, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
