package id.franspratama.geol.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.AlarmFilter;
import id.franspratama.geol.core.pojo.AlarmFilterType;
import id.franspratama.geol.core.pojo.Severity;

public interface IAlarmFilterRepository extends JpaRepository<AlarmFilter, Integer>{
	
	public List<AlarmFilter> findBySeverity(Severity severity);
	public List<AlarmFilter> findByType(AlarmFilterType type);
}
