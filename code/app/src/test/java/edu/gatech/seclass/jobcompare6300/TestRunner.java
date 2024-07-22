package edu.gatech.seclass.jobcompare6300;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

// 1. Add more test class(es) here
@Suite.SuiteClasses({
        JobClassTest.class,
        JobManagerClassTest.class
    }
)

// 2. Run this empty class for all unit tests listed above
class TestRunner {
    
}
