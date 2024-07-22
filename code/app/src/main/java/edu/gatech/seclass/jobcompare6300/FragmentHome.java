package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.utility.JobAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FragmentHome extends Fragment {

    private RecyclerView mVRecycler;
    private JobAdapter mAdapter;
    private List<Job> mList;

    public FragmentHome() {
        // Required empty public constructor
    }

    public void setListMode(JobAdapter.Mode mode) {
        mAdapter.notifyDataSetChanged();
        mAdapter.setMode(mode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVRecycler = (RecyclerView)  view.findViewById(R.id.recyclerView);
        mVRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        App app  = App.getInstance(null);
        mList = app.getJobManager().getAllJobs();
        mAdapter =  new JobAdapter(mList);
        mVRecycler.setAdapter(mAdapter);
    }

    public List<Job> getSelectedJobs() {
        return mAdapter.getSelectedJobs();
    }
}