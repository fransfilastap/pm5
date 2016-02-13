package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email_modules",catalog="geolv2")
public class EmailModule {
	
	@Id
	@Column(name="module_key",nullable=false)
	private String moduleKey;
	
	@Column(name="email_address")
	private String emailAddress;
	
	@Column(name="email_subject")
	private String emailSubject;
	
	@Column(name="recepients")
	private String recepients;
	
	@Column(name="cc")
	private String cc;
	
	@Column(name="bcc")
	private String bcc;
	
	@Column(name="moduleParam")
	private String moduleParam;

	public EmailModule() {
		super();
	}

	public EmailModule(String moduleKey, String emailAddress, String emailSubject, String recepients, String cc,
			String bcc, String moduleParam) {
		super();
		this.moduleKey = moduleKey;
		this.emailAddress = emailAddress;
		this.emailSubject = emailSubject;
		this.recepients = recepients;
		this.cc = cc;
		this.bcc = bcc;
		this.moduleParam = moduleParam;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getRecepients() {
		return recepients;
	}

	public void setRecepients(String recepients) {
		this.recepients = recepients;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getModuleParam() {
		return moduleParam;
	}

	public void setModuleParam(String moduleParam) {
		this.moduleParam = moduleParam;
	}

	@Override
	public String toString() {
		return "EmailModule [moduleKey=" + moduleKey + ", emailAddress=" + emailAddress + ", emailSubject="
				+ emailSubject + ", recepients=" + recepients + ", cc=" + cc + ", bcc=" + bcc + ", moduleParam="
				+ moduleParam + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bcc == null) ? 0 : bcc.hashCode());
		result = prime * result + ((cc == null) ? 0 : cc.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((emailSubject == null) ? 0 : emailSubject.hashCode());
		result = prime * result + ((moduleKey == null) ? 0 : moduleKey.hashCode());
		result = prime * result + ((moduleParam == null) ? 0 : moduleParam.hashCode());
		result = prime * result + ((recepients == null) ? 0 : recepients.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailModule other = (EmailModule) obj;
		if (bcc == null) {
			if (other.bcc != null)
				return false;
		} else if (!bcc.equals(other.bcc))
			return false;
		if (cc == null) {
			if (other.cc != null)
				return false;
		} else if (!cc.equals(other.cc))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (emailSubject == null) {
			if (other.emailSubject != null)
				return false;
		} else if (!emailSubject.equals(other.emailSubject))
			return false;
		if (moduleKey == null) {
			if (other.moduleKey != null)
				return false;
		} else if (!moduleKey.equals(other.moduleKey))
			return false;
		if (moduleParam == null) {
			if (other.moduleParam != null)
				return false;
		} else if (!moduleParam.equals(other.moduleParam))
			return false;
		if (recepients == null) {
			if (other.recepients != null)
				return false;
		} else if (!recepients.equals(other.recepients))
			return false;
		return true;
	}
	
	

}
