package com.cst.siziqi;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window_GameState extends JFrame
{
	 JLabel help = new JLabel(new ImageIcon("./img/Game_State.jpg"));
     public Window_GameState()
     {
    	 init();
    	 this.setLayout(null);
    	 this.setSize(718, 647);
    	 
		 //把程序放在屏幕中间
		 Dimension  frameSize = this.getSize();
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   
		 int center_X = screenSize.width/2;
		 int center_Y = screenSize.height/2;	    
		 this.setLocation(center_X - frameSize.width/2,center_Y-frameSize.height/2 );
    	
    	 this.setVisible(true);
    	 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	 
     }
     public void init()
     {
    	 help.setBounds(0, 0, 700, 600);
    	 this.add(help);
     }
     
}
