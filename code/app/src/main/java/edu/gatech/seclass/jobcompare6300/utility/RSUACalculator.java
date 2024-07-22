package edu.gatech.seclass.jobcompare6300.utility;


import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.model.Job;

public class RSUACalculator implements IJobRankingCalculator{
    public Double calculateRanking(Job job, ComparisonSetting setting) {
        Integer w1 = setting.getYearlySalaryWeight();
        Integer w2 = setting.getYearlyBonusWeight();
        Integer w3 = setting.getRetirementBenefitWeight();
        Integer w4 = setting.getRelocationStipendWeight();
        Integer w5 = setting.getRestrictedStockUnitAwardWeight();
        Integer totalWeights = w1 + w2 + w3 + w4 + w5;
        Double AYS = job.getYearlySalary() / job.getCostOfLiving() * 100;
        Double AYB = job.getYearlyBonus() / job.getCostOfLiving() * 100;
        Double RS = job.getRelocationStipend();
        Integer RPB = job.getRetirementBenefit();
        Double RSUA = job.getRestrictedStockUnitAward();
        Double score = (w1 * AYS + w2 * AYB + w3 * RS + w4 * RPB * AYS/100 + w5 * RSUA/4) / totalWeights;
        return score;
    }

    public RSUACalculator() {}
}
