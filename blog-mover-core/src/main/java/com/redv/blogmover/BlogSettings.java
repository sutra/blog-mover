package com.redv.blogmover;

public class BlogSettings {
	private Boolean isSaveImage = false;
	public Boolean getIsSaveImage() {
		return isSaveImage;
	}

	public void setIsSaveImage(Boolean isSaveImage) {
		this.isSaveImage = isSaveImage;
	}

	private String imageSavePath = "";
	private String imageUrlPrefix = "";
	
	/**
	 * 获取图片URL地址前缀
	 * @return
	 */
	public String getImageUrlPrefix() {
		return imageUrlPrefix;
	}

	/**
	 * 设置图片URL地址前缀
	 * @param imageUrlPrefix
	 */
	public void setImageUrlPrefix(String imageUrlPrefix) {
		this.imageUrlPrefix = imageUrlPrefix;
	}

	/**
	 * 获取图片存放路径
	 * @return 图片存放路径
	 */
	public String getImageSavePath() {
		return imageSavePath;
	}

	/**
	 * 设置图片存放路径
	 * @param imageSavePath
	 */
	public void setImageSavePath(String imageSavePath) {
		this.imageSavePath = imageSavePath;
	}
}
