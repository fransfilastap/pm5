package id.franspratama.geol.core.dao;

import java.util.List;

import id.franspratama.geol.core.pojo.AlarmDown;

public interface IAlarmDownRepository {
	public List<AlarmDown> getCurrentNeDownList();
}
