package id.franspratama.geol.core.pojo;


public class AlarmDown {
	
    private int eventId;
    private String group;
    private String alarmName;
    private String node;
    private String siteId;
    private String site;
    private String zone;
    private String ttno;
    private String firstOccurrence;
    private String lastOccurrence;
    private String lastReceived;
    private String firstReceived;
    private String summary;
    private String managerArea;
    private String manager;
    private String gmArea;
    private String managerPhone;
    private String managerEmail;
    private String supervisorArea;
    private String supervisor;
    private String supervisorPhone;
    private String supervisorEmail;
    private long age;
    private String isDead;
    private String towerProvider;
    private String category;
    private String towerId;
    private String priority;
    private boolean isDeadBool;
    private String hmanager;
    private String agingCategory;
    private String ne;
    private String msPartner;
    private String city;
    private String sourcePower;
    private String address;
    private String siteOwner;
    private String siteType;
    private WFMTicketAndWorkOrder ttAndWO;

    public AlarmDown() {
    }


    public WFMTicketAndWorkOrder getTtAndWO() {
		return ttAndWO;
	}



	public void setTtAndWO(WFMTicketAndWorkOrder ttAndWO) {
		this.ttAndWO = ttAndWO;
	}



	public String getCategory() {
        return category;
    }

    public String getTowerId() {
        return towerId;
    }

    public void setTowerId(String towerId) {
        this.towerId = towerId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    
    
    public void setCategory(String category) {
        this.category = category;
    }

    
    
    public String getIsDead() {
        return isDead;
    }

    public void setIsDead(String isDead) {
        this.isDead = isDead;
    }

    public String getTowerProvider() {
        return towerProvider;
    }

    public void setTowerProvider(String towerProvider) {
        this.towerProvider = towerProvider;
    }
   
    
    
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getTtno() {
        return ttno;
    }

    public void setTtno(String ttno) {
        this.ttno = ttno;
    }

    public String getFirstOccurrence() {
        return firstOccurrence;
    }

    public void setFirstOccurrence(String firstOccurrence) {
        this.firstOccurrence = firstOccurrence;
    }

    public String getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(String lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public String getLastReceived() {
        return lastReceived;
    }

    public void setLastReceived(String lastReceived) {
        this.lastReceived = lastReceived;
    }

    public String getFirstReceived() {
        return firstReceived;
    }

    public void setFirstReceived(String firstReceived) {
        this.firstReceived = firstReceived;
    }



    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGmArea() {
        return gmArea;
    }

    public void setGmArea(String gmArea) {
        this.gmArea = gmArea;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisorPhone() {
        return supervisorPhone;
    }

    public void setSupervisorPhone(String supervisorPhone) {
        this.supervisorPhone = supervisorPhone;
    }

    public String getSupervisorEmail() {
        return supervisorEmail;
    }

    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }

    public String getManagerArea() {
        return managerArea;
    }

    public void setManagerArea(String managerArea) {
        this.managerArea = managerArea;
    }

    public String getSupervisorArea() {
        return supervisorArea;
    }

    public void setSupervisorArea(String supervisorArea) {
        this.supervisorArea = supervisorArea;
    }

    public boolean isIsDeadBool() {
        return isDeadBool;
    }

    public void setIsDeadBool(boolean isDeadBool) {
        this.isDeadBool = isDeadBool;
    }

    public String getHmanager() {
        return hmanager;
    }

    public void setHmanager(String hmanager) {
        this.hmanager = hmanager;
    }

    public String getAgingCategory() {
        return agingCategory;
    }

    public void setAgingCategory(String agingCategory) {
        this.agingCategory = agingCategory;
    }

    public String getNe() {
        return ne;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }

	public boolean isDeadBool() {
		return isDeadBool;
	}

	public void setDeadBool(boolean isDeadBool) {
		this.isDeadBool = isDeadBool;
	}

	public String getMsPartner() {
		return msPartner;
	}

	public void setMsPartner(String msPartner) {
		this.msPartner = msPartner;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSourcePower() {
		return sourcePower;
	}

	public void setSourcePower(String sourcePower) {
		this.sourcePower = sourcePower;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSiteOwner() {
		return siteOwner;
	}

	public void setSiteOwner(String siteOwner) {
		this.siteOwner = siteOwner;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public void setAge(long age) {
		this.age = age;
	}
    
    
}
