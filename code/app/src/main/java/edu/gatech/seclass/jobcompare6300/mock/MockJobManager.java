package edu.gatech.seclass.jobcompare6300.mock;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.utility.IJobManager;
import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;

public class MockJobManager implements IJobManager {

    private List<Job> jobs = new ArrayList<>();

    public MockJobManager() {
        // Initialize jobs with fake Job data
        // for example
        // jobs.add(new Job(<values here>));
        jobs.add(new Job("Software Engineer", "Google", "San Francisco", "CA",
                264, 144000.0, 22000.0, 50, 5000.0,
                140000.0, false, 1));
        jobs.add(new Job("Web Developer", "Facebook", "New York City", "NY",
                269, 164000.0, 24000.0, 45, 2000.0,
                216000.0, true, 2));
        jobs.add(new Job("SDE Distributed System", "Amazon", "Boston", "MA",
                211, 150000.0, 45000.0, 20, 0.0,
                120000.0, false, 3));
        jobs.add(new Job("Software Engineer", "Uber", "Seattle", "WA",
                211, 165000.0, 65000.0, 15, 5000.0,
                135000.0, false, 4));
        jobs.add(new Job("Machine Learning Engineer", "Apple", "San Jose", "CA",
                212, 160000.0, 30000.0, 35, 10000.0,
                180000.0, false, 5));
        jobs.add(new Job("Deep Learning Engineer", "Apple", "San Jose", "CA",
                212, 160000.0, 30000.0, 35, 10000.0,
                180000.0, false, 6));
        jobs.add(new Job("Reinforcement Learning Engineer", "Apple", "San Jose", "CA",
                212, 160000.0, 30000.0, 35, 10000.0,
                180000.0, false, 7));

        // This will allow us to continue build UI without actual Job manager implementation.

    }
    @Override
    public List<Job> getAllJobs() {
        return jobs;
    }

    @Override
    public void addJob(Job job) {
        jobs.add(job);
    }

    @Override
    public void deleteJob(Job job) {
        jobs.remove(job);
    }

    @Override
    public void refreshJobList() {
        throw new NotImplementedError();
    }

    @Override
    public void updateJob(Job job) {
        for(int i=0; i < jobs.size(); i++) {
            if (jobs.get(i).getId() == job.getId()) {
                jobs.set(i, job);
                break;
            }
        }
    }

    @Override
    public void setCurrentJob(int id) {
        for (Job job : jobs) {
            job.setCurrentJob(job.getId() == id);
        }
    }

    @Override
    public Job getJob(int id) {
        for (Job job : jobs) {
            if (job.getId() == id) {
                return job;
            }
        }
        return null;
    }

    @Override
    public Job getCurrentJob() {
        for (Job job : jobs) {
            if (job.isCurrentJob()) {
                return job;
            }
        }
        return null;
    }

    @Override
    public void updateComparisonSetting(ComparisonSetting setting) {

    }

    @Override
    public ComparisonSetting getComparisonSetting() {
        return null;
    }
}
