package edu.cw.cbr.domain;

// Generated 27.04.2013 1:42:58 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * HardwareComponentType generated by hbm2java
 */
@Entity
@Table(name = "hardwarecomponenttype", schema = "public")
public class HardwareComponentType{

	private int hardwareComponentTypeId;
	private String hardwareComponentTypeName;
	private Set<HardwareComponentfamily> hardwareComponentfamilies = new HashSet<HardwareComponentfamily>(
			0);

	public HardwareComponentType() {
	}

	public HardwareComponentType(int hardwareComponentTypeId,
			String hardwareComponentTypeName) {
		this.hardwareComponentTypeId = hardwareComponentTypeId;
		this.hardwareComponentTypeName = hardwareComponentTypeName;
	}

	public HardwareComponentType(int hardwareComponentTypeId,
			String hardwareComponentTypeName,
			Set<HardwareComponentfamily> hardwareComponentfamilies) {
		this.hardwareComponentTypeId = hardwareComponentTypeId;
		this.hardwareComponentTypeName = hardwareComponentTypeName;
		this.hardwareComponentfamilies = hardwareComponentfamilies;
	}

	@Id
	@Column(name = "hardware_component_type_id", unique = true, nullable = false)
	public int getHardwareComponentTypeId() {
		return this.hardwareComponentTypeId;
	}

	public void setHardwareComponentTypeId(int hardwareComponentTypeId) {
		this.hardwareComponentTypeId = hardwareComponentTypeId;
	}

	@Column(name = "hardware_component_type_name", nullable = false, length = 256)
	public String getHardwareComponentTypeName() {
		return this.hardwareComponentTypeName;
	}

	public void setHardwareComponentTypeName(String hardwareComponentTypeName) {
		this.hardwareComponentTypeName = hardwareComponentTypeName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hardwarecomponenttype")
	public Set<HardwareComponentfamily> getHardwarecomponentfamilies() {
		return this.hardwareComponentfamilies;
	}

	public void setHardwarecomponentfamilies(
			Set<HardwareComponentfamily> hardwareComponentfamilies) {
		this.hardwareComponentfamilies = hardwareComponentfamilies;
	}

}
