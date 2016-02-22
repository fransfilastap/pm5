package id.franspratama.geol.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

public interface IWFMTicketAndWORepository extends JpaRepository<WFMTicketAndWorkOrder, Integer>{
	public List<WFMTicketAndWorkOrder> findBySiteId(String siteId);
}
