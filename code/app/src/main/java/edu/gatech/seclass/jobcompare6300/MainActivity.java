package edu.gatech.seclass.jobcompare6300;

import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import edu.gatech.seclass.jobcompare6300.databinding.ActivityMainBinding;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.utility.JobAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Menu menu;
    FragmentHome fragmentHome;
    FragmentSetting fragmentSetting;
    App app;

    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        app = App.getInstance(this);
        app.getJobManager().refreshJobList();

        // Create all fragments and save for reuse
        fragmentHome = new FragmentHome();
        //fragmentNewjob = new FragmentNewJob();  ** NewJob Fragment is always newly created
        fragmentSetting = new FragmentSetting();

        app.navigateTo(App.NavigationMenu.HOME);
    }

    public Menu getMenu() {
        return menu;
    }

    public Fragment getFragmentHome() {
        return fragmentHome;
    }

    public Fragment getFragmentSetting() {
        return fragmentSetting;
    }

    public Fragment getFragmentNewJob() {
        return new FragmentNewJob();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    setAppTitle(R.string.app_name);
                    app.navigateTo(App.NavigationMenu.HOME);
                    //replaceFragment(getFragmentHome());
                    break;
                case R.id.nav_new_job:
                    setAppTitle(R.string.add_new_job);
                    app.navigateTo(App.NavigationMenu.NEWJOB);
                    //replaceFragment(getFragmentNewJob());
                    break;
                case R.id.nav_setting:
                    setAppTitle(R.string.setting);
                    app.navigateTo(App.NavigationMenu.SETTING);
                    break;
            }
            return true;
        });
    }

    public void setAppTitle(String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setAppTitle(int string_resource_id) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(string_resource_id);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_compare:
                if(item.getTitle() == getResources().getString(R.string.compare)) {
                    item.setTitle(R.string.done);
                    MenuItem cancel = menu.findItem(R.id.menu_cancel);
                    cancel.setVisible(true);
                    fragmentHome.setListMode(JobAdapter.Mode.COMPARE);
                } else {
                    List<Job> selectedJobs = fragmentHome.getSelectedJobs();
                    if(selectedJobs.size() != 2) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.frameLayout) , "You need to select two jobs to compare", BaseTransientBottomBar.LENGTH_SHORT);
                        snackbar.show();
                    } else {
                        // User selected two jobs to compare and click "Done"
                        FragmentCompare compare = new FragmentCompare(selectedJobs.get(0), selectedJobs.get(1));
                        app.navigateTo(compare);

                        item.setTitle(R.string.compare);
                        MenuItem cancel = menu.findItem(R.id.menu_cancel);
                        cancel.setVisible(false);
                        fragmentHome.setListMode(JobAdapter.Mode.LIST);
                    }
                }
                break;
            case R.id.menu_cancel:
                MenuItem compare = menu.findItem(R.id.menu_compare);
                MenuItem cancel = menu.findItem(R.id.menu_cancel);
                compare.setTitle(R.string.compare);
                cancel.setVisible(false);
                fragmentHome.setListMode(JobAdapter.Mode.LIST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }
}
