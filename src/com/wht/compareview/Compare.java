package com.wht.compareview;

public class Compare {

	public String getLeftText() {
		return leftText;
	}
	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}
	public String getRightText() {
		return rightText;
	}
	public void setRightText(String rightText) {
		this.rightText = rightText;
	}
	public String getCenterText() {
		return centerText;
	}
	public void setCenterText(String centerText) {
		this.centerText = centerText;
	}
	private String leftText;
	private String rightText;
	private String centerText;
	private float Max;
	public float getMax() {
		return Max;
	}
	public void setMax(float max) {
		Max = max;
	}

}
