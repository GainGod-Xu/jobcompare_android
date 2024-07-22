package edu.gatech.seclass.jobcompare6300.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.Result;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDbConnector extends SQLiteOpenHelper implements IDbConnector {

    public static final int DB_QUERY_EXEC_FAILED = -1;

    public static final String DATABASE_NAME = "JobCompare6300.db";
    public static final String JOB_TABLE_NAME = "job";
    public static final String SETTING_TABLE_NAME = "setting";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "Title";
    public static final String COL_COMPANY = "Company";
    public static final String COL_LOCATION_CITY = "LocationCity";
    public static final String COL_LOCATION_STATE = "LocationState";
    public static final String COL_COST_OF_LIVING = "CostOfLiving";
    public static final String COL_YEARLY_SALARY = "YearlySalary";
    public static final String COL_YEARLY_BONUS = "YearlyBonus";
    public static final String COL_RETIREMENT_BENEFIT = "RetirementBenefit";
    public static final String COL_RELOCATION_STIPEND = "RelocationStipend";
    public static final String COL_RESTRICTED_STOCK_UNIT_AWARD = "RestrictedStockUnitAward";
    public static final String COL_IS_CURRENT_JOB = "IsCurrentJob";
    public static final String COL_SALARY_WEIGHT = "Salary";
    public static final String COL_BONUS_WEIGHT = "Bonus";
    public static final String COL_RETIREMENT_BENEFIT_WEIGHT = "RetirementBenefit";
    public static final String COL_RELOCATION_STIPEND_WEIGHT = "RelocationStipend";
    public static final String COL_RESTRICTED_STOCK_UNIT_AWARD_WEIGHT = "RestrictedStockUnitAward";



    public SQLiteDbConnector(Context ctx) {
        super(ctx, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Initialize Tables and default Setting record
        sqLiteDatabase.execSQL(getCREATE_Job_Table_Query());
        sqLiteDatabase.execSQL(getCREATE_Setting_Table_Query());
        sqLiteDatabase.execSQL(getINSERT_Default_Setting_Query());
    }
    private String getCREATE_Job_Table_Query() {
        return "CREATE TABLE IF NOT EXISTS " + JOB_TABLE_NAME  + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT," +
                COL_COMPANY + " TEXT," +
                COL_LOCATION_CITY + " TEXT," +
                COL_LOCATION_STATE + " TEXT," +
                COL_COST_OF_LIVING + " INTEGER," +
                COL_YEARLY_SALARY + " REAL," +
                COL_YEARLY_BONUS + " REAL," +
                COL_RETIREMENT_BENEFIT + " INTEGER," +
                COL_RELOCATION_STIPEND + " REAL," +
                COL_RESTRICTED_STOCK_UNIT_AWARD + " REAL," +
                COL_IS_CURRENT_JOB + " INTEGER)";
    }
    private String getCREATE_Setting_Table_Query() {
        return "CREATE TABLE IF NOT EXISTS " + SETTING_TABLE_NAME + " (" +
                 COL_SALARY_WEIGHT + " INTEGER," +
                 COL_BONUS_WEIGHT + " INTEGER," +
                 COL_RETIREMENT_BENEFIT + " INTEGER, " +
                 COL_RELOCATION_STIPEND_WEIGHT + " INTEGER, " +
                 COL_RESTRICTED_STOCK_UNIT_AWARD_WEIGHT +  " INTEGER)";
    }
    private String getINSERT_Default_Setting_Query() {
        return "INSERT INTO " + SETTING_TABLE_NAME + "(" +
                COL_SALARY_WEIGHT + "," + COL_BONUS_WEIGHT + "," + COL_RETIREMENT_BENEFIT + "," + COL_RELOCATION_STIPEND_WEIGHT + "," + COL_RESTRICTED_STOCK_UNIT_AWARD_WEIGHT +
                ") VALUES (1,1,1,1,1)";
    }
    private String getSELECT_From_Job_Table_Query() {
        return "SELECT " + COL_TITLE + "," + COL_COMPANY + "," + COL_LOCATION_CITY + "," + COL_LOCATION_STATE + "," + COL_COST_OF_LIVING + "," +
                COL_YEARLY_SALARY + "," + COL_YEARLY_BONUS + "," + COL_RETIREMENT_BENEFIT + "," + COL_RELOCATION_STIPEND + "," +
                COL_RESTRICTED_STOCK_UNIT_AWARD + "," + COL_IS_CURRENT_JOB + "," + COL_ID +  " FROM " + JOB_TABLE_NAME + " ";
    }

    private String getSELECT_From_Setting_Table_Query() {
        return "SELECT " +
                COL_SALARY_WEIGHT + "," + COL_BONUS_WEIGHT + "," + COL_RETIREMENT_BENEFIT + "," + COL_RELOCATION_STIPEND_WEIGHT + "," + COL_RESTRICTED_STOCK_UNIT_AWARD_WEIGHT +
                " FROM " + SETTING_TABLE_NAME + " ";
    }

    private Job createJobFromCursor(Cursor cursor) {
        String title = cursor.getString(0);
        String company = cursor.getString(1);
        String location_city = cursor.getString(2);
        String location_state = cursor.getString(3);
        int cost_of_living = cursor.getInt(4);
        double yearly_salary = cursor.getDouble(5);
        double yearly_bonus = cursor.getDouble(6);
        int retired_benefit = cursor.getInt(7);
        double relocation_stipend = cursor.getInt(8);
        double restricted_stock_unit_award = cursor.getInt(9);
        boolean isCurrentJob = cursor.getInt(10) == 1;
        int id = cursor.getInt(11);

        Job job = new Job(title, company, location_city, location_state, cost_of_living, yearly_salary, yearly_bonus, retired_benefit, relocation_stipend, restricted_stock_unit_award,isCurrentJob, id);
        return job;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + JOB_TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + SETTING_TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    private Job getMostRecentJobRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getSELECT_From_Job_Table_Query()
                        + " ORDER BY ID DESC LIMIT 1", null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            Job job = createJobFromCursor(cursor);
            cursor.close();
            return job;
        } else {
            cursor.close();
            return null;
        }
    }

    private boolean updateSettingData(ComparisonSetting setting) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE IF EXISTS " + SETTING_TABLE_NAME;
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS " + SETTING_TABLE_NAME + " (Salary INTEGER," +
                "Bonus INTEGER, RetirementBenefit INTEGER, RelocationStipend INTEGER, RestrictedStockUnitAward INTEGER)";
        db.execSQL(query);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SALARY_WEIGHT, setting.getYearlySalaryWeight());
        contentValues.put(COL_BONUS_WEIGHT, setting.getYearlyBonusWeight());
        contentValues.put(COL_RETIREMENT_BENEFIT_WEIGHT, setting.getRetirementBenefitWeight());
        contentValues.put(COL_RELOCATION_STIPEND_WEIGHT, setting.getRelocationStipendWeight());
        contentValues.put(COL_RESTRICTED_STOCK_UNIT_AWARD_WEIGHT, setting.getRestrictedStockUnitAwardWeight());
        long result = db.insert(SETTING_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    @Override
    public Result loadAllJobs() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getSELECT_From_Job_Table_Query(), null);
            List<Job> jobs = new ArrayList<>();
            while (cursor.moveToNext()) {
                Job job = createJobFromCursor(cursor);
                jobs.add(job);
            }
            cursor.close();

            return new Result(true, "", jobs);
        } catch (Exception e) {
            return new Result(false, "Failed to load Jobs - " + e.getMessage());
        }
    }

    @Override
    public Result insertJob(Job job) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_TITLE, job.getTitle());
            contentValues.put(COL_COMPANY, job.getCompany());
            contentValues.put(COL_LOCATION_CITY, job.getLocationCity());
            contentValues.put(COL_LOCATION_STATE, job.getLocationState());
            contentValues.put(COL_COST_OF_LIVING, Integer.valueOf(job.getCostOfLiving()));
            contentValues.put(COL_YEARLY_SALARY, Double.valueOf(job.getYearlySalary()));
            contentValues.put(COL_YEARLY_BONUS, Double.valueOf(job.getYearlyBonus()));
            contentValues.put(COL_RETIREMENT_BENEFIT, Integer.valueOf(job.getRetirementBenefit()));
            contentValues.put(COL_RELOCATION_STIPEND, Double.valueOf(job.getRelocationStipend()));
            contentValues.put(COL_RESTRICTED_STOCK_UNIT_AWARD, Double.valueOf(job.getRestrictedStockUnitAward()));
            contentValues.put(COL_IS_CURRENT_JOB, job.isCurrentJob() ? 1 : 0);
            long returnValue = db.insert(JOB_TABLE_NAME, null, contentValues);

            if(returnValue != DB_QUERY_EXEC_FAILED) {
                return new Result(true, "Successfully Inserted", getMostRecentJobRecord());
            } else {
                return new Result(false, "Failed to insert Job Record");
            }
        } catch (Exception e) {
            return new Result(false, "Failed to Insert Job Record - " + e.getMessage());
        }
    }

    @Override
    public Result deleteJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isSuccess = db.delete(JOB_TABLE_NAME, "ID = ?", new String[] {String.valueOf(job.getId())}) > 0;
        return isSuccess ? new Result(true, "Job Deleted!") : new Result(false, "Job not Deleted!");
    }

    @Override
    public Result updateJob(Job job) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_TITLE, job.getTitle());
            contentValues.put(COL_COMPANY, job.getCompany());
            contentValues.put(COL_LOCATION_CITY, job.getLocationCity());
            contentValues.put(COL_LOCATION_STATE, job.getLocationState());
            contentValues.put(COL_COST_OF_LIVING, job.getCostOfLiving());
            contentValues.put(COL_YEARLY_SALARY, job.getYearlySalary());
            contentValues.put(COL_YEARLY_BONUS, job.getYearlyBonus());
            contentValues.put(COL_RETIREMENT_BENEFIT, job.getRetirementBenefit());
            contentValues.put(COL_RELOCATION_STIPEND, job.getRelocationStipend());
            contentValues.put(COL_RESTRICTED_STOCK_UNIT_AWARD, job.getRestrictedStockUnitAward());
            contentValues.put(COL_IS_CURRENT_JOB, job.isCurrentJob() ? 1 : 0);
            int return_value = db.update(JOB_TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(job.getId())});
            if(return_value != DB_QUERY_EXEC_FAILED) {
                return new Result(true, "Job successfully Updated", job);
            } else {
                return new Result(false, "Failed to update Job - Please check data or query", job);
            }

        } catch (Exception e) {
            return new Result(false, "Failed to update Job - " + e.getMessage());
        }
    }

    @Override
    public Result saveSetting(ComparisonSetting setting) {
        boolean isSuccess = updateSettingData(setting);
        return isSuccess ? new Result(true, "Setting Saved!") : new Result(false, "Setting not Saved!");
    }

    @Override
    public Result readSetting() {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getSELECT_From_Setting_Table_Query(), null);
            cursor.moveToFirst();
            ComparisonSetting setting = new ComparisonSetting(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
            cursor.close();

            return new Result(true, "", setting);

        } catch (Exception e) {
            return new Result(false, "Failed to read Setting - " + e.getMessage());
        }

    }
}
