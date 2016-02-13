package id.franspratama.geol.core.pojo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import id.franspratama.geol.util.CustomDateTimeSerializer;

public class NEDownMovement {
    private Date time;
    private String region;
    private int totalDown;

    public NEDownMovement() {
    }

    public NEDownMovement(Date time, String region, int totalDown) {
        this.time = time;
        this.region = region;
        this.totalDown = totalDown;
    }

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTotalDown() {
        return totalDown;
    }

    public void setTotalDown(int totalDown) {
        this.totalDown = totalDown;
    }
}
