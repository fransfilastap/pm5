package id.franspratama.geol.util;

import java.io.File;

public class FilePair implements Comparable<File>{

	public long time;
	public File file;
	
	
	
	public FilePair(File file) {
		this.file = file;
		time 	  = file.lastModified();
	}



	@Override
	public int compareTo(File o) {
		long time2 = o.lastModified();
		return time < time2 ? -1 : time == time2 ? 0 : 1;
	}

}
