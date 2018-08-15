package com.cst.siziqi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/*
* 主程序类	
*/

public class Game extends JFrame implements KeyListener, ActionListener,MouseListener
{
	//组件声明
	private final int N = 4;
	private JPanel mainPanel = null;
	private JLabel[][] computer = null;
	private JLabel[][] player = null;
	private Position[][] pos = null;
	private Choose_Label choose;

    //时间
	private JLabel time;// 显示 时间
	private Timer timer;
	private int mnt = 0, scd = 0;// 分、秒

	private JMenuBar menuBar;
	private JMenu menuE, menuH,menuF,menuG;
	private JMenuItem menuItemCZ;
	private JMenuItem Game_State;
	private JMenuItem Game1100;
	private JMenuItem Game800;
	private JMenuItem Before;
	private JMenuItem After;

	/*
	 * 初始化主视窗
	 */
	private void InitLabel()
	{
		Container con = this.getContentPane();
		con.setLayout(null); // 清空布局管理器，即不使用FlowLayout布局，便于使用setBound固定位置

		// 主面板（棋盘）
		this.mainPanel = new MainPanel();
		this.mainPanel.setBounds(0, 0, 1100, 800);
		this.mainPanel.setLayout(null);
		mainPanel.addMouseListener(this);
		con.add(this.mainPanel);


		/**
		 * 初始化所有点 给每个点上都放置一个玩家子、一个电脑子，设置棋子为不可见
		 */
		this.pos = new Position[7][6];
		this.player = new JLabel[7][6];
		this.computer = new JLabel[7][6];
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				pos[i][j] = new Position();
				pos[i][j].setX(j);
				pos[i][j].setY(i);
				pos[i][j].setLabel_Status(0);// 是否已经有棋子 此处初始为无棋子

				this.player[i][j] = new JLabel(new ImageIcon("./img/player.png"));
				this.player[i][j].setBounds(375 + 100 * i, 115 + 100 * j, 100, 100); // x轴上每个棋子相差36，边框相差63，y轴上每个棋子相差36，边框相差64。
				
				this.player[i][j].setVisible(false);// 不可见
				this.mainPanel.add(this.player[i][j]);

				this.computer[i][j] = new JLabel(new ImageIcon("./img/computer.png"));
				this.computer[i][j].setBounds(375 + 100 * i, 115 + 100 * j, 100, 100);
				this.computer[i][j].setVisible(false);
				this.mainPanel.add(this.computer[i][j]);

			}
		}

		// 添加选择列的控件 (确定要放棋子的位置)
		this.choose = new Choose_Label();	
		this.choose.setBounds(385, 35, 80, 80);
		this.mainPanel.add(this.choose);
		this.addKeyListener((KeyListener) this);
		

	}

	/**
	 * 添加time监听 对时间 进行运算
	 *
	 */
	private void initTime()
	{
		ActionListener keepTime = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				scd++;
				/**
				 * 对时间进行计算 scd等于60秒时清零 mnt加1
				 */
				if (scd >= 60)
				{
					scd = 0;
					mnt++;
				}
				String str = "";
				if (mnt < 10)
				{
					str = "0" + mnt + ":";
				} else
				{
					str = mnt + ":";
				}
				if (scd < 10)
				{
					str = str + "0" + scd;
				} else
				{
					str = str + scd;
				}
				time.setText(str);
			}
		};
		this.timer = new Timer(1000, keepTime);
		
		this.time = new JLabel("00:00");
		
		this.time.setForeground(Color.RED);
		
		Font font = new Font(Font.DIALOG, Font.PLAIN, 25);
		
		this.time.setBounds(590, 749, 80, 25);
		this.time.setFont(font);
		this.setPreferredSize(new Dimension (100,100));
		mainPanel.add(this.time);	
		
		this.timer.start();
	}

	// 菜单初始化
	private void initMenu()
	{
		this.menuBar = new JMenuBar();
		this.menuE = new JMenu("菜单(E)");
		this.menuH = new JMenu("帮助(H)");
		this.menuF = new JMenu("游戏分辨率(F)");
		this.menuG = new JMenu("下棋顺序(G)");

		this.menuItemCZ = new JMenuItem("重置");
		
		this.Game_State = new JMenuItem("游戏说明");
	    this.Game1100  =  new JMenuItem("1100*800");
	    this.Game800  =  new JMenuItem("800*600");
	    this.Before = new JMenuItem("先手");
	    this.After = new JMenuItem("后手");

		/*
		 * getKeyStroke(int keyCode, int modifiers) 在给出一个数字键代码和一组修饰符的情况下，返回
		 * KeyStroke 的一个共享实例。
		 */
		this.menuItemCZ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

		this.menuItemCZ.addActionListener(this); // 本身实现了监听器的接口
		this.Game_State.addActionListener(this);
		this.Game1100.addActionListener(this);
		this.Game800.addActionListener(this);
		this.Before.addActionListener(this);
		this.After.addActionListener(this);
  
		this.menuF.add(Game1100);
		this.menuF.add(Game800);
		this.menuH.add(Game_State);
		this.menuE.add(this.menuItemCZ);
		this.menuG.add(Before);
		this.menuG.add(After);
		this.menuBar.add(this.menuE);
		this.menuBar.add(this.menuH);
		this.menuBar.add(menuF);
		this.menuBar.add(menuG);
		
		setJMenuBar(this.menuBar);
	}

	/**
	 * 当从键盘得到响应时改变边框位置
	 *
	 */
	private void setchoose()   //385, 35, 80, 80
	{
		this.choose.setLocation(385 + 100 * this.choose.getXX(), 35 + 100 * this.choose.getYY());
	}

	/**
	 * 重新初始化，隐藏所有棋子，所有位置状态清零，边框回到原来位置
	 *
	 */
	private void initAll()
	{
		this.mnt = 0;
		this.scd = 0;
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				pos[i][j].setLabel_Status(0);
				this.player[i][j].setVisible(false);
				this.computer[i][j].setVisible(false);
			}
		}
		this.choose.setBounds(385, 35, 80, 80);
		this.choose.returnMyLabel(); // 边框初始化
		this.timer.start();// 重新开始计时
	}

	/**
	 * 胜利显示
	 *
	 */
	private void showOver(int judge)
	{
		this.timer.stop();
        String use_time = this.time.getText();
		
		if (judge == 1)
		{
			JOptionPane.showMessageDialog(this, "       玩家获胜!\n本局用时 : "+use_time);
			int flag = JOptionPane.showConfirmDialog(this, "再来一局吧!", "消息", JOptionPane.YES_NO_OPTION);
			if (flag == JOptionPane.YES_OPTION)
			{
				this.initAll();
			}
			else
			{
				System.exit(0);
			}
			
		} 
		else if (judge == 2)
		{
			JOptionPane.showMessageDialog(this, "       电脑获胜!\n本局用时 : "+use_time);
			int flag = JOptionPane.showConfirmDialog(this, "再来一局吧!", "消息", JOptionPane.YES_NO_OPTION);
			if (flag == JOptionPane.YES_OPTION)
			{
				this.initAll();
			} 
			else
			{
				System.exit(0);
			}
		}
		else if(judge == 0)
		{
			JOptionPane.showMessageDialog(this,"       平局!\n本局用时 : "+use_time);
			int flag = JOptionPane.showConfirmDialog(this, "再来一局吧!", "消息", JOptionPane.YES_NO_OPTION);
			if (flag == JOptionPane.YES_OPTION)
			{
				this.initAll();
			} 
			else
			{
				System.exit(0);
			}
		}

	}

	public boolean is_Four(int x ,int y , int flag)
	{
		//#region		
		boolean temp = false;

		int computer_staNum = 0;
		int player_staNum = 0;
		
		if (flag == 1)
		{
			// start
			/**
			 * 向右下方检查是否形成四连子↘
			 */
			for (int i = x - 3, j = y - 3; i < x + 4; i++, j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("↘player_staNum = " + player_staNum);
						if (player_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						player_staNum = 0;
					}
				}
				else
				{
					player_staNum = 0;
				}
			}
			/**
			 * 向右方检查是否形成四连子→   i表示x轴
			 */
			for (int i = x - 3, j = y; i < x + 4; i++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("→player_staNum = " + player_staNum);
						if (player_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						player_staNum = 0;
					}
				} 
				else
				{
					player_staNum = 0;
				}
			}
			/**
			 * 向右上方检查是否形成四连子↗
			 */
			for (int i = x - 3, j = y + 3; i < x + 4; i++, j--)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("↗player_staNum = " + player_staNum);
						if (player_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						player_staNum = 0;
					}
				} 
				else
				{
					player_staNum = 0;
				}
			}
			/**
			 * 向下方检查是否形成四连子↓
			 */
			for (int i = x, j = y - 3; j < y + 4; j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("↓player_staNum = " + player_staNum);
						if (player_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						player_staNum = 0;
					}
				} 
				else
				{
					player_staNum = 0;
				}
			}
		}

		if (flag == 2)
		{
			/**
			 * 向右下方检查是否形成四连子↘
			 */
			for (int i = x - 3, j = y - 3; i < x + 4; i++, j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("↘computer_staNum = " + computer_staNum);
						if (computer_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						computer_staNum = 0;
					}
				} 
				else
				{
					computer_staNum = 0;
				}
			}
			/**
			 * 向右方检查是否形成四连子→ i 表示 列
			 */
			for (int i = x - 3, j = y; i < x + 4; i++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("→computer_staNum = " + computer_staNum);
						if (computer_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						computer_staNum = 0;
					}
				} 
				else
				{
					computer_staNum = 0;
				}
			}
			/**
			 * 向右上方检查是否形成四连子↗
			 */
			for (int i = x - 3, j = y + 3; i < x + 4; i++, j--)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("↗computer_staNum = " + computer_staNum);
						if (computer_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						computer_staNum = 0;
					}
				} 
				else
				{
					computer_staNum = 0;
				}
			}
			/**
			 * 向下方检查是否形成四连子↓
			 */
			for (int i = x, j = y - 3; j < y + 4; j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("↓computer_staNum = " + computer_staNum);
						if (computer_staNum == N)
						{
							temp = true;
						}
					} 
					else
					{
						computer_staNum = 0;
					}
				} 
				else
				{
					computer_staNum = 0;
				}
			}
			// end
		}
		
        return temp;
    	//#endregion
	}

	/**
	 * 判断游戏是否结束
	 * 
	 * @param x
	 *            当前落子x坐标
	 * @param y
	 *            当前落子y坐标
	 */
	private void gameOver(int x, int y)
	{
		System.out.println("\n" + "gameOver" + "\n");

//		int computer_staNum = 0;
//		int player_staNum = 0;
		if (this.choose.getLabel_Status() == 1)
		{
            if(is_Four(x,y,1))
            {
            	System.out.println("\n" + "gameOver" + "\n");
            	this.showOver(1);
            }
		}

		if (this.choose.getLabel_Status() == 2)
		{
            if(is_Four(x,y,2))
            {
            	System.out.println("\n" + "gameOver" + "\n");
            	this.showOver(2);
            }
		}
		System.out.println("\n" + "gameOver" + "\n");
	}

	public Game()
		{
		
			initMenu();
			InitLabel();
			initTime();
			this.setVisible(true);

			this.setSize(1118, 872);
			
			//把程序放在屏幕中间
		    Dimension  frameSize = this.getSize();
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   
		    int center_X = screenSize.width/2;
		    int center_Y = screenSize.height/2;	    
		    this.setLocation(center_X - frameSize.width/2,center_Y-frameSize.height/2 );
			
			
			this.setTitle("四子棋");
		
			this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		}

	//玩家使用键盘下子
	public void keyPressed(KeyEvent e) 
	{
		// 设置边框移动
		
		switch (e.getKeyCode())
			{
			case KeyEvent.VK_W:
				if (this.choose.getYY() >0)
				{
					this.choose.moveYY(-1);
					this.setchoose();
				}
				break;
			case KeyEvent.VK_S:
				if (this.choose.getYY() < 5)
				{
					this.choose.moveYY(1);
					this.setchoose();
				}
				break;
			case KeyEvent.VK_A:
				if (this.choose.getXX() > 0)
				{
					this.choose.moveXX(-1);
					this.setchoose();
				}
				break;
			case KeyEvent.VK_D:
				if (this.choose.getXX() < 6)
				{
					this.choose.moveXX(1);
					this.setchoose();
				}
				break;
			case KeyEvent.VK_SPACE:
				
				for (int j = 5; j >= 0; j--)
				{ 
					if (this.pos[this.choose.getXX()][j].getLabel_Status() == 0 && this.choose.getXX() < 7&& this.choose.getXX() >= 0)
					{
						this.player[this.choose.getXX()][j].setVisible(true);
						this.choose.setLocation(385 + 100 * this.choose.getXX(), 35);
						this.repaint();
						this.pos[this.choose.getXX()][j].setLabel_Status(1);
						this.gameOver(this.choose.getXX(), j);
						this.choose.setLabel_Status(2		);
						ComputerMove();
						break;
					}
				}
								
				break;
			default:
				break;
			}
	}

	//玩家使用鼠标下子
	public void mouseClicked(MouseEvent e)
	{
		int x =(e.getX()-375)/100 ;
				
		for (int j = 5; j >= 0; j--)
		{ 
			if (this.pos[x][j].getLabel_Status() == 0 && x < 7&& x >= 0)
			{		
				this.player[x][j].setVisible(true);
				this.choose.setLocation(385 + 100 * x, 35);
				this.repaint();
				this.pos[x][j].setLabel_Status(1);
				this.gameOver(x, j);
				this.choose.setLabel_Status(2);
				ComputerMove();
				break;
			}
		}
			
		System.out.println(e.getX() + "," +e.getY() );
			
	}
	
	//电脑下子
	public void ComputerMove() 
	{
//		int computer_staNum = 0;
		
//		int player_staNum = 0;
		
//		boolean flag = false;
		int x = 0;
		int y = 0;
		double max_x[] = new double[7]; // 初始化，每个数据应该都为0 记录每列电脑子各方向连子最多值。
		
		//将每列电脑子各方向连子最多值初始为较小数
		for(int i = 0; i<max_x.length ; i++)
		{
			max_x[i] = -10;
		}
		
		int _y[] = new int[7]; // 记录每列可放数据的行号
		
		//将每个元素赋值为-1，即初始化为每个位置都不可放，后面再找出每列可放数据的行号，存进去 其中 i 表示列号，元素为行号
		for(int i = 0 ; i<_y.length ; i++)
		{
			_y[i] = -1;
		}
		
		// #region //找出每列可放数据的行号 并记录 ，同时找出各列放电脑子后可成最大连子数
		for (int r = 0; r <= 6; r++)
		{
			// 找出每列可放数据的行号 并记录
			for (int c = 5; c >= 0; c--)
			{
				if (this.pos[r][c].getLabel_Status() == 0)
				{
					_y[r] = c;
					break;
				} // if后括号
			}

			// #region计算电脑的各个方向连子数
	        if(_y[r]!=-1)
	        {
			this.pos[r][_y[r]].setLabel_Status(2);
			System.out.println("计算电脑的各个方向连子数 : ");

			int you_xia = 0;
			int you = 0;
			int xia = 0;
			int you_shang = 0;
			x = r; 
			y = _y[r]; 
			/**
			 * 向右下方检查是否形成四连子↘
			 */
			for (int i = x - 3, j = y - 3, n = 0; i < x + 4; i++, j++) // 找位置主要是由i
																		// j
																		// 的增减方式决定
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						n++;
						if (n > you_xia)
						{
							you_xia = n;
							System.out.println("↘you_xia_staNum = " + you_xia);
						}
					} 
					else 
					{
						n = 0;
					}
				}
				else
				{
					n = 0;
				}
			}

			/**
			 * 向右方检查 →
			 */
			for (int i = x - 3, j = y, n = 0; i < x + 4; i++) // 第 i 列 ?
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						n++;
						if (n > you)
						{
							you = n;
							System.out.println("→you_staNum = " + you);
						}
					} 
					else 
					{
						n = 0;
					}
				}
				else
				{
					n = 0;
				}
			}

			/**
			 * 向右上方检查 ↗
			 */
			for (int i = x - 3, j = y + 3, n = 0; i < x + 4; i++, j--)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						n++;
						if (n > you_shang)
						{
							you_shang = n;
							System.out.println("↗you_shang_staNum = " + you_shang);
						}
					}
					else 
					{
						n = 0;
					}
				}
				else
				{
					n = 0;
				}
			}

			/**
			 * 向下方检查 ↓
			 */
			for (int i = x, j = y - 3, n = 0; j < y + 4; j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						n++;
						if (n > xia)
						{
							xia = n;
							System.out.println(" ↓xia_staNum = " + xia);
						}
					} 
					else 
					{
						n = 0;
					}
				}
				else
				{
					n = 0;
				}
			}
			max_x[r] = Math.max(Math.max(you, you_xia), Math.max(xia, you_shang)); // 求各个方向上连接棋子最多的个数
			System.out.println("计算电脑的各个方向连子数\n");
			this.pos[r][_y[r]].setLabel_Status(0);
		
           }
	       // #endregion
	        
		} // r后括号
			// #endregion

		// #region判断是否平局
		boolean ping_flag = true;
		for(int i = 0 ; i < _y.length ; i++)
		{
			if(_y[i] != -1)
			{
				ping_flag =false;
			}
		}
		if(ping_flag == true) //如果无位置可放
		{
			this.showOver(0);
		}
		//#endregion
		
		System.out.println("测试数据1");
		
		// 测试数据
		System.out.println("max_x[r]:");
		for (int i = 0; i < max_x.length; i++)
		{
			System.out.println(max_x[i]);
		}
		System.out.println("max_x[r]");

		System.out.println("测试数据1\n");
		
		// 设立各种情况的判定标志
		boolean flag1 = false;
		boolean flag2 = false;
