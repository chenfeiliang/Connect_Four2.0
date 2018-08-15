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
* ��������	
*/

public class Game extends JFrame implements KeyListener, ActionListener,MouseListener
{
	//�������
	private final int N = 4;
	private JPanel mainPanel = null;
	private JLabel[][] computer = null;
	private JLabel[][] player = null;
	private Position[][] pos = null;
	private Choose_Label choose;

    //ʱ��
	private JLabel time;// ��ʾ ʱ��
	private Timer timer;
	private int mnt = 0, scd = 0;// �֡���

	private JMenuBar menuBar;
	private JMenu menuE, menuH,menuF,menuG;
	private JMenuItem menuItemCZ;
	private JMenuItem Game_State;
	private JMenuItem Game1100;
	private JMenuItem Game800;
	private JMenuItem Before;
	private JMenuItem After;

	/*
	 * ��ʼ�����Ӵ�
	 */
	private void InitLabel()
	{
		Container con = this.getContentPane();
		con.setLayout(null); // ��ղ��ֹ�����������ʹ��FlowLayout���֣�����ʹ��setBound�̶�λ��

		// ����壨���̣�
		this.mainPanel = new MainPanel();
		this.mainPanel.setBounds(0, 0, 1100, 800);
		this.mainPanel.setLayout(null);
		mainPanel.addMouseListener(this);
		con.add(this.mainPanel);


		/**
		 * ��ʼ�����е� ��ÿ�����϶�����һ������ӡ�һ�������ӣ���������Ϊ���ɼ�
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
				pos[i][j].setLabel_Status(0);// �Ƿ��Ѿ������� �˴���ʼΪ������

				this.player[i][j] = new JLabel(new ImageIcon("./img/player.png"));
				this.player[i][j].setBounds(375 + 100 * i, 115 + 100 * j, 100, 100); // x����ÿ���������36���߿����63��y����ÿ���������36���߿����64��
				
				this.player[i][j].setVisible(false);// ���ɼ�
				this.mainPanel.add(this.player[i][j]);

				this.computer[i][j] = new JLabel(new ImageIcon("./img/computer.png"));
				this.computer[i][j].setBounds(375 + 100 * i, 115 + 100 * j, 100, 100);
				this.computer[i][j].setVisible(false);
				this.mainPanel.add(this.computer[i][j]);

			}
		}

		// ���ѡ���еĿؼ� (ȷ��Ҫ�����ӵ�λ��)
		this.choose = new Choose_Label();	
		this.choose.setBounds(385, 35, 80, 80);
		this.mainPanel.add(this.choose);
		this.addKeyListener((KeyListener) this);
		

	}

	/**
	 * ���time���� ��ʱ�� ��������
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
				 * ��ʱ����м��� scd����60��ʱ���� mnt��1
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

	// �˵���ʼ��
	private void initMenu()
	{
		this.menuBar = new JMenuBar();
		this.menuE = new JMenu("�˵�(E)");
		this.menuH = new JMenu("����(H)");
		this.menuF = new JMenu("��Ϸ�ֱ���(F)");
		this.menuG = new JMenu("����˳��(G)");

		this.menuItemCZ = new JMenuItem("����");
		
		this.Game_State = new JMenuItem("��Ϸ˵��");
	    this.Game1100  =  new JMenuItem("1100*800");
	    this.Game800  =  new JMenuItem("800*600");
	    this.Before = new JMenuItem("����");
	    this.After = new JMenuItem("����");

		/*
		 * getKeyStroke(int keyCode, int modifiers) �ڸ���һ�����ּ������һ�����η�������£�����
		 * KeyStroke ��һ������ʵ����
		 */
		this.menuItemCZ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

		this.menuItemCZ.addActionListener(this); // ����ʵ���˼������Ľӿ�
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
	 * ���Ӽ��̵õ���Ӧʱ�ı�߿�λ��
	 *
	 */
	private void setchoose()   //385, 35, 80, 80
	{
		this.choose.setLocation(385 + 100 * this.choose.getXX(), 35 + 100 * this.choose.getYY());
	}

	/**
	 * ���³�ʼ���������������ӣ�����λ��״̬���㣬�߿�ص�ԭ��λ��
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
		this.choose.returnMyLabel(); // �߿��ʼ��
		this.timer.start();// ���¿�ʼ��ʱ
	}

	/**
	 * ʤ����ʾ
	 *
	 */
	private void showOver(int judge)
	{
		this.timer.stop();
        String use_time = this.time.getText();
		
		if (judge == 1)
		{
			JOptionPane.showMessageDialog(this, "       ��һ�ʤ!\n������ʱ : "+use_time);
			int flag = JOptionPane.showConfirmDialog(this, "����һ�ְ�!", "��Ϣ", JOptionPane.YES_NO_OPTION);
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
			JOptionPane.showMessageDialog(this, "       ���Ի�ʤ!\n������ʱ : "+use_time);
			int flag = JOptionPane.showConfirmDialog(this, "����һ�ְ�!", "��Ϣ", JOptionPane.YES_NO_OPTION);
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
			JOptionPane.showMessageDialog(this,"       ƽ��!\n������ʱ : "+use_time);
			int flag = JOptionPane.showConfirmDialog(this, "����һ�ְ�!", "��Ϣ", JOptionPane.YES_NO_OPTION);
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
			 * �����·�����Ƿ��γ������ӨK
			 */
			for (int i = x - 3, j = y - 3; i < x + 4; i++, j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("�Kplayer_staNum = " + player_staNum);
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
			 * ���ҷ�����Ƿ��γ������ӡ�   i��ʾx��
			 */
			for (int i = x - 3, j = y; i < x + 4; i++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("��player_staNum = " + player_staNum);
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
			 * �����Ϸ�����Ƿ��γ������ӨJ
			 */
			for (int i = x - 3, j = y + 3; i < x + 4; i++, j--)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("�Jplayer_staNum = " + player_staNum);
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
			 * ���·�����Ƿ��γ������ӡ�
			 */
			for (int i = x, j = y - 3; j < y + 4; j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 1)
					{
						player_staNum++;
						System.out.println("��player_staNum = " + player_staNum);
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
			 * �����·�����Ƿ��γ������ӨK
			 */
			for (int i = x - 3, j = y - 3; i < x + 4; i++, j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("�Kcomputer_staNum = " + computer_staNum);
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
			 * ���ҷ�����Ƿ��γ������ӡ� i ��ʾ ��
			 */
			for (int i = x - 3, j = y; i < x + 4; i++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("��computer_staNum = " + computer_staNum);
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
			 * �����Ϸ�����Ƿ��γ������ӨJ
			 */
			for (int i = x - 3, j = y + 3; i < x + 4; i++, j--)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("�Jcomputer_staNum = " + computer_staNum);
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
			 * ���·�����Ƿ��γ������ӡ�
			 */
			for (int i = x, j = y - 3; j < y + 4; j++)
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						computer_staNum++;
						System.out.println("��computer_staNum = " + computer_staNum);
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
	 * �ж���Ϸ�Ƿ����
	 * 
	 * @param x
	 *            ��ǰ����x����
	 * @param y
	 *            ��ǰ����y����
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
			
			//�ѳ��������Ļ�м�
		    Dimension  frameSize = this.getSize();
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   
		    int center_X = screenSize.width/2;
		    int center_Y = screenSize.height/2;	    
		    this.setLocation(center_X - frameSize.width/2,center_Y-frameSize.height/2 );
			
			
			this.setTitle("������");
		
			this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		}

	//���ʹ�ü�������
	public void keyPressed(KeyEvent e) 
	{
		// ���ñ߿��ƶ�
		
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

	//���ʹ���������
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
	
	//��������
	public void ComputerMove() 
	{
//		int computer_staNum = 0;
		
//		int player_staNum = 0;
		
//		boolean flag = false;
		int x = 0;
		int y = 0;
		double max_x[] = new double[7]; // ��ʼ����ÿ������Ӧ�ö�Ϊ0 ��¼ÿ�е����Ӹ������������ֵ��
		
		//��ÿ�е����Ӹ������������ֵ��ʼΪ��С��
		for(int i = 0; i<max_x.length ; i++)
		{
			max_x[i] = -10;
		}
		
		int _y[] = new int[7]; // ��¼ÿ�пɷ����ݵ��к�
		
		//��ÿ��Ԫ�ظ�ֵΪ-1������ʼ��Ϊÿ��λ�ö����ɷţ��������ҳ�ÿ�пɷ����ݵ��кţ����ȥ ���� i ��ʾ�кţ�Ԫ��Ϊ�к�
		for(int i = 0 ; i<_y.length ; i++)
		{
			_y[i] = -1;
		}
		
		// #region //�ҳ�ÿ�пɷ����ݵ��к� ����¼ ��ͬʱ�ҳ����зŵ����Ӻ�ɳ����������
		for (int r = 0; r <= 6; r++)
		{
			// �ҳ�ÿ�пɷ����ݵ��к� ����¼
			for (int c = 5; c >= 0; c--)
			{
				if (this.pos[r][c].getLabel_Status() == 0)
				{
					_y[r] = c;
					break;
				} // if������
			}

			// #region������Եĸ�������������
	        if(_y[r]!=-1)
	        {
			this.pos[r][_y[r]].setLabel_Status(2);
			System.out.println("������Եĸ������������� : ");

			int you_xia = 0;
			int you = 0;
			int xia = 0;
			int you_shang = 0;
			x = r; 
			y = _y[r]; 
			/**
			 * �����·�����Ƿ��γ������ӨK
			 */
			for (int i = x - 3, j = y - 3, n = 0; i < x + 4; i++, j++) // ��λ����Ҫ����i
																		// j
																		// ��������ʽ����
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						n++;
						if (n > you_xia)
						{
							you_xia = n;
							System.out.println("�Kyou_xia_staNum = " + you_xia);
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
			 * ���ҷ���� ��
			 */
			for (int i = x - 3, j = y, n = 0; i < x + 4; i++) // �� i �� ?
			{
				if (i >= 0 && i <= 6 && j >= 0 && j <= 5)
				{
					if (this.pos[i][j].getLabel_Status() == 2)
					{
						n++;
						if (n > you)
						{
							you = n;
							System.out.println("��you_staNum = " + you);
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
			 * �����Ϸ���� �J
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
							System.out.println("�Jyou_shang_staNum = " + you_shang);
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
			 * ���·���� ��
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
							System.out.println(" ��xia_staNum = " + xia);
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
			max_x[r] = Math.max(Math.max(you, you_xia), Math.max(xia, you_shang)); // ����������������������ĸ���
			System.out.println("������Եĸ�������������\n");
			this.pos[r][_y[r]].setLabel_Status(0);
		
           }
	       // #endregion
	        
		} // r������
			// #endregion

		// #region�ж��Ƿ�ƽ��
		boolean ping_flag = true;
		for(int i = 0 ; i < _y.length ; i++)
		{
			if(_y[i] != -1)
			{
				ping_flag =false;
			}
		}
		if(ping_flag == true) //�����λ�ÿɷ�
		{
			this.showOver(0);
		}
		//#endregion
		
		System.out.println("��������1");
		
		// ��������
		System.out.println("max_x[r]:");
		for (int i = 0; i < max_x.length; i++)
		{
			System.out.println(max_x[i]);
		}
		System.out.println("max_x[r]");

		System.out.println("��������1\n");
		
		// ��������������ж���־
		boolean flag1 = false;
		boolean flag2 = false;
//		boolean flag3 = false;
//		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;

		//#region   1.�������Ϊ�����ӣ��ж�����������
           flag1 = computer_four(_y);
		// #endregion
		
        //#region   2.�������Ϊ����ӣ��жϿ��Ƿ���ڣ����4���������������λ����ҽ����ӵ��������������
		if (flag1 == false)
		{
			flag2 = player_four(_y);
		}
		// #endregion
  
		//#region   4.�ų�������࣬���ǵ�����Ҳ�޷����4�����ӵ�	���ȼ�Ϊ0	    
		    
		    useless_chess ( _y,max_x);
		    
		    
		//#endregion
		
		//#region   5.������Է�һ����һ�� ������ͬʱ�γ�2�ַ��������������� ��������max_x[x] = 10,�����ȼ�Ϊ 4
		if( flag1 == false && flag2 == false )
		  {
                  flag5 = double_head(_y,max_x);
		  }

		//#endregion
		
		//#region   6.�����ҷ�һ����һ�������ͬʱ�γ�2�ַ��������������� ��������max_x[x] = 10,�����ȼ�Ϊ 10
		if (flag1 == false && flag2 == false )
			{
			     flag6 = double_three ( _y,max_x);
			}
		//#endregion  
	   	
        //#region   8.�ų����ʯ��״̬, ��������´�λ�ã���������ڴ�λ����һ�ӣ��������ַ��������ӣ����λ�����ȼ�Ϊ-8 ����δ��ƣ�
		          
     	//#endregion
		
        //#region   3.�ų����ʯ��״̬, �����λ����һλ��������ӣ�����ӿɳ����ӣ����λ�����ȼ�Ϊ-9
		    stepping_stone(_y,max_x);	
     	//#endregion
		
	    // #region  7.��ͨ�������������Ӻ���������λ������		
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
	
	//1.�������Ϊ�����ӣ��ж�����������
	public boolean computer_four(int _y[])
	{
		int x = 0 ;
		int y = 0 ;
		boolean flag1 = false;
		
	    System.out.println("����1 :");
		for (int x1 = 0; x1 < 7; x1++)
		{
            if(_y[x1]!=-1)
            {
				x = x1;
				y = _y[x1];
				
				this.pos[x][y].setLabel_Status(2);
	            
				flag1 = is_Four(x,y,2);
				// ������������ӣ������ڴ˴�
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
    
	//2.�������Ϊ����ӣ��жϿ��Ƿ���ڣ����4���������������λ����ҽ����ӵ��������������
	public boolean player_four(int _y[])
	{
		int x = 0 ;
		int y = 0 ;
		boolean flag2 = false;
		
		System.out.println("����2 :");
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
					
					//�ж��Ƿ�ƽ��
					boolean ping_flag = true;
					
					for (int r2 = 0; r2 <= 6; r2++)
					{
						for (int c = 5; c >= 0; c--)
						{
							if (this.pos[r2][c].getLabel_Status() == 0)
							{
								ping_flag = false;
								break;
							} // if������
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
   
	//3.�ų����ʯ��״̬, �����λ����һλ��������ӣ�����ӿɳ����ӣ����λ�ò�����
	public void stepping_stone(int _y[],double max_x[])
	{
		  int x = 0 ;
		  int y = 0 ;
		  boolean flag3 = false;
		  
	      System.out.println("����3 ��");
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
		  	
			// ��������
		   System.out.println("��������2");
		  
			System.out.println("max_x[r]:");
			for (int i = 0; i < max_x.length; i++)
			{
				System.out.println(max_x[i]);
			}
			System.out.println("max_x[r]");

			System.out.println("��������2\n");
		  
	System.out.println("����3���� :");
	}
   
	// 4.�ų�������࣬���ǵ�����Ҳ�޷����4�����ӵ�
	public void useless_chess(int _y[] , double max_x[])
	{
	    int x = 0 ;
		int y = 0 ;
		
	    System.out.println("����4 :");
	    
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
				 * �����·�����Ƿ��γ������ӨK
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
				 * ���ҷ�����Ƿ��γ������ӡ�
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
				 * �����Ϸ�����Ƿ��γ������ӨJ
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
				
			    this.pos[x][y].setLabel_Status(temp);  //�ָ���־
			    
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
		
		// ��������
		   System.out.println("��������3");
		  
			System.out.println("max_x[r]:");
			for (int i = 0; i < max_x.length; i++)
			{
				System.out.println(max_x[i]);
			}
			System.out.println("max_x[r]");

			System.out.println("��������3\n");
		
		System.out.println("����4���� :");
	}
 
	//5.������Է�һ����һ�� ������ͬʱ�γ�2�ַ��������������� ��������max_x[x] = 10,�����ȼ�Ϊ 4
	public boolean double_head(int _y[],double max_x[])
	{
		
		
		  int x = 0 ;
		  int y = 0 ;
//		  int player_staNum = 0;
		  boolean flag5= false;
		   boolean flag= false;
		  System.out.println("����5  :");
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

				    
					int _z[] = new int[7]; // ��¼ÿ�пɷ����ݵ��к�
					
					//��ÿ��Ԫ�ظ�ֵΪ-1������ʼ��Ϊÿ��λ�ö����ɷţ��������ҳ�ÿ�пɷ����ݵ��кţ����ȥ ���� i ��ʾ�кţ�Ԫ��Ϊ�к�
					for(int i = 0 ; i<_y.length ; i++)
					{
						_z[i] = -1;
					}
					
					// #region //�ҳ�ÿ�пɷ����ݵ��к� ����¼ ��ͬʱ�ҳ����зŵ����Ӻ�ɳ����������
					for (int r = 0; r <= 6; r++)
					{
						// �ҳ�ÿ�пɷ����ݵ��к� ����¼
						for (int c = 5; c >= 0; c--)
						{
							if (this.pos[r][c].getLabel_Status() == 0)
							{
								_z[r] = c;
								break;
							} // if������
						}
					}
	                 		    
					//�жϸ����ܷ�����һ����Ч��ʵ��4����
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
		  
		  
		  System.out.println("����5����");
		  return flag5 ;
	}

	
	// 6.�����ҷ�һ����һ�������ͬʱ�γ�2�ַ��������������� ��������max_x[x] = 10,�����ȼ�Ϊ 10
	public boolean double_three (int _y[],double max_x[])
	{
	    int x = 0 ;
        int y = 0 ;
//	    int player_staNum = 0;
	    boolean flag6= false;
	    boolean flag= false;
		System.out.println("����6  :");
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

			    
				int _z[] = new int[7]; // ��¼ÿ�пɷ����ݵ��к�
				
				//��ÿ��Ԫ�ظ�ֵΪ-1������ʼ��Ϊÿ��λ�ö����ɷţ��������ҳ�ÿ�пɷ����ݵ��кţ����ȥ ���� i ��ʾ�кţ�Ԫ��Ϊ�к�
				for(int i = 0 ; i<_y.length ; i++)
				{
					_z[i] = -1;
				}
				
				// #region //�ҳ�ÿ�пɷ����ݵ��к� ����¼ ��ͬʱ�ҳ����зŵ����Ӻ�ɳ����������
				for (int r = 0; r <= 6; r++)
				{
					// �ҳ�ÿ�пɷ����ݵ��к� ����¼
					for (int c = 5; c >= 0; c--)
					{
						if (this.pos[r][c].getLabel_Status() == 0)
						{
							_z[r] = c;
							break;
						} // if������
					}
				}
                 
    
			    
				//�жϸ����ܷ�����һ����Ч��ʵ��4����
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
        System.out.println("����6����");
        return flag6;
	}
	
	// 8.�������ӵ�ų��������һ�� ����������ַ���������
	public void stepping_stone2(int _y[],double max_x[])
	{

	}
	
	//7.��ͨ�������������Ӻ���������λ������
	public void normal_attack (int _y[],double max_x[])  
	{
		System.out.println("����7 �� ");
		
		// ��ͨ�������ж���һ��������࣬�ͷ�����һ�У��������ͬ�����
		double a[] = new double[7];

		// ����һ����������������кţ�index_num��ʵ���� ����ͬʱ������� ���к���
		int MaxX_index[] = new int[7];
		int index_num = 0;
		

		for (int i = 0; i < max_x.length; i++)
		{
				 if(i>=2&&i<=4)
				 {
					 max_x[i]+=0.5;
				 }
		}


		// Ϊ�˱���max��max_col�����ֵ����a����ͨ�������������������������������
		for (int i = 0; i < max_x.length; i++)
		{
			a[i] = max_x[i];
		}
		Arrays.sort(a);

		double max = a[6]; // maxΪ��������������������

		// �ҳ������������У�����index������
		for (int i = 0; i < max_x.length; i++)
		{
			if (max == max_x[i])
			{
				MaxX_index[index_num] = i;
				index_num++;
			}
		}
		
        System.out.println("��������max_x   end : ");
		for (int i = 0; i < max_x.length; i++)
		{
			 System.out.print(max_x[i] + " \n");
		}
		 System.out.println("��������max_x   end : ");

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
        
		
		//�ж��Ƿ�ƽ��
		boolean ping_flag = true;
		
		for (int r2 = 0; r2 <= 6; r2++)
		{
			for (int c = 5; c >= 0; c--)
			{
				if (this.pos[r2][c].getLabel_Status() == 0)
				{
					ping_flag = false;
					break;
				} // if������
			}
		}
		
		if(ping_flag == true)
		{
			this.showOver(0);
		}
		this.validate();
        
		System.out.println("����7����");
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
	
 //#region ���෽��
	
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