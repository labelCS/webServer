package com.sva.model;

public class PetAttributesModel {
	
	
	private double probability;//宠物捕捉概率
	
	private double viewRange;//宠物查看范围内
	
	private double captureRange;//宠物捕捉范围
	
	private String petName;//宠物名称
	
	private String captureTime;
	
	private String phoneNumber;
	
	private String floorNo;
	
	private String startTime;
	
	private String endTime;
	
	private double x;
	
	private double y;
	
	private int count;
	
	private int id;
	

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getViewRange() {
		return viewRange;
	}

	public void setViewRange(double viewRange) {
		this.viewRange = viewRange;
	}

	public double getCaptureRange() {
		return captureRange;
	}

	public void setCaptureRange(double captureRange) {
		this.captureRange = captureRange;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
}
