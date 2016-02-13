package id.franspratama.geol.core.dao;

import java.util.List;

import id.franspratama.geol.core.pojo.AlarmDown;
import id.franspratama.geol.core.pojo.NEDown;
import id.franspratama.geol.core.pojo.NEDownAge;
import id.franspratama.geol.core.pojo.NEDownMovement;
import id.franspratama.geol.core.pojo.NEDownStatus;
import id.franspratama.geol.core.pojo.TimeSpan;

public interface INEDownRepository {
	
	public List<NEDownMovement> getNeDownMovementHourly(NEDownAge age,TimeSpan timespan);
	public List<NEDownMovement> getNeDownMovementDaily(NEDownAge age,TimeSpan timespan);
	public List<NEDownStatus> getNeDownStatusHourly();
	public List<AlarmDown> getCurrentNeDownList();
	public List<NEDown> getNEDownSummary();
}
