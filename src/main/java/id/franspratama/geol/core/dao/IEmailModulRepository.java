package id.franspratama.geol.core.dao;

import id.franspratama.geol.core.pojo.EmailModule;

public interface IEmailModulRepository extends IBaseRepository<EmailModule, String>{
	public EmailModule findByModuleKey(String key);
}
