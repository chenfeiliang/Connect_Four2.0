package com.cst.siziqi;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Jpanel��װ��
 * 
 */
public class MainPanel2 extends JPanel {
	
		private ImageIcon welcomImageIcon = null;  //����ͼƬ
		
		public MainPanel2(){		
			this.welcomImageIcon = new ImageIcon("./img/MainPanel2.jpg");	  //����·������һ��ͼ��
		}	
		
	    public void paintComponent(Graphics g) {
	        g.drawImage(this.welcomImageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	    }
	
}