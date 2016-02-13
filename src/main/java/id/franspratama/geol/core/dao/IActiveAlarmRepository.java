package id.franspratama.geol.core.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.ActiveAlarm;

public interface IActiveAlarmRepository extends JpaRepository<ActiveAlarm, Long>{

}
