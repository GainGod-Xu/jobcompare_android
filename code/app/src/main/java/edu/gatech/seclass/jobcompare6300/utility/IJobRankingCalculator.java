package edu.gatech.seclass.jobcompare6300.utility;

import android.content.Context;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.model.Job;

public interface IJobRankingCalculator {
    Double calculateRanking(Job job, ComparisonSetting setting);
}
