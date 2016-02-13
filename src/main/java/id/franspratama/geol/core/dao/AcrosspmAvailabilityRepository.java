package id.franspratama.geol.core.dao;

import java.util.List;

public interface AcrosspmAvailabilityRepository<T> {
	public void store(List<T> availabilities);
}
