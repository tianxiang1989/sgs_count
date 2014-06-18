package sgs;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class SgsFrame extends JFrame {
	// 实体类
	private SgsBean sgsBean;
	// 显示酒桃无懈的标签
	private JLabel jiuJLabel;
	private JLabel taoJLabel;
	private JLabel wxJLabel;
	// 计算酒桃无懈的按钮
	private JButton[] thisWhat = { new JButton("酒"), new JButton("桃"),
			new JButton("无懈"),new JButton("万剑"),new JButton("南蛮") };
	// 显示酒桃无懈剩余数量的标签
	private JLabel[] thisCount = new JLabel[thisWhat.length];
	//一轮结束 统计一轮的结果
	private JButton clearResult;
	//整局结束
	private JButton clearAll;
	//显示每一轮的出牌结果
	private JLabel resShowLable;
	//第几轮
	private int round = 1;
	private final int jiu = 0;
	private final int tao = 1;
	private final int wuxie = 2;
	private final int wanjian=3;
	private final int nanman=4;
	
	private SgsFrame() {
		setTitle("三国杀记牌器 左键减一 右键加一");
		setSize(480, 300);
		// setFont(new Font("Serif",Font.BOLD|Font.ITALIC,24));
		init();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void init() {
		sgsBean = new SgsBean();
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		resShowLable = new JLabel("");
		resShowLable.setVerticalAlignment(SwingConstants.TOP);
		resShowLable.setBounds(140, 15, 310, 230);
		// resShowLable.setBorder(BorderFactory.createLineBorder(Color.red));
		// contentPane.add(resShowLable);
		JScrollPane scroll = new JScrollPane(resShowLable);
		scroll.setBounds(140, 15, 310, 230);
		// 设置垂直滚动条始终隐藏
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//设置水平滚动条自动出现
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scroll);

		for (int i = 0; i < thisWhat.length; i++) {
			//显示酒桃无懈的标签
			thisWhat[i].setFont(new Font("Serif", Font.PLAIN, 13));
			thisWhat[i].setBounds(30, 10 + i * 35, 60, 25);
			assignMouse(i);
			contentPane.add(thisWhat[i]);
			//显示酒桃无懈剩余数量的标签
			thisCount[i] = new JLabel("" + sgsBean.getCountAll().get(i));
			thisCount[i].setBounds(110, 10 + i * 35, 60, 25);
			contentPane.add(thisCount[i]);
		}
		//一轮结束标签
		clearResult = new JButton("一轮结束");
		clearResult.setBounds(30, 185, 90, 30);
		//绑定事件
		clearResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mods = e.getModifiers();
				if ((mods & InputEvent.BUTTON1_MASK) != 0) {
					// 统计每一轮剩余的数量
					StringBuffer sb = new StringBuffer();
					String a = resShowLable.getText();
					// System.out.println("a是："+a);
					if (!"".equals(a)) {
						int begin = a.indexOf("<html>");
						int end = a.indexOf("</html>");
						a = a.substring(begin + 6, end);
						sb.append(a);
					}
					if(round<10){
						sb.append("第0" + (round++) + "轮：");
					}else{
					sb.append("第" + (round++) + "轮：");
					}
					for (int i = 0; i < thisWhat.length; i++) {
						if(Integer.parseInt(thisCount[i].getText())==0){
							sb.append(thisWhat[i].getText()).append("->").append(thisCount[i].getText());
							//判断某个数字是否小于10 补齐位数
							if(Integer.parseInt(thisCount[i].getText())<10
									&&Integer.parseInt(thisCount[i].getText())>=0){
								sb.append("&nbsp");
							}
						}else{
							sb.append("<font color=#ff0000>");
							sb.append(thisWhat[i].getText()).append("->").append(thisCount[i].getText());
							sb.append("</font>");
							//判断某个数字是否小于10 补齐位数
							if(Integer.parseInt(thisCount[i].getText())<10
									&&Integer.parseInt(thisCount[i].getText())>=0){
								sb.append("&nbsp");
							}
						}
						sb.append("&nbsp");
					}
					 System.out.println(sb.toString());
					resShowLable.setText("<html>" + sb.toString()
							+ "<br></html>");
					sgsBean.init();
					update();
				}
			}
		});
		contentPane.add(clearResult);
		//整局结束的标签
		clearAll = new JButton("整局结束");
		clearAll.setBounds(30, 223, 90, 30);
		//绑定事件
		clearAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mods = e.getModifiers();
				if ((mods & InputEvent.BUTTON1_MASK) != 0) {
				sgsBean.init();
				round = 1;
				resShowLable.setText("");
				update();
				}
			}
		});
		contentPane.add(clearAll);

	}

	// 绑定鼠标事件 酒桃无懈专用
	private void assignMouse(int i) {
		if (i == jiu) {
			thisWhat[jiu].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mods = e.getModifiers();
					//鼠标右键
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, jiu);
					}
					//鼠标左键
					if ((mods & InputEvent.BUTTON1_MASK) != 0) {
						SgsUtils.desc(sgsBean, jiu);
					}
					update();
				}
			});

		} else if (i == tao) {
			thisWhat[tao].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mods = e.getModifiers();
					//鼠标右键
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, tao);
					}
					//鼠标左键
					if ((mods & InputEvent.BUTTON1_MASK) != 0) {
						SgsUtils.desc(sgsBean, tao);
					}
					update();
				}
			});

		} else if (i == wuxie) {
			thisWhat[wuxie].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mods = e.getModifiers();
					//鼠标右键
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, wuxie);
					}
					//鼠标左键
					if ((mods & InputEvent.BUTTON1_MASK) != 0) {
						SgsUtils.desc(sgsBean, wuxie);
					}
					update();
				}
			});
		}else if (i == wanjian) {
			thisWhat[wanjian].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mods = e.getModifiers();
					//鼠标右键
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, wanjian);
					}
					//鼠标左键
					if ((mods & InputEvent.BUTTON1_MASK) != 0) {
						SgsUtils.desc(sgsBean, wanjian);
					}
					update();
				}
			});
		}else if (i == nanman) {
			thisWhat[nanman].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mods = e.getModifiers();
					//鼠标右键
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, nanman);
					}
					//鼠标左键
					if ((mods & InputEvent.BUTTON1_MASK) != 0) {
						SgsUtils.desc(sgsBean, nanman);
					}
					update();
				}
			});
		}
	}

	// 刷新剩余数量
	private void update() {
		for (int i = 0; i < thisCount.length; i++) {
			thisCount[i].setText("");
		}
		for (int i = 0; i < thisCount.length; i++) {
			thisCount[i].setText("" + sgsBean.getCountAll().get(i));
		}
	}

	public static void main(String[] args) throws Exception {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Windows".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
		SgsFrame sgsFrame = new SgsFrame();
		sgsFrame.setVisible(true);
	}
}
