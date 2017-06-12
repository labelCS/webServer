package com.sva.model;

public class PetAttributesModel {
	
	
	private double probability;//宠物捕捉概率
	
	private double viewRange;//宠物查看范围内
	
	private double captureRange;//宠物捕捉范围
	
	private String petName;//宠物名称
	
	private String floorNo;
	
    private int mapId;	
	
	private double x;
	
	private double y;
	
	private int count;
	
	private int id;
	
	
	public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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
