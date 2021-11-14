package com.example.showtracker.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.showtracker.R;
import com.example.showtracker.db.DatabaseHandler;
import com.example.showtracker.domain.Show;
import com.google.android.material.snackbar.Snackbar;

public class UpdateShowActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_show);
        DatabaseHandler dbHandler = new DatabaseHandler(this);

        int showId = getIntent().getIntExtra(String.valueOf(R.string.idExtra),-1);
        Show show = dbHandler.getShow(showId);
        Toolbar toolbar = findViewById(R.id.updateToolbar);

        toolbar.setTitle(show.getTitle());
        setSupportActionBar(toolbar);

        TextView titleView = findViewById(R.id.titleUpdate);
        TextView producerView = findViewById(R.id.producerUpdate);
        TextView firstYearView = findViewById(R.id.firstYearUpdate);
        TextView lastYearView = findViewById(R.id.lastYearUpdate);
        TextView seasonsView = findViewById(R.id.seasonsUpdate);
        RatingBar rb = findViewById(R.id.ratingBarUpdate);

        titleView.setText(show.getTitle());
        producerView.setText(show.getProducer());
        firstYearView.setText(String.valueOf(show.getFirstYear()));
        lastYearView.setText(String.valueOf(show.getLastYear()));
        seasonsView.setText(String.valueOf(show.getNumberOfSeasons()));
        rb.setRating((float) show.getRating());

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);

        Button updateButton = findViewById(R.id.updateShowButton);
        updateButton.setOnClickListener(view -> {
            if (titleView.getText().length() > 0 && producerView.getText().length() > 0 && firstYearView.getText().length() > 0
                    && lastYearView.getText().length() > 0 && seasonsView.getText().length() > 0){
                show.setTitle(titleView.getText().toString());
                show.setProducer(producerView.getText().toString());
                show.setFirstYear(Integer.parseInt(firstYearView.getText().toString()));
                show.setLastYear(Integer.parseInt(lastYearView.getText().toString()));
                show.setRating(rb.getRating());
                progressBar.setVisibility(View.VISIBLE);
                dbHandler.updateShow(show);
                //ShowRepo.updateShow(show);
                progressBar.setVisibility(View.INVISIBLE);
                finish();
            }
            else{
                Snackbar.make(view.getRootView(), R.string.fabErrorMessage, Snackbar.LENGTH_LONG)
                        .show();
            }
        });

         };

}
