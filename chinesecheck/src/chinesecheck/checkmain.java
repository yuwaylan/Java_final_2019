/*
 * check main 主要進行畫布繪製以及得知滑鼠操控
 * 以及所有ＵＩ畫面
 * */

/*
 * 棋盤視窗寬為 一千
 * 視窗高為七百
 * log_pannel寬為200、高與視窗同高
 * 
 * 棋盤寬為800、高為七百
 * 
 * 直線有9條
 * 橫線有10條
 * 
 * 寬要除以10
 * 高耀除以11
 * 
 * 
 * 
 * 
 */
package chinesecheck;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class checkmain {

	Frame frame_border;
	Canvas canvas;
	ArrayList<String> log_arrlist = new ArrayList<String>(); // 用來log
	// List<shape> list=new ArrayList<shape>();
	static TextField log_ipTextField = new TextField(); // 可能用不到 用來設定ip
	TextField log_portTextField = new TextField(); // 可能用不到 用來設定port
	JTextArea log_TextField = new JTextArea(); // 用來記錄ＬＯＧ 位置在最大的文字匡
	Button startButton = new Button("Start");
	Button nextstepButton = new Button("Next");
	mthread mt;
	public static int[][] chess_chess = { { 7, 5, 3, 1, 0, 2, 4, 6, 8 }, // 0,j;j from 0 to 8
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 1,j
			{ -1, 9, -1, -1, -1, -1, -1, 10, -1 }, // 2,j
			{ 11, -1, 12, -1, 13, -1, 14, -1, 15 }, // 3,j
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 4,j
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 5,j
			{ 111, -1, 112, -1, 113, -1, 114, -1, 115 }, // 6,j
			{ -1, 109, -1, -1, -1, -1, -1, 110, -1 }, // 7,j
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 8,j
			{ 107, 105, 103, 101, 100, 102, 104, 106, 108, 110 }// 9,
	};

	public static Point[][] cbPoint = new Point[9][10];
	public static int frame_width = 1200;
	public static int frame_height = 800;
	static String logString = new String();
	public static int chessboard_width = frame_width;
	public static int chessboard_heigh = frame_height;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		checkmain checkmain = new checkmain();
		checkmain.makeboard();
	}

	public checkmain() {

	}

	void makeboard() {

		frame_border = new Frame("象棋");
		frame_border.setSize(frame_width, frame_height);
		Panel log_panel = new Panel();
		mc canvas = new mc();

		frame_border.setLayout(new BorderLayout(100, frame_height));
		frame_border.add("West", log_panel);// 切出左側log console
		log_panel.setPreferredSize(new Dimension(200, frame_height));
		log_panel.add(startButton);
		log_panel.add(nextstepButton);
		log_panel.add(new Label("SID:"));
		log_panel.add(log_ipTextField);
		log_ipTextField.setPreferredSize(new Dimension(150, 20));
		log_panel.add(new Label("STATUS:"));
		log_TextField.setPreferredSize(new Dimension(150, 500));
		// log_TextField.setColumns(1000);
		log_TextField.setWrapStyleWord(true);
		log_panel.add(log_TextField); // 作為ＬＯＧ的視窗
		log_TextField.setEditable(false); // 不給編輯
		log_panel.setBackground(Color.lightGray);
		canvas.setBackground(Color.LIGHT_GRAY);

		frame_border.add(canvas);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {

				cbPoint[i][j] = new Point();

				cbPoint[i][j].x = (i * 80);
				cbPoint[i][j].y = 40 + (j * 70);
				// System.out.print(i + "," + j + "");
				// System.out.print("(" + cbPoint[i][j].x + "," + cbPoint[i][j].y + ")\t");

			}
			System.out.println();
		}

		// chess board的i,j chess_chess的 i j;

		frame_border.setVisible(true);
		// log panel UI end!!!!!!

		// frame_border.addWindowListener
		frame_border.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				// System.exit(0);
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

		});

		startButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String sid = log_ipTextField.getText();
				mt = new mthread(sid);
				mt.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				log_TextField.setText(logString);

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
	// ++++++++++++window listener end

}

class mc extends Canvas {
	Point a = new Point();
	Point b = new Point();
	int temp = 0;
	// online online = new online(checkmain.log_ipTextField.getText());
	// get_point_on_board pob;
	static int tax = 0, tay = 0;
	static int tbx = 0, tby = 0;

	String s[] = new String[3];

	public mc() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				a.setLocation(e.getPoint());
				b.setLocation(e.getPoint());

				// System.out.println("PPP " + a.x + "," + a.y);
				// pob = new get_point_on_board(a, b);
				// Point[9][10];
				// System.out.print("cbpa pos= (");
				for (int i = 0; i < 9; i++) {
					if (Math.abs(a.x - (50 + i * 80)) <= 25) {
						// System.out.print(i + " , ");
						tax = i;
					}
				}
				for (int i = 0; i < 10; i++) {
					if (Math.abs(a.y - (40 + i * 70)) <= 25) {
						// System.out.print(i + " )");
						tay = i;
					}
				}
				System.out.println();
				temp = 0;
				// 得出cpb pos

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				b.setLocation(e.getPoint());
				// System.out.print("cbpb pos= (");
				for (int i = 0; i < 9; i++) {
					if (Math.abs(b.x - (50 + i * 80)) <= 25) {
						// System.out.print(i + " , ");
						tbx = i;
					}
				}
				for (int i = 0; i < 10; i++) {
					if (Math.abs(b.y - (40 + i * 70)) <= 25) {
						// System.out.print(i + " )");
						tby = i;
					}
				}
				// System.out.println();
				// System.out.print("cbp:" + tbx + "," + tby + "chess=(" +
				// checkmain.chess_chess[tay][tax] + ")");

