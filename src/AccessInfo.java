

//Blueprint to store the value of each record in a CSV file
public class AccessInfo {

	public String ip;
	public String accessTime;
	public String cik;
	public String accession;
	public String extension;



	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCik() {
		return cik;
	}
	public void setCik(String cik) {
		this.cik = cik;
	}
	public String getAccession() {
		return accession;
	}
	public void setAccession(String accession) {
		this.accession = accession;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	@Override
	public String toString() {
		return "AccessInfo [ip=" + ip + ", accessTime=" + accessTime + ", cik=" + cik + ", accession=" + accession
				+ ", extension=" + extension + "]";
	}

}