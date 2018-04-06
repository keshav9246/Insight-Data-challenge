
// Blueprint for store the Active IP elements in a list and write to the output as their session expire
public class ActiveID  {

	public String ip;
	public String firstAccess;
	public String lastAccess;
	public long activeDuration;
	public long inActiveDuration;
	public long noOfDocAccessed;

	public long getActiveDuration() {
		return activeDuration;
	}
	public void setActiveDuration(long activeDuration) {
		this.activeDuration = activeDuration;
	}

	public long getInActiveDuration() {
		return inActiveDuration;
	}

	public void setInActiveDuration(long inActiveDuration) {
		this.inActiveDuration = inActiveDuration;
	}





	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFirstAccess() {
		return firstAccess;
	}
	public void setFirstAccess(String firstAccess) {
		this.firstAccess = firstAccess;
	}
	public String getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	public long getNoOfDocAccessed() {
		return noOfDocAccessed;
	}
	public void setNoOfDocAccessed(long noOfDocAccessed) {
		this.noOfDocAccessed = noOfDocAccessed;
	}
	



}
