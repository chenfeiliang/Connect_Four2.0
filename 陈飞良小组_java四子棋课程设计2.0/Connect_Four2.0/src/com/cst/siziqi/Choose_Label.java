package com.cst.siziqi;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Jlable��װ��
 */
public class Choose_Label extends JLabel {
	private int xx;  
	private int yy;
	private int Label_Status;
	
	public int getLabel_Status() {
		return Label_Status;
	}

	public void setLabel_Status(int label_Status) {
		Label_Status = label_Status;
	}

	public int getXX() {
		return xx;
	}

	public void moveXX(int xx) {
		this.xx = this.xx + xx;
	}
	
	public void setXX( int xx)
	{
		this.xx = xx;	
	}
	
	public void setYY( int yy)
	{
		this.yy = yy;	
	}

	public int getYY() {
		return yy;
	}

	public void moveYY(int yy) {
		this.yy = this.yy + yy;
	}

	public Choose_Label() {
		this.xx = 0;  //�߿��ʼ�����м�
		this.yy = 0;
		this.setIcon(new ImageIcon("./img/choose.png"));
	}
	/**
	 * �Ա߿����³�ʼ��
	 *
	 */
	public void returnMyLabel() {
		this.xx = 0;
		this.yy = 0;
	}
	
}
