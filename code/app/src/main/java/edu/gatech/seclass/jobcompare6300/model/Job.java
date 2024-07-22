package edu.gatech.seclass.jobcompare6300.model;

import edu.gatech.seclass.jobcompare6300.utility.IJobRankingCalculator;
import edu.gatech.seclass.jobcompare6300.utility.RSUACalculator;

public class Job {
    private int id;
    private String title;
    private String company;
    private String locationCity;
    private String locationState;
    private Double yearlySalary;
    private Double yearlyBonus;
    private Integer retirementBenefit;
    private Integer costOfLiving;
    private Double relocationStipend;
    private Double restrictedStockUnitAward;
    private boolean isCurrentJob;

    public Job(String title, String company, String locationCity, String locationState, Integer costOfLiving, Double yearlySalary,
               Double yearlyBonus, Integer retirementBenefit, Double relocationStipend, Double restrictedStockUnitAward,
               boolean isCurrentJob, int id) {
        this.title = title;
        this.company = company;
        this.locationCity = locationCity;
        this.locationState = locationState;
        this.costOfLiving = costOfLiving;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.retirementBenefit = retirementBenefit;
        this.relocationStipend = relocationStipend;
        this.restrictedStockUnitAward = restrictedStockUnitAward;
        this.isCurrentJob = isCurrentJob;
        this.id = id;
    }

    public int getId() {return id;}

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public String getLocationState() {
        return locationState;
    }

    public Double getYearlySalary() {
        return yearlySalary;
    }

    public Double getYearlyBonus() {
        return yearlyBonus;
    }

    public Integer getRetirementBenefit() {
        return retirementBenefit;
    }

    public Integer getCostOfLiving() {
        return costOfLiving;
    }

    public Double getRelocationStipend(){
        return relocationStipend;
    }

    public Double getRestrictedStockUnitAward() {
        return restrictedStockUnitAward;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean isCurrentJob) {
        this.isCurrentJob = isCurrentJob;
    }

    public Double getScore(ComparisonSetting setting) {
        IJobRankingCalculator c = new RSUACalculator();
        return c.calculateRanking(this, setting);
    }
}
