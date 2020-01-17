package com.itheima.domain;

import java.nio.file.Path;

public class Images {
     private String path;//图片保存路径
     private String owner;//上传者
     private String country;//国别
     private String last_time;//最近一次操作时间
     private String resolution;//分辨率*
     private String acquisition_time;//采集时间*
     private String acquisition_long;//采集时长*
     private String satellite;//卫星
     private String longitude_range;//经度*
     private String latitude_range;//纬度*
     private String image_category;//图像分类
 	 private String remark;//备注
 	 
 	 
     public String getLongitude_range() {
		return longitude_range;
	}
	public void setLongitude_range(String longitude_range) {
		this.longitude_range = longitude_range;
	}

	public String getLatitude_range() {
		return latitude_range;
	}

	public void setLatitude_range(String latitude_range) {
		this.latitude_range = latitude_range;
	}

	public String getImage_category() {
		return image_category;
	}

	public void setImage_category(String image_category) {
		this.image_category = image_category;
	} 
     
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getAcquisition_time() {
		return acquisition_time;
	}

	public void setAcquisition_time(String acquisition_time) {
		this.acquisition_time = acquisition_time;
	}

	public String getAcquisition_long() {
		return acquisition_long;
	}

	public void setAcquisition_long(String acquisition_long) {
		this.acquisition_long = acquisition_long;
	}

	public String getSatellite() {
		return satellite;
	}

	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}

	public String getLast_time() {
		return last_time;
	}
     
     public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
     
     
     public String getOwner() {
		return owner;
	}
     
     public String getPath() {
		return path;
	}
     
   
     public void setOwner(String owner) {
		this.owner = owner;
	}
     
     public void setPath(String path) {
		this.path = path;
	}
     
     public Images() {
		setAcquisition_long("");
		setAcquisition_time("");
		setCountry("");
		setImage_category("");
		setLast_time("");
		setLatitude_range("");
		setLongitude_range("");
		setOwner("");
		setPath("");
		setRemark("");
		setResolution("");
		setSatellite("");
	}
     
     public void ToString() {
		System.out.println(getPath()+"\n"+getOwner()+"\n"+getLast_time()+"\n"+getCountry()+"\n"+getAcquisition_time()+"\n"+getAcquisition_long()+"\n"+getLongitude_range()+"\n"+getLatitude_range()+"\n"+getImage_category()+"\n"+getResolution()+"\n"+getSatellite()+"\n"+getRemark());
	}
     
}
