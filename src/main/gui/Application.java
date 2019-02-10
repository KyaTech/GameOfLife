package main.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import main.constant.Constants;
import main.game.Logic;

public class Application extends JFrame implements ActionListener {

	private Logic gameLogic = new Logic();
	private Board board;
	private Timer timer;

	public Application() {
		initUI();

		timer = new Timer(Constants.DELAY, this);
	}

	private void initUI() {
		int propSpace = 70;
		setSize(Constants.WIDTH, Constants.HEIGHT + propSpace);
		setLayout(null);

		board = new Board(gameLogic);
		board.setBounds(0, 0, Constants.WIDTH, Constants.HEIGHT);
		add(board);

		JPanel propPanel = new JPanel();
		propPanel.setBackground(Color.white);
		propPanel.setBounds(Constants.WIDTH / 4, Constants.HEIGHT - propSpace, Constants.WIDTH / 2, propSpace);
		propPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(propPanel);

		JButton restart = new JButton("Restart");
		restart.setBounds(10, 10, (int) ((propPanel.getWidth() - 20) * 0.2), propPanel.getHeight() - 20);
		restart.addActionListener(e -> {
			gameLogic = new Logic();
			board.updateGameLogic(gameLogic);
		});
		propPanel.add(restart);

		JSlider speedSlider = new JSlider(1,1000, (int) Constants.FPS);
		speedSlider.setBounds((int)((propPanel.getWidth() - 20)  * 0.2) + 10,10,(int)((propPanel.getWidth() - 20)  * 0.8) - 10, propPanel.getHeight() - 20);
		speedSlider.addChangeListener(e -> {
			Constants.DELAY = Math.round(1000f / speedSlider.getValue());
			timer.setDelay(Constants.DELAY);
		});
		propPanel.add(speedSlider);




		setTitle("Game of Life");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent evt) {
				Component c = (Component)evt.getSource();
				board.setSize(c.getWidth(),c.getHeight() - propSpace);
				propPanel.setBounds((c.getWidth() - (Constants.WIDTH / 2)) / 2, c.getHeight() - propSpace,Constants.WIDTH / 2, propSpace);
			}
		});
	}

	public void startGame() {
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameLogic.nextFrame();
		board.nextFrame();
	}
}
