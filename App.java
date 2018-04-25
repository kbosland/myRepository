import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

public class App extends JFrame {

	public final int LIN_SYS = 0;
	public final int LIN_EQ = 1;
	public final int QUAD_EQ = 2;

	public int choice = -1;

	JTextField eq1 = new JTextField(15);
	JTextField eq2 = new JTextField(15);
	JTextField eq3 = new JTextField(15);

	JTextArea output = new JTextArea(10, 15);

	String equation1 = "";
	String equation2 = "";
	String equation3 = "";

	public App() {

		setTitle("Kael Bosland");
		setBounds(500, 500, 550, 300);
		setLayout(new FlowLayout());

		JLabel title = new JLabel("Welcome to the Equation Solver", SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(500, 50));
		title.setFont(new Font("Calibri", Font.BOLD, 35));
		title.setForeground(Color.black);
		getContentPane().add(title, BorderLayout.NORTH);

		JLabel choice = new JLabel("", SwingConstants.CENTER);
		choice.setText("<html>Please choose one of the three options below for <br>inputting an equation.</html>");
		choice.setPreferredSize(new Dimension(500, 80));
		choice.setFont(new Font("Calibri", Font.BOLD, 20));
		choice.setForeground(Color.black);
		getContentPane().add(choice, BorderLayout.NORTH);

		JButton linSystem = new JButton("Linear System of Equations");
		JButton linear = new JButton("Linear Equation");
		JButton quadratic = new JButton("Quadratic Equation");

		linSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				linSystem();
				pageTwo();
			}
		});

		linear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				linEq();
				pageTwo();
			}
		});

		quadratic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quadEq();
				pageTwo();
			}
		});

		linSystem.setFont(new Font("Calibri", Font.BOLD, 18));
		linear.setFont(new Font("Calibri", Font.BOLD, 18));
		quadratic.setFont(new Font("Calibri", Font.BOLD, 18));

		getContentPane().add(linSystem, BorderLayout.NORTH);
		getContentPane().add(linear, BorderLayout.NORTH);
		getContentPane().add(quadratic, BorderLayout.NORTH);

		setVisible(true);
	}

	public void pageTwo() {
		setVisible(false);

		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setTitle("Kael Bosland");
		j.setBounds(500, 500, 550, 550);
		j.setLayout(new FlowLayout());
		j.setVisible(true);

		JPanel upperPanel = new JPanel();
		JPanel lowerPanel = new JPanel();
		JPanel titleL = new JPanel();
		JPanel submitL = new JPanel();
		JPanel backL = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.PAGE_AXIS));
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.PAGE_AXIS));
		titleL.setLayout(new BoxLayout(titleL, BoxLayout.PAGE_AXIS));
		submitL.setLayout(new BoxLayout(submitL, BoxLayout.PAGE_AXIS));
		j.getContentPane().add(titleL, BorderLayout.NORTH);
		j.getContentPane().add(upperPanel, BorderLayout.NORTH);
		j.getContentPane().add(lowerPanel, BorderLayout.NORTH);
		j.getContentPane().add(submitL, BorderLayout.PAGE_END);
		j.getContentPane().add(backL, BorderLayout.SOUTH);

		String s = "";
		String s1 = "";

		if (this.choice == LIN_SYS) {
			s = "Solving a System of Linear Equations";
			s1 = "Input Form: Ax + By + Cz = D";
		} else if (this.choice == LIN_EQ) {
			s = "Solving a Linear Equation of a Graph";
			s1 =  "Input Form: Ax + By = C";
		} else if (this.choice == QUAD_EQ) {
			s = "Solving a Quadratic Equation of a Graph";
			s1 = "Input Form: y = Ax^2 + Bx + C";
		}

		JLabel title = new JLabel(s, SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(500, 50));
		title.setFont(new Font("Calibri", Font.BOLD, 30));
		title.setForeground(Color.black);
		titleL.add(title, BorderLayout.NORTH);
		
		JLabel subtitle = new JLabel(s1, SwingConstants.CENTER);
		subtitle.setPreferredSize(new Dimension(500, 50));
		subtitle.setFont(new Font("Calibri", Font.BOLD, 20));
		subtitle.setForeground(Color.black);
		titleL.add(subtitle, BorderLayout.SOUTH);

		eq1.setFont(new Font("Calibri", Font.BOLD, 25));
		eq2.setFont(new Font("Calibri", Font.BOLD, 25));
		eq3.setFont(new Font("Calibri", Font.BOLD, 25));

		upperPanel.add(eq1);
		if (this.choice == LIN_SYS) {
			upperPanel.add(eq2);
			upperPanel.add(eq3);
		}

		JButton submit = new JButton("Calculate the Results");
		submit.setFont(new Font("Calibri", Font.BOLD, 30));

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choice == LIN_SYS) {
					if (!eq1.getText().equals("") && !eq2.getText().equals("") && !eq3.getText().equals("")) {
						submit();
					}
				} else {
					if (!eq1.getText().equals("")) {
						submit();
					}
				}
			}
		});

		output.setEditable(false);
		output.setFont(new Font("Calibri", Font.BOLD, 15));
		lowerPanel.add(output);
		submitL.add(submit, BorderLayout.CENTER);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eq1.setText("");
				eq2.setText("");
				eq3.setText("");
				output.setText("");
				setVisible(true);
				j.setVisible(false);
			}
		});
		
		
		backL.add(back, BorderLayout.SOUTH);
	}

	public void linSystem() {
		this.choice = LIN_SYS;
	}

	public void linEq() {
		this.choice = LIN_EQ;
	}

	public void quadEq() {
		this.choice = QUAD_EQ;
	}

	public void submit() {
		this.equation1 = eq1.getText();
		this.equation2 = eq2.getText();
		this.equation3 = eq3.getText();
		
		if (this.choice == LIN_SYS) {

		ArrayList<String> data = new ArrayList<String>();
		data.add(this.equation1);
		data.add(this.equation2);
		data.add(this.equation3);

		SystemSolver s = new SystemSolver(data);
		output.setText(s.solve());

		} else if (this.choice == LIN_EQ) {
			LinearSolver ls = new LinearSolver(this.equation1);
			output.setText(ls.solve());
		} else if (this.choice == QUAD_EQ) {
			QuadraticSolver qs = new QuadraticSolver(this.equation1);
			output.setText(qs.solve());
		}
	}

	public static void main(String[] args) {
		App app = new App();
	}
}