package id.franspratama.geol.core.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface IBaseRepository<T, ID extends Serializable> extends Repository<T,ID> {
	public List<T> findAll();
	public T save(T persisted);
	public void delete(T deleted);
}
