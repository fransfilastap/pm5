package id.franspratama.geol.core.dao;

import java.util.List;

import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.TimeSpan;

public interface ITimespanBasedAvailabilityRepository<T> {
	public List<T> getAvailability(TimeSpan span);
	public List<T> getAvailability(TimeSpan span, Region region, NetworkTechnology technology);
}
