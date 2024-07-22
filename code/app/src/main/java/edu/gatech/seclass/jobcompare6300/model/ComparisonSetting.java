package edu.gatech.seclass.jobcompare6300.model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class ComparisonSetting {
    private int yearlySalaryWeight;
    private int yearlyBonusWeight;
    private int retirementBenefitWeight;
    private int relocationStipendWeight;
    private int restrictedStockUnitAwardWeight;

    public ComparisonSetting(Integer yearlySalaryWeight,
                              Integer yearlyBonusWeight,
                              Integer retirementBenefitWeight,
                              Integer relocationStipendWeight,
                              Integer restrictedStockUnitAwardWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
        this.yearlyBonusWeight = yearlyBonusWeight;
        this.retirementBenefitWeight = retirementBenefitWeight;
        this.relocationStipendWeight = relocationStipendWeight;
        this.restrictedStockUnitAwardWeight = restrictedStockUnitAwardWeight;
    }

    public ComparisonSetting() {
        yearlySalaryWeight = 1;
        yearlyBonusWeight = 1;
        retirementBenefitWeight = 1;
        relocationStipendWeight = 1;
        restrictedStockUnitAwardWeight = 1;
    }

    public int getYearlySalaryWeight() {
        return yearlySalaryWeight;
    }

    public int getYearlyBonusWeight() {
        return yearlyBonusWeight;
    }

    public int getRetirementBenefitWeight() {
        return retirementBenefitWeight;
    }

    public int getRelocationStipendWeight() {
        return relocationStipendWeight;
    }

    public int getRestrictedStockUnitAwardWeight() {
        return restrictedStockUnitAwardWeight;
    }

    public void setSettings(int yearlySalaryWeight,
                            int yearlyBonusWeight,
                            int retirementBenefitWeight,
                            int relocationStipendWeight,
                            int restrictedStockUnitAwardWeight) {
        setYearlySalaryWeight(yearlySalaryWeight);
        setYearlyBonusWeight(yearlyBonusWeight);
        setRetirementBenefitWeight(retirementBenefitWeight);
        setRelocationStipendWeight(relocationStipendWeight);
        setRestrictedStockUnitAwardWeight(restrictedStockUnitAwardWeight);
    }

    public List<Integer> getSettings(){
        List<Integer> settingList = new ArrayList<Integer>();
        settingList.add(yearlySalaryWeight);
        settingList.add(yearlyBonusWeight);
        settingList.add(retirementBenefitWeight);
        settingList.add(relocationStipendWeight);
        settingList.add(restrictedStockUnitAwardWeight);
        return settingList;
    }

    public void setYearlySalaryWeight(int weight) {
        yearlySalaryWeight = weight;
    }

    public void setYearlyBonusWeight(int weight) {
        yearlyBonusWeight = weight;
    }

    public void setRetirementBenefitWeight(int weight) {
        retirementBenefitWeight = weight;
    }

    public void setRelocationStipendWeight(int weight) {
        relocationStipendWeight = weight;
    }

    public void setRestrictedStockUnitAwardWeight(int weight) {
        restrictedStockUnitAwardWeight = weight;
    }



}
