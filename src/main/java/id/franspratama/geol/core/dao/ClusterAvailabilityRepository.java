package id.franspratama.geol.core.dao;

import java.util.List;

import id.franspratama.geol.core.pojo.ClusterAvailability;
import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.TimeSpan;

public interface ClusterAvailabilityRepository extends ITimespanBasedAvailabilityRepository<ClusterAvailability>{
	public List<ClusterAvailability> getAvailability(NetworkTechnology netype,Region region,TimeSpan span);
}
