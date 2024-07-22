package edu.gatech.seclass.jobcompare6300.model;

public class Result {
    private Boolean successful;
    private String message = "";
    private Object data = null;

    public Result(Boolean successful) {
        this.successful = successful;
    }

    public Result(Boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public Result(Boolean successful, String message, Object data) {
        this.successful = successful;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
