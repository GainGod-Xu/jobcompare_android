package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.gatech.seclass.jobcompare6300.model.Job;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Currency;

public class FragmentCompare extends Fragment {

    Job job1;
    Job job2;

    public FragmentCompare() {

    }

    public FragmentCompare(Job job1, Job job2 ) {
        this.job1 = job1;
        this.job2 = job2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compare, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button closeButton = view.findViewById(R.id.buttonCloseComparisonTable);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getInstance(null).navigateTo(App.NavigationMenu.HOME);
            }
        });

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("USD"));

        ((TextView)view.findViewById(R.id.job1Title)).setText(job1.getTitle());
        ((TextView)view.findViewById(R.id.job2Title)).setText(job2.getTitle());

        ((TextView)view.findViewById(R.id.job1Company)).setText(job1.getCompany());
        ((TextView)view.findViewById(R.id.job2Company)).setText(job2.getCompany());

        ((TextView)view.findViewById(R.id.job1Location)).setText(job1.getLocationCity() + "," + job1.getLocationState());
        ((TextView)view.findViewById(R.id.job2Location)).setText(job2.getLocationCity() + "," + job2.getLocationState());

        ((TextView)view.findViewById(R.id.job1CostOfLiving)).setText(job1.getCostOfLiving().toString());
        ((TextView)view.findViewById(R.id.job2CostOfLiving)).setText(job2.getCostOfLiving().toString());

        ((TextView)view.findViewById(R.id.job1YearlySalary)).setText(format.format(job1.getYearlySalary()));
        ((TextView)view.findViewById(R.id.job2YearlySalary)).setText(format.format(job2.getYearlySalary()));

        ((TextView)view.findViewById(R.id.job1YearlyBonus)).setText(format.format(job1.getYearlyBonus()));
        ((TextView)view.findViewById(R.id.job2YearlyBonus)).setText(format.format(job2.getYearlyBonus()));

        ((TextView)view.findViewById(R.id.job1RetirementBenefit)).setText(job1.getRetirementBenefit().toString());
        ((TextView)view.findViewById(R.id.job2RetirementBenefit)).setText(job2.getRetirementBenefit().toString());

        ((TextView)view.findViewById(R.id.job1RelocationStipend)).setText(job1.getRelocationStipend().toString());
        ((TextView)view.findViewById(R.id.job2RelocationStipend)).setText(job2.getRelocationStipend().toString());

        ((TextView)view.findViewById(R.id.job1RestrictedStock)).setText(job1.getRestrictedStockUnitAward().toString());
        ((TextView)view.findViewById(R.id.job2RestrictedStock)).setText(job2.getRestrictedStockUnitAward().toString());
    }
}