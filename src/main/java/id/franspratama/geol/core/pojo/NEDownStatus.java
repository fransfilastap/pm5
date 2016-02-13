package id.franspratama.geol.core.pojo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import id.franspratama.geol.util.DateToStringSerializer;

public class NEDownStatus {

    private String region;
    private Date time;
    private Date timeBefore;
    private int downClosed;
    private int downNew;
    private int downOpen;

    public NEDownStatus() {
    }

    public NEDownStatus(String region, Date time,Date timeBefore, int downClosed,int downNew, int downOpen) {
        this.region = region;
        this.time = time;
        this.downNew = downNew;
        this.downOpen = downOpen;
        this.downClosed = downClosed;
        this.timeBefore = timeBefore;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public int getDownNew() {
        return downNew;
    }

    public void setDownNew(int downNew) {
        this.downNew = downNew;
    }

    public int getDownOpen() {
        return downOpen;
    }

    public void setDownOpen(int downOpen) {
        this.downOpen = downOpen;
    }

    @JsonSerialize(using=DateToStringSerializer.class)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getDownClosed() {
        return downClosed;
    }

    public void setDownClosed(int downClosed) {
        this.downClosed = downClosed;
    }

    @JsonSerialize(using=DateToStringSerializer.class)
	public Date getTimeBefore() {
		return timeBefore;
	}

	public void setTimeBefore(Date timeBefore) {
		this.timeBefore = timeBefore;
	}
	
    
}
