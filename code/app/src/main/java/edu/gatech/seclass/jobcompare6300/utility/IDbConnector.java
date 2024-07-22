package edu.gatech.seclass.jobcompare6300.utility;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.Result;

public interface IDbConnector {
    // All functions return Result object
    // Result object has "data" property which is Object type. Store any type of return result
    // for example, object = List<Job>();
    // and receiving method will cast to its proper type after checking successful property

    Result loadAllJobs();
    Result insertJob(Job job);
    Result deleteJob(Job jon);
    Result updateJob(Job job);
    Result saveSetting(ComparisonSetting setting);
    Result readSetting();

}
