package me.fit.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IPLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iplog_seq")
	private Long id;
	private String ipString;
	private String ipType;
	private Date createdDate;

	public IPLog() {
		super();
	}

	public IPLog(Long id, String ipString, String ipType, Date createdDate) {
		super();
		this.id = id;
		this.ipString = ipString;
		this.ipType = ipType;
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpString() {
		return ipString;
	}

	public void setIpString(String ipString) {
		this.ipString = ipString;
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ipString == null) ? 0 : ipString.hashCode());
		result = prime * result + ((ipType == null) ? 0 : ipType.hashCode());
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
		IPLog other = (IPLog) obj;
		return Objects.equals(createdDate, other.createdDate) && Objects.equals(id, other.id)
				&& Objects.equals(ipString, other.ipString) && Objects.equals(ipType, other.ipType);
	}

	@Override
	public String toString() {
		return "IPLog [id=" + id + ", ipString=" + ipString + ", ipType=" + ipType + ", createdDate=" + createdDate
				+ "]";
	}

}
