package id.franspratama.geol.core.services;

import org.springframework.data.domain.Sort;

import id.franspratama.geol.web.api.ActiveAlarmDetailResponseWrapper;

public interface IActiveAlarmService {

	public static final int PAGE_SIZE = 500;
	
	public ActiveAlarmDetailResponseWrapper getActiveAlarmList(Integer pageNumber,Sort sort,String[] fields);
	
}
