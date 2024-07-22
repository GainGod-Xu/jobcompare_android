package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;


public class FragmentSetting extends Fragment {

    private TextInputEditText yswText;
    private TextInputEditText ybText;
    private TextInputEditText rbText;
    private TextInputEditText rsText;
    private TextInputEditText RSUAText;

    private Button buttonSave;
    private Button buttonCancel;

    public FragmentSetting() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yswText = view.findViewById(R.id.edit_settingYSW);
        ybText = view.findViewById(R.id.edit_settingYB);
        rbText = view.findViewById(R.id.edit_settingRB);
        rsText = view.findViewById(R.id.edit_settingRS);
        RSUAText = view.findViewById(R.id.edit_settingRSUA);

        buttonSave = view.findViewById(R.id.edit_settingSave);
        buttonCancel = view.findViewById(R.id.edit_settingCancel);

        populateSettingsWithSavedValue();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateSettingsWithSavedValue();
                App.getInstance(null).navigateTo(App.NavigationMenu.HOME);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValidData()) {
                    ComparisonSetting cps = createSetting();
                    App.getInstance(null).getJobManager().updateComparisonSetting(cps);
                    Toast.makeText(getActivity(),"You just changed the setting!",Toast.LENGTH_LONG).show();
                    App.getInstance(null).navigateTo(App.NavigationMenu.HOME);
                }
            }
        });
    }

    private void populateSettingsWithSavedValue() {
        ComparisonSetting cps = App.getInstance(null).getJobManager().getComparisonSetting();
        yswText.setText(String.valueOf(cps.getYearlySalaryWeight()));
        ybText.setText(String.valueOf(cps.getYearlyBonusWeight()));
        rbText.setText(String.valueOf(cps.getRetirementBenefitWeight()));
        rsText.setText(String.valueOf(cps.getRelocationStipendWeight()));
        RSUAText.setText(String.valueOf(cps.getRestrictedStockUnitAwardWeight()));
    }

    private ComparisonSetting createSetting() {
        ComparisonSetting cps = new ComparisonSetting();
        cps.setSettings(Integer.valueOf(yswText.getText().toString()),
                        Integer.valueOf(ybText.getText().toString()),
                        Integer.valueOf(rbText.getText().toString()),
                        Integer.valueOf(rsText.getText().toString()),
                        Integer.valueOf(RSUAText.getText().toString()));
        return cps;
    }

    private boolean isValidData() {
        boolean status = true;
        if(yswText.getText().length() == 0) {
            status = false;
            yswText.setError("Please enter yearly salary!");
        }

        if(ybText.getText().length() == 0){
            status = false;
            ybText.setError("Please enter yearly bonus!");
        }

        if(rbText.getText().length() == 0){
            status = false;
            rbText.setError("Please enter retirement benefit!");
        }

        if(rsText.getText().length() == 0) {
            status = false;
            rsText.setError("Please enter relocation stipend");
        }

        if(RSUAText.getText().length() == 0){
            status = false;
            RSUAText.setError("Please enter restricted stock unit award!");
        }
        return status;
    }
}