package id.franspratama.geol.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrderFileLog;

public interface IWFMTicketAndWorkOrderFileLogRepository extends JpaRepository<WFMTicketAndWorkOrderFileLog, Integer>{
	public WFMTicketAndWorkOrderFileLog findByFilename(String filename);
}
