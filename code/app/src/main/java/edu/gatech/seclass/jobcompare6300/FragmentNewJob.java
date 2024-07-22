package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.WindowManager;
import edu.gatech.seclass.jobcompare6300.model.Job;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class FragmentNewJob extends Fragment {

    static int dummy_id = 0;

    TextInputEditText mTitle;
    TextInputEditText mCompany;
    TextInputEditText mLocationCity;
    TextInputEditText mLocationState;
    TextInputEditText mYearlySalary;
    TextInputEditText mYearlyBonus;
    TextInputEditText mRetirementBenefit;
    TextInputEditText mCostOfLiving;
    TextInputEditText mRelocationStipened;
    TextInputEditText mRestictedStockUnitAward;
    int id = dummy_id;

    public FragmentNewJob() {
        // Required empty public constructor
    }

    private void populateFields() {
        if(getArguments() != null) {
            try {
                mTitle.setText(getArguments().getString("title"));
                mCompany.setText(getArguments().getString("company"));
                mLocationCity.setText(getArguments().getString("locationCity"));
                mLocationState.setText(getArguments().getString("locationState"));
                mYearlySalary.setText(getArguments().getString("yearlySalary"));
                mYearlyBonus.setText(getArguments().getString("yearlyBonus"));
                mRetirementBenefit.setText(getArguments().getString("retirementBenefit"));
                mCostOfLiving.setText(getArguments().getString("costOfLiving"));
                mRelocationStipened.setText(getArguments().getString("relocationStipened"));
                mRestictedStockUnitAward.setText(getArguments().getString("restrictedStockUnitAward"));
                this.id = getArguments().getInt("id");
            } catch (Exception e) {
                System.out.println("OMSCS6300:" + e.getMessage());
            }
        }
    }

    public static FragmentNewJob newInstance(Job job) {
        FragmentNewJob fragment = new FragmentNewJob();
        Bundle bundle = new Bundle();
        bundle.putString("title", job.getTitle());
        bundle.putString("company", job.getCompany());
        bundle.putString("locationCity", job.getLocationCity());
        bundle.putString("locationState", job.getLocationState());
        bundle.putString("yearlySalary", job.getYearlySalary().toString());
        bundle.putString("yearlyBonus", job.getYearlyBonus().toString());
        bundle.putString("retirementBenefit", job.getRetirementBenefit().toString());
        bundle.putString("costOfLiving", job.getCostOfLiving().toString());
        bundle.putString("relocationStipened", job.getRelocationStipend().toString());
        bundle.putString("restrictedStockUnitAward", job.getRestrictedStockUnitAward().toString());
        bundle.putInt("id", job.getId());

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_new_job, container, false);
    }

    private void putFakeData() {
        mTitle.setText("Project Manager");
        mCompany.setText("Microsoft");
        mLocationCity.setText("New York");
        mLocationState.setText("NY");
        mYearlySalary.setText("15000");
        mYearlyBonus.setText("9500");
        mRetirementBenefit.setText("3");
        mCostOfLiving.setText("100");
        mRelocationStipened.setText("40");
        mRestictedStockUnitAward.setText("100");
    }

    private Job createJob() {
        String title = mTitle.getText().toString();
        String company = mCompany.getText().toString();
        String locationCity = mLocationCity.getText().toString();
        String locationState = mLocationState.getText().toString();
        Double yearlySalary = Double.valueOf(mYearlySalary.getText().toString());
        Double yearlyBonus = Double.valueOf(mYearlyBonus.getText().toString());
        int retirementBenefit = Integer.valueOf(mRetirementBenefit.getText().toString());
        int costOfLiving = Integer.valueOf(mCostOfLiving.getText().toString());
        Double relocationStipend  = Double.valueOf(mRelocationStipened.getText().toString());
        Double restrictedStockUnitAward = Double.valueOf(mRestictedStockUnitAward.getText().toString());

        Job newJob = new Job(title, company, locationCity, locationState, costOfLiving, yearlySalary, yearlyBonus, retirementBenefit, relocationStipend, restrictedStockUnitAward, false, this.id);
        return newJob;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = view.findViewById(R.id.edit_jobTitle);
        mCompany = view.findViewById(R.id.edit_jobCompany);
        mLocationCity = view.findViewById(R.id.edit_jobCity);
        mLocationState = view.findViewById(R.id.edit_jobState);
        mYearlySalary = view.findViewById(R.id.edit_jobYS);
        mYearlyBonus = view.findViewById(R.id.edit_jobYB);
        mRetirementBenefit = view.findViewById(R.id.edit_jobRB);
        mCostOfLiving = view.findViewById(R.id.edit_jobCOL);
        mRelocationStipened = view.findViewById(R.id.edit_jobRS);
        mRestictedStockUnitAward = view.findViewById(R.id.edit_jobRSUA);

        Button buttonSave = view.findViewById(R.id.edit_jobSave);
        Button buttonCancel = view.findViewById(R.id.edit_jobCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getInstance(null).navigateTo(App.NavigationMenu.HOME);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isDataValid()) {
                    Job job = createJob();
                    App app = App.getInstance(null);

                    if(id != dummy_id) {
                        app.getJobManager().updateJob(job);
                        Toast.makeText(getActivity(),"Job is updated.",Toast.LENGTH_LONG).show();
                    } else {
                        app.getJobManager().addJob(job);
                        Toast.makeText(getActivity(),"New Job is saved.",Toast.LENGTH_LONG).show();
                    }
                    app.getJobManager().refreshJobList();
                }
                App.getInstance(null).navigateTo(App.NavigationMenu.HOME);
            }
        });

        if(id == dummy_id) {
            putFakeData();
        }

        populateFields();
    }

    private boolean isDataValid() {
        boolean validData = true;
        if (mTitle.getText().length() == 0) {
            validData = false;
            mTitle.setError("Please enter title!");
        }

        if(mCompany.getText().length() == 0){
            validData = false;
            mCompany.setError("Please enter company!");
        }

        if(mLocationCity.getText().length() == 0){
            validData = false;
            mLocationCity.setError("Please enter city!");
        }

        if(mLocationState.getText().length() == 0){
            validData = false;
            mLocationState.setError("Please enter state!");
        }

        if(mYearlySalary.getText().length() == 0){
            validData = false;
            mYearlySalary.setError("Please enter yearly salary!");
        }

        if(mYearlyBonus.getText().length() == 0){
            validData = false;
            mYearlyBonus.setError("Please enter yearly bonus!");
        }


        if(mCostOfLiving.getText().length() == 0){
            validData = false;
            mCostOfLiving.setError("Please enter cost of living!");
        }

        if(mRetirementBenefit.getText().length() == 0){
            validData = false;
            mRetirementBenefit.setError("Please enter retirement benefit!");
        }

        if(mRestictedStockUnitAward.getText().length() == 0){
            validData = false;
            mRestictedStockUnitAward.setError("Please enter restricted stock unit award!");
        }

        if(mRelocationStipened.getText().length() == 0){
            validData = false;
            mRelocationStipened.setError("Please enter relocation stipend");
        }

        return validData;
    }

}