				s[0] = String.valueOf(checkmain.chess_chess[tay][tax]);
				s[1] = String.valueOf(tbx);
				s[2] = String.valueOf(tby);

				chinesecheck.online.srtwalkchess(s);
				System.out.print("you:(" + chinesecheck.online.walkchess[0] + " , " + chinesecheck.online.walkchess[1]
						+ " , " + chinesecheck.online.walkchess[2] + ")");
				checkmain.logString += "you:(" + chinesecheck.online.walkchess[0] + " , "
						+ chinesecheck.online.walkchess[1] + " , " + chinesecheck.online.walkchess[2] + ")";

				if (checkmain.chess_chess[tay][tax] == -1) {
					temp = checkmain.chess_chess[tay][tax];
					checkmain.chess_chess[tay][tax] = checkmain.chess_chess[tby][tbx];
					checkmain.chess_chess[tby][tbx] = temp;
				} else {
					temp = checkmain.chess_chess[tay][tax];
					checkmain.chess_chess[tay][tax] = -1;
					checkmain.chess_chess[tby][tbx] = temp;
				}
				repaint();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void paint(Graphics g) {
		// Point[9][10];
		for (int i = 0; i < 9; i++) {
			g.drawLine(checkmain.cbPoint[i][0].x + 50, checkmain.cbPoint[i][0].y, checkmain.cbPoint[i][9].x + 50,
					checkmain.cbPoint[i][9].y);
		}
		for (int i = 0; i <= 9; i++) {
			g.drawLine(checkmain.cbPoint[0][i].x + 50, checkmain.cbPoint[0][i].y, checkmain.cbPoint[8][i].x + 50,
					checkmain.cbPoint[8][i].y);
		}
		g.drawLine(checkmain.cbPoint[3][0].x + 50, checkmain.cbPoint[3][0].y, checkmain.cbPoint[5][2].x + 50,
				checkmain.cbPoint[5][2].y);
		g.drawLine(checkmain.cbPoint[3][2].x + 50, checkmain.cbPoint[3][2].y, checkmain.cbPoint[5][0].x + 50,
				checkmain.cbPoint[5][0].y);

		g.drawLine(checkmain.cbPoint[3][7].x + 50, checkmain.cbPoint[3][7].y, checkmain.cbPoint[5][9].x + 50,
				checkmain.cbPoint[5][9].y);
		g.drawLine(checkmain.cbPoint[3][9].x + 50, checkmain.cbPoint[3][9].y, checkmain.cbPoint[5][7].x + 50,
				checkmain.cbPoint[5][7].y);
		g.setColor(Color.gray);
		g.fillRect(50, checkmain.cbPoint[0][4].y, Math.abs(checkmain.cbPoint[0][4].x - checkmain.cbPoint[8][4].x), 70);

		BufferedImage in;
		// String flocationString =
		// "/Users/chenghanyu/Documents/Java/chinesecheck/src/chinesecheck/chess/";
		String flocationString = "\\chess\\";
		try {
			// in = ImageIO.read(new FileInputStream(flocationString+"00.png"));
			// g.drawImage(in, 0, 0,50,50, this);
			// URL rUrl=checkmain.class.getResource("/chess00.png");

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 9; j++) {
					if (checkmain.chess_chess[i][j] != -1) {
						switch (checkmain.chess_chess[i][j]) {
						case 0:

							in = ImageIO.read(this.getClass().getResource(flocationString + "00.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 1:
						case 2:
							in = ImageIO.read(this.getClass().getResource(flocationString + "01.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 3:
						case 4:
							in = ImageIO.read(this.getClass().getResource(flocationString + "02.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 5:
						case 6:
							in = ImageIO.read(this.getClass().getResource(flocationString + "03.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 7:
						case 8:
							in = ImageIO.read(this.getClass().getResource(flocationString + "05.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 9:
						case 10:
							in = ImageIO.read(this.getClass().getResource(flocationString + "04.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 11:
						case 12:
						case 13:
						case 14:
						case 15:
							in = ImageIO.read(this.getClass().getResource(flocationString + "06.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						/* 紅色棋子VS黑色棋子 */
						case 100:
							in = ImageIO.read(this.getClass().getResource(flocationString + "10.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 101:
						case 102:
							in = ImageIO.read(this.getClass().getResource(flocationString + "11.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 103:
						case 104:
							in = ImageIO.read(this.getClass().getResource(flocationString + "12.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 105:
						case 106:
							in = ImageIO.read(this.getClass().getResource(flocationString + "13.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 107:
						case 108:
							in = ImageIO.read(this.getClass().getResource(flocationString + "15.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 109:
						case 110:
							in = ImageIO.read(this.getClass().getResource(flocationString + "14.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;
						case 111:
						case 112:
						case 113:
						case 114:
						case 115:
							in = ImageIO.read(this.getClass().getResource(flocationString + "16.png"));
							g.drawImage(in, checkmain.cbPoint[j][i].x + 25, checkmain.cbPoint[j][i].y - 25, 50, 50,
									this);
							break;

						}

					}

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
