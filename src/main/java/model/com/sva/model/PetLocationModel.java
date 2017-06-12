package com.sva.model;

public class PetLocationModel {
	
	private double x;
	
	private double y;
	
	private int mapId;
	
	private String petTypes;
	
	private int count;//宠物个数
	
	private int status;//宠物状态
	
	private double actualPositionX;
	
	private double actualPositionY;
	
	private double probability;//宠物捕捉概率
	
	private double viewRange;//宠物查看范围内
	
	private double captureRange;//宠物捕捉范围
	
	private long petRefreshTime;
	
	private int  petId;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public long getPetRefreshTime() {
		return petRefreshTime;
	}

	public void setPetRefreshTime(long petRefreshTime) {
		this.petRefreshTime = petRefreshTime;
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

	public double getActualPositionX() {
		return actualPositionX;
	}

	public void setActualPositionX(double actualPositionX) {
		this.actualPositionX = actualPositionX;
	}

	public double getActualPositionY() {
		return actualPositionY;
	}

	public void setActualPositionY(double actualPositionY) {
		this.actualPositionY = actualPositionY;
	}


	public String getPetTypes() {
        return petTypes;
    }

    public void setPetTypes(String petTypes) {
        this.petTypes = petTypes;
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

	public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
