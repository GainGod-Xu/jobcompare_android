package edu.gatech.seclass.jobcompare6300.utility;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;

import java.util.List;

public interface IJobManager {
    List<Job> getAllJobs();
    void addJob(Job job);
    void deleteJob(Job job);
    void updateJob(Job job);
    void setCurrentJob(int id);
    Job getJob(int id);
    Job getCurrentJob();
    void refreshJobList();
    void updateComparisonSetting(ComparisonSetting setting);
    ComparisonSetting getComparisonSetting();
}
