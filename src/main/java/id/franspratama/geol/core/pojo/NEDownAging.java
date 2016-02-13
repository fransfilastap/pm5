package id.franspratama.geol.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@SqlResultSetMapping(
		name="neDownAging1Hour",
		entities = @EntityResult(
				entityClass = NEDownAging.class,
				fields={
						@FieldResult(name="id",column="ID"),
						@FieldResult(name="region",column="REGION"),
						@FieldResult(name="one_to_four_h",column="ZERO_TO_FOUR_H"),
						@FieldResult(name="four_to_24_h",column="FOUR_TO_TWENTYFOUR_H"),
						@FieldResult(name="one_to_three_d",column="ONE_TO_THREE_D"),
						@FieldResult(name="three_to_seven_d",column="THREE_TO_SEVEN_D"),
						@FieldResult(name="more_than_seven_d",column="MORE_THAN_SEVEN_D")
				}
				
		))

@Entity
@Table(name="nedown_by_aging_1hour",catalog="geolv2")
public class NEDownAging {
	
	@Column(name="time")
	private Date time;
	
	@Column(name="region")
	private String region;
	
	@Column(name="one_to_four_h")
	private int oneHourToFour;
	
	@Column(name="four_to_24_h")
	private int fourTo24Hour;
	
	@Column(name="one_to_three_d")
	private int oneToThreeDay;
	
	@Column(name="three_to_seven_d")
	private int threeToSevendDay;
	
	@Column(name="more_than_seven_d")
	private int moreThanSevenDay;
	
	@Id @GeneratedValue
	@Column(name="id")
	private int id;

	public NEDownAging() {
		super();
	}

	public NEDownAging(Date time, String region, int oneHourToFour, int fourTo24Hour, int oneToThreeDay,
			int threeToSevendDay, int moreThanSevenDay) {
		super();
		this.time = time;
		this.region = region;
		this.oneHourToFour = oneHourToFour;
		this.fourTo24Hour = fourTo24Hour;
		this.oneToThreeDay = oneToThreeDay;
		this.threeToSevendDay = threeToSevendDay;
		this.moreThanSevenDay = moreThanSevenDay;
	}

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

	public int getOneHourToFour() {
		return oneHourToFour;
	}

	public void setOneHourToFour(int oneHourToFour) {
		this.oneHourToFour = oneHourToFour;
	}

	public int getFourTo24Hour() {
		return fourTo24Hour;
	}

	public void setFourTo24Hour(int fourTo24Hour) {
		this.fourTo24Hour = fourTo24Hour;
	}

	public int getOneToThreeDay() {
		return oneToThreeDay;
	}

	public void setOneToThreeDay(int oneToThreeDay) {
		this.oneToThreeDay = oneToThreeDay;
	}

	public int getThreeToSevendDay() {
		return threeToSevendDay;
	}

	public void setThreeToSevendDay(int threeToSevendDay) {
		this.threeToSevendDay = threeToSevendDay;
	}

	public int getMoreThanSevenDay() {
		return moreThanSevenDay;
	}

	public void setMoreThanSevenDay(int moreThanSevenDay) {
		this.moreThanSevenDay = moreThanSevenDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
