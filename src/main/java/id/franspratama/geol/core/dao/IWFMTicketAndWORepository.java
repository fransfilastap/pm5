package id.franspratama.geol.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

public interface IWFMTicketAndWORepository extends JpaRepository<WFMTicketAndWorkOrder, Integer>{
	
}
