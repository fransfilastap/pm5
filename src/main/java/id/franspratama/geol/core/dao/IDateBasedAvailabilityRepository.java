package id.franspratama.geol.core.dao;

import java.util.Date;
import java.util.List;

/**
 * 
 * Availability Repository Interface to make it easier to extend
 * 
 * @author franspotter
 *
 * @param <T> where T is availability entity class
 */

public interface IDateBasedAvailabilityRepository<T> {
	public List<T> getAvailability(Date from, Date to);
}
