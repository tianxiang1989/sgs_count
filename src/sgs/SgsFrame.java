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
	// ʵ����
	private SgsBean sgsBean;
	// ��ʾ������и�ı�ǩ
	private JLabel jiuJLabel;
	private JLabel taoJLabel;
	private JLabel wxJLabel;
	// ���������и�İ�ť
	private JButton[] thisWhat = { new JButton("��"), new JButton("��"),
			new JButton("��и"),new JButton("��"),new JButton("����") };
	// ��ʾ������иʣ�������ı�ǩ
	private JLabel[] thisCount = new JLabel[thisWhat.length];
	//һ�ֽ��� ͳ��һ�ֵĽ��
	private JButton clearResult;
	//���ֽ���
	private JButton clearAll;
	//��ʾÿһ�ֵĳ��ƽ��
	private JLabel resShowLable;
	//�ڼ���
	private int round = 1;
	private final int jiu = 0;
	private final int tao = 1;
	private final int wuxie = 2;
	private final int wanjian=3;
	private final int nanman=4;
	
	private SgsFrame() {
		setTitle("����ɱ������ �����һ �Ҽ���һ");
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
		// ���ô�ֱ������ʼ������
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//����ˮƽ�������Զ�����
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scroll);

		for (int i = 0; i < thisWhat.length; i++) {
			//��ʾ������и�ı�ǩ
			thisWhat[i].setFont(new Font("Serif", Font.PLAIN, 13));
			thisWhat[i].setBounds(30, 10 + i * 35, 60, 25);
			assignMouse(i);
			contentPane.add(thisWhat[i]);
			//��ʾ������иʣ�������ı�ǩ
			thisCount[i] = new JLabel("" + sgsBean.getCountAll().get(i));
			thisCount[i].setBounds(110, 10 + i * 35, 60, 25);
			contentPane.add(thisCount[i]);
		}
		//һ�ֽ�����ǩ
		clearResult = new JButton("һ�ֽ���");
		clearResult.setBounds(30, 185, 90, 30);
		//���¼�
		clearResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mods = e.getModifiers();
				if ((mods & InputEvent.BUTTON1_MASK) != 0) {
					// ͳ��ÿһ��ʣ�������
					StringBuffer sb = new StringBuffer();
					String a = resShowLable.getText();
					// System.out.println("a�ǣ�"+a);
					if (!"".equals(a)) {
						int begin = a.indexOf("<html>");
						int end = a.indexOf("</html>");
						a = a.substring(begin + 6, end);
						sb.append(a);
					}
					if(round<10){
						sb.append("��0" + (round++) + "�֣�");
					}else{
					sb.append("��" + (round++) + "�֣�");
					}
					for (int i = 0; i < thisWhat.length; i++) {
						if(Integer.parseInt(thisCount[i].getText())==0){
							sb.append(thisWhat[i].getText()).append("->").append(thisCount[i].getText());
							//�ж�ĳ�������Ƿ�С��10 ����λ��
							if(Integer.parseInt(thisCount[i].getText())<10
									&&Integer.parseInt(thisCount[i].getText())>=0){
								sb.append("&nbsp");
							}
						}else{
							sb.append("<font color=#ff0000>");
							sb.append(thisWhat[i].getText()).append("->").append(thisCount[i].getText());
							sb.append("</font>");
							//�ж�ĳ�������Ƿ�С��10 ����λ��
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
		//���ֽ����ı�ǩ
		clearAll = new JButton("���ֽ���");
		clearAll.setBounds(30, 223, 90, 30);
		//���¼�
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

	// ������¼� ������иר��
	private void assignMouse(int i) {
		if (i == jiu) {
			thisWhat[jiu].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mods = e.getModifiers();
					//����Ҽ�
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, jiu);
					}
					//������
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
					//����Ҽ�
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, tao);
					}
					//������
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
					//����Ҽ�
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, wuxie);
					}
					//������
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
					//����Ҽ�
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, wanjian);
					}
					//������
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
					//����Ҽ�
					if ((mods & InputEvent.BUTTON3_MASK) != 0) {
						SgsUtils.add(sgsBean, nanman);
					}
					//������
					if ((mods & InputEvent.BUTTON1_MASK) != 0) {
						SgsUtils.desc(sgsBean, nanman);
					}
					update();
				}
			});
		}
	}

	// ˢ��ʣ������
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
