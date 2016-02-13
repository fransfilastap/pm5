package id.franspratama.geol.core.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import id.franspratama.geol.core.pojo.ClusterAvailability;

public interface IClusterAvailabilityRepository extends Repository<ClusterAvailability, Integer>{

	
}
