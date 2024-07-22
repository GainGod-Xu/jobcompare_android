package edu.gatech.seclass.jobcompare6300.utility;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import android.content.Context;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import edu.gatech.seclass.jobcompare6300.App;
import edu.gatech.seclass.jobcompare6300.FragmentNewJob;
import edu.gatech.seclass.jobcompare6300.MainActivity;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.model.Job;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    static public enum Mode {
        COMPARE,
        LIST
    }

    private List<Job> jobList;
    private List<Job> selectedJobs = new ArrayList<Job>();
    Context context;
    Mode mode;

    public JobAdapter(List<Job> list) {
        this.mode = Mode.LIST;
        this.jobList = list;
    }

    public List<Job> getSelectedJobs() {
        return selectedJobs;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if(this.mode == Mode.COMPARE) {
            selectedJobs.clear();
        } else {

        }
        notifyItemRangeChanged(0, getItemCount());
    }

    //Inflate the layout from XML and returning the holder
    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // Inflate the job_list layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Return a new holder instance
        View view = inflater.inflate(R.layout.job_item, parent, false);
        JobViewHolder viewHolder = new JobViewHolder(view);
        return viewHolder;
    }

    // Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        Job job = jobList.get(holder.getAdapterPosition());
        NumberFormat format = NumberFormat.getInstance();
        format.setCurrency(Currency.getInstance("USD"));

        holder.jobTitle.setText(job.getTitle());
        holder.companyName.setText(job.getCompany());

        String location = job.getLocationCity() + ", " + job.getLocationState();
        holder.jobLocation.setText(location);
        holder.salary.setText("$" + format.format(job.getYearlySalary()));
        double score = jobList.get(holder.getAdapterPosition()).getScore(App.getInstance(null).getJobManager().getComparisonSetting());
        holder.score.setText(String.valueOf((int)score));

        if(job.isCurrentJob()) {
            holder.currentJob.setText("Current Job");
        } else {
            holder.currentJob.setText("");
        }

        int backgroundColor = holder.getAdapterPosition() % 2 == 0 ? Color.rgb(255, 255, 255) : Color.rgb(240, 240, 240);
        holder.background.setBackgroundColor(backgroundColor);

        holder.checkbox.setChecked(false);
        if(mode == Mode.COMPARE ) {
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.imageButton.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox.setVisibility(View.INVISIBLE);
            holder.imageButton.setVisibility(View.VISIBLE);
        }

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkbox.isChecked()) {
                    if(selectedJobs.size() >= 2) {
                        Snackbar snackbar = Snackbar.make(holder.view, "You can't select more than 2 Jobs", BaseTransientBottomBar.LENGTH_SHORT);
                        snackbar.show();
                        holder.checkbox.setChecked(false);
                    } else {
                        selectedJobs.add(jobList.get(holder.getAdapterPosition()));
                    }
                } else {
                    if(selectedJobs.get(0).getId() == jobList.get(holder.getAdapterPosition()).getId()) {
                        selectedJobs.remove(0);
                    } else {
                        selectedJobs.remove(1);
                    }
                }
            }
        });


        //Set up Popup Menu with context together
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.imageButton);
                popup.inflate(R.menu.menu_job);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itemDelete:
                                Job jobToDelete = jobList.get(holder.getAdapterPosition());
                                App.getInstance(null).getJobManager().deleteJob(jobToDelete);
                                notifyItemRemoved(holder.getAdapterPosition());
                                // This is to notify all items to update its background color, so it can have odd / even items different color after a delete
                                notifyItemRangeChanged(0, getItemCount());
                                return true;
                            case R.id.itemEdit:
                                Job selectedJob = jobList.get(holder.getAdapterPosition());
                                FragmentNewJob fragmentEditJob = FragmentNewJob.newInstance(selectedJob);
                                App.getInstance(null).setAppTitle(R.string.edit_job);
                                App.getInstance(null).navigateTo(fragmentEditJob);
                                return true;
                            case R.id.setToCurrentJob:
                                Job job = jobList.get(holder.getAdapterPosition());
                                App.getInstance(null).getJobManager().setCurrentJob(job.getId());
                                notifyItemRangeChanged(0, jobList.size());
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitle;
        public TextView companyName;
        public TextView jobLocation;
        public TextView salary;
        public TextView score;
        public ImageButton imageButton;
        public TextView currentJob;
        public CardView background;
        public CheckBox checkbox;

        View view;

        JobViewHolder(View itemView)
        {
            super(itemView);
            jobTitle = itemView.findViewById (R.id.jobTitle);
            companyName = itemView.findViewById (R.id.companyName);
            jobLocation = itemView.findViewById (R.id.jobLocation);
            salary = itemView.findViewById (R.id.salary);
            score = itemView.findViewById (R.id.score);
            imageButton = itemView.findViewById(R.id.jobMenuButton);
            currentJob = itemView.findViewById(R.id.currentJob);
            background = itemView.findViewById(R.id.jobCardView);
            checkbox = itemView.findViewById(R.id.checkBox);
            view  = itemView;
        }
    }
}