package id.franspratama.geol.integration;

import java.io.File;
import java.util.List;

public interface Parser<T> {
	public List<T> parse(File file);
}
