package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.mock.MockJobManager;
import edu.gatech.seclass.jobcompare6300.utility.IJobManager;
import edu.gatech.seclass.jobcompare6300.utility.JobManager;

public class App {

    static class NavigationMenu {
        static final String HOME = "Home";
        static final String NEWJOB = "NewJob";
        static final String SETTING = "SETTING";
    }

    public static App app;
    private final IJobManager jobManager;
    private final Context context;

    // Singleton pattern
    // constructor is hidden
    private App(IJobManager jobManager, Context context) {
        this.jobManager = jobManager;
        this.context = context;
    }

    static public App getInstance(Context context) {
        if (app == null) {
            app = new App(new JobManager(context), context);
        }
        return app;
    }

    public IJobManager getJobManager() {
        return jobManager;
    }

    public Context getContext() {
        return context;
    }

    public void setAppTitle(String appTitle) {
        ((MainActivity)context).setAppTitle(appTitle);
    }

    public void setAppTitle(int stringResourceId) {
        ((MainActivity)context).setAppTitle(stringResourceId);
    }

    public void navigateTo(Fragment fragment) {
        MainActivity activity = (MainActivity)context;

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void navigateTo(String screenName) {
        switch (screenName) {
            case NavigationMenu.HOME:
                navigateTo(((MainActivity)context).getFragmentHome());
                break;
            case NavigationMenu.NEWJOB:
                navigateTo(((MainActivity)context).getFragmentNewJob());
                break;
            case NavigationMenu.SETTING:
                navigateTo(((MainActivity)context).getFragmentSetting());
                break;
        }
    }
}
