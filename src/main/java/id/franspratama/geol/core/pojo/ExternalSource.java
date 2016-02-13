package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sources",catalog="geolv2")
public class ExternalSource {
	
	@Id
	@Column(name="key")
	private String key;
	
	@Column(name="type")
	private String type;

	@Column(name="sources")
	private String sources;

	public ExternalSource(String key, String type, String sources) {
		super();
		this.key = key;
		this.type = type;
		this.sources = sources;
	}

	public ExternalSource() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}
	
	
}
