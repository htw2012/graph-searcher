/*
 * Copyright Lenovo  @ 2015 版权所有
 */
package com.pic.model;

/**
 * <p>(用一句话描述该文件做什么)</p>
 * @author jiahc1
 * @Email  jiahc1@lenovo.com
 * 2015-1-17下午7:43:04
 *
 * @version V1.0  
 */
public class ImageInfo {
    
    private String fileName;
    private String formatType;
    private int imageType;
    private int width;
    private int height;
    private int minx;
    private int miny;
    private int[] pixelArray;
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFormatType() {
        return formatType;
    }
    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }
    public int getImageType() {
        return imageType;
    }
    public void setImageType(int imageType) {
        this.imageType = imageType;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getMinx() {
        return minx;
    }
    public void setMinx(int minx) {
        this.minx = minx;
    }
    public int getMiny() {
        return miny;
    }
    public void setMiny(int miny) {
        this.miny = miny;
    }
    public int[] getPixelArray() {
        return pixelArray;
    }
    public void setPixelArray(int[] pixelArray) {
        this.pixelArray = pixelArray;
    }   

}
