package com.cst.siziqi;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Jpanel封装类
 * 
 */
public class MainPanel2 extends JPanel {
	
		private ImageIcon welcomImageIcon = null;  //棋盘图片
		
		public MainPanel2(){		
			this.welcomImageIcon = new ImageIcon("./img/MainPanel2.jpg");	  //根据路径创建一个图标
		}	
		
	    public void paintComponent(Graphics g) {
	        g.drawImage(this.welcomImageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	    }
	
}