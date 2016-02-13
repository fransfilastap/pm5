package id.franspratama.geol.core.dao;

import java.util.List;

import id.franspratama.geol.core.pojo.NEDownStatus;

public interface INEDownStatusRepository {
	public List<NEDownStatus> getNeDownStatusHourly();
	public List<NEDownStatus> geNeDownStatusDaily();

}
