package edu.cw.cbr.domain;

// Generated 27.04.2013 1:42:58 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Task generated by hbm2java
 */
@Entity
@Table(name = "task", schema = "public")
public class Task{

	private int taskId;
	private float computationalComplexity;
	private float amountOfMemory;
	private float downloadingAmountOfData;
	private float uploadingAmountOfData;
	private float maxRunningTime;

	public Task() {
	}

	public Task(int taskId, float computationalComplexity,
			float amountOfMemory, float downloadingAmountOfData,
			float uploadingAmountOfData, float maxRunningTime) {
		this.taskId = taskId;
		this.computationalComplexity = computationalComplexity;
		this.amountOfMemory = amountOfMemory;
		this.downloadingAmountOfData = downloadingAmountOfData;
		this.uploadingAmountOfData = uploadingAmountOfData;
		this.maxRunningTime = maxRunningTime;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "task_id", unique = true, nullable = false)
	public int getTaskId() {
		return this.taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	@Column(name = "computational_complexity", nullable = false, precision = 8, scale = 8)
	public float getComputationalComplexity() {
		return this.computationalComplexity;
	}

	public void setComputationalComplexity(float computationalComplexity) {
		this.computationalComplexity = computationalComplexity;
	}

	@Column(name = "amount_of_memory", nullable = false, precision = 8, scale = 8)
	public float getAmountOfMemory() {
		return this.amountOfMemory;
	}

	public void setAmountOfMemory(float amountOfMemory) {
		this.amountOfMemory = amountOfMemory;
	}

	@Column(name = "downloading_amount_of_data", nullable = false, precision = 8, scale = 8)
	public float getDownloadingAmountOfData() {
		return this.downloadingAmountOfData;
	}

	public void setDownloadingAmountOfData(float downloadingAmountOfData) {
		this.downloadingAmountOfData = downloadingAmountOfData;
	}

	@Column(name = "uploading_amount_of_data", nullable = false, precision = 8, scale = 8)
	public float getUploadingAmountOfData() {
		return this.uploadingAmountOfData;
	}

	public void setUploadingAmountOfData(float uploadingAmountOfData) {
		this.uploadingAmountOfData = uploadingAmountOfData;
	}

	@Column(name = "max_running_time", nullable = false, precision = 8, scale = 8)
	public float getMaxRunningTime() {
		return this.maxRunningTime;
	}

	public void setMaxRunningTime(float maxRunningTime) {
		this.maxRunningTime = maxRunningTime;
	}

}
