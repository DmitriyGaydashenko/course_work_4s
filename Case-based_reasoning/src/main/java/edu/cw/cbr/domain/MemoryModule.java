package edu.cw.cbr.domain;

// Generated 27.04.2013 1:42:58 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * MemoryModule generated by hbm2java
 */
@Entity
@Table(name = "memorymodule", schema = "public")
public class MemoryModule{

	private int hardwareComponentId;
	private HardwareComponent hardwareComponent;
	private float transferRate;
	private float amountOfMemory;

	public MemoryModule() {
	}

	public MemoryModule(HardwareComponent hardwareComponent,
			float transferRate, float amountOfMemory) {
		this.hardwareComponent = hardwareComponent;
		this.transferRate = transferRate;
		this.amountOfMemory = amountOfMemory;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "hardwareComponent"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "hardware_component_id", unique = true, nullable = false)
	public int getHardwareComponentId() {
		return this.hardwareComponentId;
	}

	public void setHardwareComponentId(int hardwareComponentId) {
		this.hardwareComponentId = hardwareComponentId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public HardwareComponent getHardwarecomponent() {
		return this.hardwareComponent;
	}

	public void setHardwarecomponent(HardwareComponent hardwareComponent) {
		this.hardwareComponent = hardwareComponent;
	}

	@Column(name = "transfer_rate", nullable = false, precision = 8, scale = 8)
	public float getTransferRate() {
		return this.transferRate;
	}

	public void setTransferRate(float transferRate) {
		this.transferRate = transferRate;
	}

	@Column(name = "amount_of_memory", nullable = false, precision = 8, scale = 8)
	public float getAmountOfMemory() {
		return this.amountOfMemory;
	}

	public void setAmountOfMemory(float amountOfMemory) {
		this.amountOfMemory = amountOfMemory;
	}

}