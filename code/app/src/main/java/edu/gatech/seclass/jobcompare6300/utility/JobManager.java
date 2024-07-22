package edu.gatech.seclass.jobcompare6300.utility;

import android.content.Context;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.Result;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

public class JobManager implements IJobManager {

    IDbConnector dbConnector;
    private List<Job> jobs = new ArrayList<>();
    ComparisonSetting comparisonSetting;

    public JobManager(Context context) {
        dbConnector = new SQLiteDbConnector(context);
        initialize();
    }

    private void initialize()  {
        refreshComparisonSetting();
        refreshJobList();
    }

    @Override
    public void updateComparisonSetting(ComparisonSetting setting) {
        dbConnector.saveSetting(setting);
        comparisonSetting = setting;
    }

    @Override
    public ComparisonSetting getComparisonSetting() {
        return comparisonSetting;
    }

    private void refreshComparisonSetting() {
        Result result = dbConnector.readSetting();
        if(result.getSuccessful()) {
            comparisonSetting = (ComparisonSetting) result.getData();
        }
    }

    @Override
    public void refreshJobList() {
        Result result = dbConnector.loadAllJobs();
        if(result.getSuccessful()) {
            jobs = (List<Job>) result.getData();
            //TODO: Check Hao's code
            Collections.sort(jobs, (o1, o2) -> (int) - (o1.getScore(comparisonSetting) - o2.getScore(comparisonSetting)));
        }
    }

    @Override
    public List<Job> getAllJobs() {
        return jobs;
    }

    @Override
    public void addJob(Job job) {
        Result result = dbConnector.insertJob(job);
        if(result.getSuccessful()) {
            Job newJob = (Job)result.getData();
            jobs.add(newJob);
        }
    }

    @Override
    public void deleteJob(Job job){
        jobs.remove(job);
        dbConnector.deleteJob(job);
    }

    @Override
    public void updateJob(Job updatedJob) {
        for(int i = 0; i < jobs.size(); i++) {
            if(jobs.get(i).getId() == updatedJob.getId()) {
                jobs.set(i, updatedJob);
            }
        }
        dbConnector.updateJob(updatedJob);
    }

    @Override
    public void setCurrentJob(int id) {
        for(int i = 0; i < jobs.size(); i++) {
            if(jobs.get(i).getId() == id) {
                jobs.get(i).setCurrentJob(true);
                dbConnector.updateJob(jobs.get(i));
            } else if(jobs.get(i).isCurrentJob()) {
                jobs.get(i).setCurrentJob(false);
                dbConnector.updateJob(jobs.get(i));
            }
        }
    }

    @Override
    public Job getJob(int id) {
        return jobs.get(id);
    }

    @Override
    public Job getCurrentJob() {
        for(int i = 0; i < jobs.size(); i++) {
            if(jobs.get(i).isCurrentJob())
                return jobs.get(i);
        }
        return null;
    }
}
