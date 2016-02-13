package id.franspratama.geol.core.dao;

import java.util.Date;
import java.util.List;

import id.franspratama.geol.core.pojo.NEDownAge;
import id.franspratama.geol.core.pojo.NEDownMovement;

public interface IDateBasedNEDownMovementRepository {
	public List<NEDownMovement> getNeDownMovementHourly(NEDownAge age, Date from, Date to );
	public List<NEDownMovement> getNeDownMovementDaily(NEDownAge age,Date from, Date to);
}
