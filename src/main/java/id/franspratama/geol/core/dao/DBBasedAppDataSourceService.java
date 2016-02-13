package id.franspratama.geol.core.dao;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.pojo.ExternalSource;

@Service("dbBasedAppDataSourceService")
public class DBBasedAppDataSourceService implements AppDataSourceService{

	@Autowired
	private IAppDataSourceRepository repository;
	
	@Override
	public File getSourceDir(String key) {
		ExternalSource eSource = repository.findByKey(key);
		File dirSource = new File( eSource.getSources() );
		return dirSource;
	}

}
