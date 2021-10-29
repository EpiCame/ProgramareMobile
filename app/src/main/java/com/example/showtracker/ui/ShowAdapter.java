package com.example.showtracker.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.showtracker.R;
import com.example.showtracker.domain.Show;
import com.example.showtracker.persistence.ShowRepo;

import java.util.List;

public class ShowAdapter  extends RecyclerView.Adapter<ShowAdapter.ShowHolder> {

    private List<Show> showList;

    public ShowAdapter(List<Show> shows) {
        showList = shows;
    }


    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_show_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {
        holder.bindItem(showList.get(position));
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class ShowHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private Show show;


        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent updateShowIntent = new Intent(v.getContext(), UpdateShowActivity.class);
            updateShowIntent.putExtra(String.valueOf(R.string.idExtra), show.getId());
            v.getContext().startActivity(updateShowIntent);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure you want to delete " + show.getTitle() + "?")
                    .setPositiveButton(R.string.dialogYes, (dialog, id) -> {
                        ShowRepo.deleteShowById(show.getId());
                        showList = ShowRepo.shows;
                    })
                    .setNegativeButton(R.string.dialogNo, (dialog, id) -> {
                    });
            builder.create().show();
            return true;
        }

        public void bindItem(Show show) {
            this.show = show;

            LinearLayout fieldsLayout = itemView.findViewById(R.id.showContainer);

            TextView titleView = fieldsLayout.findViewById(R.id.title);

            titleView.setText(show.getTitle());
            titleView.setTextColor(Color.BLACK);
            titleView.setTypeface(null, Typeface.BOLD);


        }
    }
}