//		boolean flag3 = false;
//		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;

		//#region   1.假设该子为电脑子，判断能连成四子
           flag1 = computer_four(_y);
		// #endregion
		
        //#region   2.假设该子为玩家子，判断看是否存在，玩家4子相连，即不算此位置玩家将四子的情况，有则拦截
		if (flag1 == false)
		{
			flag2 = player_four(_y);
		}
		// #endregion
  
		//#region   4.排除连子最多，但是到顶端也无法组成4个连子的	优先级为0	    
		    
		    useless_chess ( _y,max_x);
		    
		    
		//#endregion
		
		//#region   5.如果电脑放一子下一步 电脑能同时形成2种方法成四子则拦截 ，则设置max_x[x] = 10,即优先级为 4
		if( flag1 == false && flag2 == false )
		  {
                  flag5 = double_head(_y,max_x);
		  }

		//#endregion
		
		//#region   6.如果玩家放一子下一步玩家能同时形成2种方法成四子则拦截 ，则设置max_x[x] = 10,即优先级为 10
		if (flag1 == false && flag2 == false )
			{
			     flag6 = double_three ( _y,max_x);
			}
		//#endregion  
	   	
        //#region   8.排除垫脚石的状态, 如果电脑下此位置，玩家子再在此位置下一子，可有两种方法成四子，则该位置优先级为-8 （暂未设计）
		          
     	//#endregion
		
        //#region   3.排除垫脚石的状态, 如果此位置上一位置是玩家子，玩家子可成四子，则该位置优先级为-9
		    stepping_stone(_y,max_x);	
     	//#endregion
		
	    // #region  7.普通攻击，电脑下子后，连子最多的位置优先		
		System.out.println("flag1 :"+flag1);
		System.out.println("flag2 :"+flag2);
		System.out.println("flag5 :"+flag5);
		System.out.println("flag6 :"+flag6);
				
		if ( flag1 == false  && flag2 == false)	
		{
			normal_attack (_y,max_x);
		}
		// #endregion
	}
	
	//1.假设该子为电脑子，判断能连成四子
	public boolean computer_four(int _y[])
	{
		int x = 0 ;
		int y = 0 ;
		boolean flag1 = false;
		
	    System.out.println("步骤1 :");
		for (int x1 = 0; x1 < 7; x1++)
		{
            if(_y[x1]!=-1)
            {
				x = x1;
				y = _y[x1];
				
				this.pos[x][y].setLabel_Status(2);
	            
				flag1 = is_Four(x,y,2);
				// 如果能连成四子，则落在此处
				if ( flag1 == true)
				{
					this.pos[x][y].setLabel_Status(2);
					this.computer[x][y].setVisible(true);
					this.repaint();
					this.showOver(2);
					break;	
				} 
				else
				{
					this.pos[x][y].setLabel_Status(0);
				}
            }
            else // _y[x1] = -1
            {
               continue;	
            }
		}
		
		return flag1;

	}
    
	//2.假设该子为玩家子，判断看是否存在，玩家4子相连，即不算此位置玩家将四子的情况，有则拦截
	public boolean player_four(int _y[])
	{
		int x = 0 ;
		int y = 0 ;
		boolean flag2 = false;
		
		System.out.println("步骤2 :");
		for (int x2 = 0; x2 < 7; x2++)
		{ 
			if(_y[x2]!=-1)
			{
				x = x2;
				y = _y[x2];
				this.pos[x][y].setLabel_Status(1);
                      
                flag2 = is_Four(x,y,1) ;
                
				if (flag2 == true)
				{
					this.pos[x][y].setLabel_Status(2);
					this.computer[x][y].setVisible(true);
					this.repaint();
					this.gameOver(x, y);
					this.choose.setLabel_Status(1);
					
					//判断是否平局
					boolean ping_flag = true;
					
					for (int r2 = 0; r2 <= 6; r2++)
					{
						for (int c = 5; c >= 0; c--)
						{
							if (this.pos[r2][c].getLabel_Status() == 0)
							{
								ping_flag = false;
								break;
							} // if后括号
						}
					}
					
					if(ping_flag == true)
					{
						this.showOver(0);
					}
					this.validate();
								
					break;
				} 
				else
				{
					this.pos[x][y].setLabel_Status(0);
				}
		    }
			else // _y[x2] = -1
			{
				continue;
			}
		}		
		return flag2;
	}
   
	//3.排除垫脚石的状态, 如果此位置上一位置是玩家子，玩家子可成四子，则该位置不能下
	public void stepping_stone(int _y[],double max_x[])
	{
		  int x = 0 ;
		  int y = 0 ;
		  boolean flag3 = false;
		  
	      System.out.println("步骤3 ：");
		  for(int x3 = 0 ; x3 < 7; x3++ )
		  {    						
			    flag3 =false;
			    if(_y[x3] - 1 >= 0)
			    {
			      x = x3;
			      y = _y[x3] -1 ;
			     int temp = this.pos[x][y].getLabel_Status();
			     
				 this.pos[x][y].setLabel_Status(1);				
		
                 flag3 = is_Four(x , y , 1) ;
				
				 this.pos[x][y].setLabel_Status(temp);
				 
				 if(flag3 == true)
				 {
					 max_x[x3] = -9;
				 }
				 
			    }
			    else
			    {
			    	continue;
			    }
		  }
		  	
			// 测试数据
		   System.out.println("测试数据2");
		  
			System.out.println("max_x[r]:");
			for (int i = 0; i < max_x.length; i++)
			{
				System.out.println(max_x[i]);
			}
			System.out.println("max_x[r]");

			System.out.println("测试数据2\n");
		  
	System.out.println("步骤3结束 :");
	}
   
	// 4.排除连子最多，但是到顶端也无法组成4个连子的
	public void useless_chess(int _y[] , double max_x[])
	{
	    int x = 0 ;
		int y = 0 ;
		
	    System.out.println("步骤4 :");
	    
		for (int x4 = 0; x4 < 7; x4++)
		{
			int you = 0 ;
			int you_shang = 0 ;
		    int you_xia = 0 ;
		    		        
            if(_y[x4] !=-1 && max_x[x4] >0)
            {		 
	    			x = x4;
	    			y = _y[x4];
	    			int temp = this.pos[x][y].getLabel_Status();
	    		    this.pos[x][y].setLabel_Status(2);	
	    		    
				/**
				 * 向右下方检查是否形成四连子↘
				 */
				for (int i = x - 3, j = y - 3; i < x + 4; i++, j++)
				{
					if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
					{
						if (this.pos[i][j].getLabel_Status() == 2)
						{
							you_xia++;
						} 
						else if(this.pos[i][j].getLabel_Status() == 1)
						{
							you_xia = 0;
						}
					} 
					else
					{
						you_xia = 0;
					}
				}
				/**
				 * 向右方检查是否形成四连子→
				 */
				for (int i = x - 3, j = y; i < x + 4; i++)
				{
					if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
					{
						if (this.pos[i][j].getLabel_Status() == 2)
						{
							you++;
						}
						else if(this.pos[i][j].getLabel_Status() == 1)
						{
							you = 0;
						}
					}
					else
					{
						you = 0;
					}
				}
				/**
				 * 向右上方检查是否形成四连子↗
				 */
				for (int i = x - 3, j = y + 3; i < x + 4; i++, j--)
				{
					if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
					{
						if (this.pos[i][j].getLabel_Status() == 2)
						{
							you_shang++;
						} 
						else if(this.pos[i][j].getLabel_Status() == 1)
						{
							you_shang = 0;
						}
					}
					else
					{
						you_shang = 0;
					}
					        
				}
				
			    this.pos[x][y].setLabel_Status(temp);  //恢复标志
			    
			    if(_y[x]-3<0  && ( you !=max_x[x] && you_shang!=max_x[x] && you_xia != max_x[x]))
			    {
	                max_x[x] = 0 ;
	                continue;
			    }
		    
            }
            else // _y[x1] = -1
            {
               continue;	
            }  
		}
		
		// 测试数据
		   System.out.println("测试数据3");
		  
			System.out.println("max_x[r]:");
			for (int i = 0; i < max_x.length; i++)
			{
				System.out.println(max_x[i]);
			}
			System.out.println("max_x[r]");

			System.out.println("测试数据3\n");
		
		System.out.println("步骤4结束 :");
	}
 
	//5.如果电脑放一子下一步 电脑能同时形成2种方法成四子则拦截 ，则设置max_x[x] = 10,即优先级为 4
	public boolean double_head(int _y[],double max_x[])
	{
		
		
		  int x = 0 ;
		  int y = 0 ;
//		  int player_staNum = 0;
		  boolean flag5= false;
		   boolean flag= false;
		  System.out.println("步骤5  :");
			for (int x5 = 0; x5 < 7; x5++)
			{ 
				if(_y[x5]!=-1)
				{
				    flag= false;
					x = x5;
					y = _y[x5];
					int n = 0;		  
					int temp = this.pos[x][y].getLabel_Status();
				    this.pos[x][y].setLabel_Status(2);

				    
					int _z[] = new int[7]; // 记录每列可放数据的行号
					
					//将每个元素赋值为-1，即初始化为每个位置都不可放，后面再找出每列可放数据的行号，存进去 其中 i 表示列号，元素为行号
					for(int i = 0 ; i<_y.length ; i++)
					{
						_z[i] = -1;
					}
					
					// #region //找出每列可放数据的行号 并记录 ，同时找出各列放电脑子后可成最大连子数
					for (int r = 0; r <= 6; r++)
					{
						// 找出每列可放数据的行号 并记录
						for (int c = 5; c >= 0; c--)
						{
							if (this.pos[r][c].getLabel_Status() == 0)
							{
								_z[r] = c;
								break;
							} // if后括号
						}
					}
	                 		    
					//判断该三能否在下一步有效地实现4连子
					for (int x2 = 0; x2 < 7; x2++)
					{ 		
						if(_z[x2]!=-1)
						{
							int x0 = x2;
							int y0 = _z[x2];
							int temp2 = this.pos[x0][y0].getLabel_Status();
							this.pos[x0][y0].setLabel_Status(2);
								
							if (is_Four(x0,y0,2))
							{
									n++;
							} 
							
							this.pos[x0][y0].setLabel_Status(temp2);

						}
						else // _y[x2] = -1
						{
							continue;
						}
					}
						
					if(n>=2)
					{
						flag = true;
						flag5 = true;
					}
							
					if (flag == true)
					{
						max_x[x] = 4;			 
					} 

					this.pos[x][y].setLabel_Status(temp);	
				}
				else // _y[x2] = -1
				{
					continue;
				}
			}
		  
		  
		  System.out.println("步骤5结束");
		  return flag5 ;
	}

	
	// 6.如果玩家放一子下一步玩家能同时形成2种方法成四子则拦截 ，则设置max_x[x] = 10,即优先级为 10
	public boolean double_three (int _y[],double max_x[])
	{
	    int x = 0 ;
        int y = 0 ;
//	    int player_staNum = 0;
	    boolean flag6= false;
	    boolean flag= false;
		System.out.println("步骤6  :");
		for (int x6 = 0; x6 < 7; x6++)
		{ 
			if(_y[x6]!=-1)
			{
			    flag= false;
				x = x6;
				y = _y[x6];
				int n = 0;		  
				int temp = this.pos[x][y].getLabel_Status();
			    this.pos[x][y].setLabel_Status(1);

			    
				int _z[] = new int[7]; // 记录每列可放数据的行号
				
				//将每个元素赋值为-1，即初始化为每个位置都不可放，后面再找出每列可放数据的行号，存进去 其中 i 表示列号，元素为行号
				for(int i = 0 ; i<_y.length ; i++)
				{
					_z[i] = -1;
				}
				
				// #region //找出每列可放数据的行号 并记录 ，同时找出各列放电脑子后可成最大连子数
				for (int r = 0; r <= 6; r++)
				{
					// 找出每列可放数据的行号 并记录
					for (int c = 5; c >= 0; c--)
					{
						if (this.pos[r][c].getLabel_Status() == 0)
						{
							_z[r] = c;
							break;
						} // if后括号
					}
				}
                 
    
			    
				//判断该三能否在下一步有效地实现4连子
				for (int x2 = 0; x2 < 7; x2++)
				{ 		
					if(_z[x2]!=-1)
					{
						int x0 = x2;
						int y0 = _z[x2];
						int temp2 = this.pos[x0][y0].getLabel_Status();
						this.pos[x0][y0].setLabel_Status(1);
							
						if (is_Four(x0,y0,1))
						{
								n++;
						} 
						
						this.pos[x0][y0].setLabel_Status(temp2);

					}
					else // _y[x2] = -1
					{
						continue;
					}
				}
					
				if(n>=2)
				{
					flag = true;
					flag6 = true;
				}
						
				if (flag == true)
				{
					max_x[x] = 10;			 
				} 

				this.pos[x][y].setLabel_Status(temp);	
			}
			else // _y[x2] = -1
			{
				continue;
			}
		}
        System.out.println("步骤6结束");
        return flag6;
	}
	
	// 8.电脑下子垫脚成玩家再下一子 玩家能有两种方法成四子
	public void stepping_stone2(int _y[],double max_x[])
	{

	}
	
	//7.普通攻击，电脑下子后，连子最多的位置优先
	public void normal_attack (int _y[],double max_x[])  
	{
		System.out.println("步骤7 ： ");
		
		// 普通攻击，判断那一列连子最多，就放在哪一列，如果有相同则随机
		double a[] = new double[7];

		// 定义一个数组存连子最多的列号，index_num是实际上 出现同时连子最多 的列号数
		int MaxX_index[] = new int[7];
		int index_num = 0;
		

		for (int i = 0; i < max_x.length; i++)
		{
				 if(i>=2&&i<=4)
				 {
					 max_x[i]+=0.5;
				 }
		}


		// 为了保留max把max_col数组各值赋给a，并通过排序求出七列中连子最多的连子数。
		for (int i = 0; i < max_x.length; i++)
		{
			a[i] = max_x[i];
		}
		Arrays.sort(a);

		double max = a[6]; // max为七列中连子最多的连子数

		// 找出连子数最多的列，存在index数组中
		for (int i = 0; i < max_x.length; i++)
		{
			if (max == max_x[i])
			{
				MaxX_index[index_num] = i;
				index_num++;
			}
		}
		
        System.out.println("测试数据max_x   end : ");
		for (int i = 0; i < max_x.length; i++)
		{
			 System.out.print(max_x[i] + " \n");
		}
		 System.out.println("测试数据max_x   end : ");

		Random r = new Random();
		
		int X = MaxX_index[r.nextInt(index_num)];
	
		System.out.println("random X :" + X);

		System.out.println("X :" + X);
		System.out.println("_y[X]:" + _y[X]);
		
        if(_y[X] != -1)
        {
			this.pos[X][_y[X]].setLabel_Status(2);
			this.computer[X][_y[X]].setVisible(true);
			this.repaint();
			this.gameOver(X, _y[X]);
			// this.user.setLocation(40, 25);
			this.repaint();
			this.choose.setLabel_Status(1);
        }
        
		
		//判断是否平局
		boolean ping_flag = true;
		
		for (int r2 = 0; r2 <= 6; r2++)
		{
			for (int c = 5; c >= 0; c--)
			{
				if (this.pos[r2][c].getLabel_Status() == 0)
				{
					ping_flag = false;
					break;
				} // if后括号
			}
		}
		
		if(ping_flag == true)
		{
			this.showOver(0);
		}
		this.validate();
        
		System.out.println("步骤7结束");
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == menuItemCZ)
		{
			this.initAll();
		}
		
		if(e.getSource() == Game_State)
		{
			new Window_GameState();
		}
		
		if(e.getSource() == Game1100)
		{
			new Game();
			this.dispose();
		}
		
		if(e.getSource() == Game800)
		{
			new Game2();
			this.dispose();
		}
		if(e.getSource() == Before)
		{
			this.initAll();
		}
		if(e.getSource() == After)
		{
			this.initAll();
			ComputerMove();
		}
	}
	
 //#region 多余方法
	
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}
	//#endregion

}