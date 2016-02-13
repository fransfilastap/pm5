package id.franspratama.geol.core.dao;

import org.springframework.data.repository.Repository;

import id.franspratama.geol.core.pojo.ExternalSource;

public interface IAppDataSourceRepository extends Repository<ExternalSource, String>{
	public ExternalSource findByKey(String key);
}
