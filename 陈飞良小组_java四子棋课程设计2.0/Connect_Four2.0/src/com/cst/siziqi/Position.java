package com.cst.siziqi;

/*
 * 位置处理类
 */
public class Position{
	private int x;
	private int y;
	private int Label_Status;
	public Position() {
		this.x = 0;
		this.y = 0;
		this.Label_Status = 0;
	}
	public int getLabel_Status() {
		return Label_Status;
	}
	public void setLabel_Status(int Label_Status) {
		this.Label_Status = Label_Status;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}	

	
}