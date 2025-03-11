package org.game.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class FancyRoundButton extends JButton {
    private Color color = new Color(52, 168, 83); // Màu khi nhấn (Xanh lá)

    public FancyRoundButton() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        
        if (getModel().isArmed()) {
            g2.setColor(Color.BLACK); // Màu khi nhấn
        } else {
            g2.setColor(getBackground()); // Màu nền bình thường
        }
        g2.fillOval(0, 0, width, height);

        // Viền
        g2.setColor(color);
        g2.setStroke(new BasicStroke(4));
        g2.drawOval(1, 1, width - 3, height - 3);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không cần viền, vì viền đã được vẽ trong paintComponent
    }

    @Override
    public boolean contains(int x, int y) {
        Ellipse2D circle = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        return circle.contains(x, y);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Fancy Round Button");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 300);
//        frame.setLayout(new FlowLayout());
//
//        FancyRoundButton roundButton = new FancyRoundButton("Press Me");
//        roundButton.setPreferredSize(new Dimension(100, 100));
//        
//        frame.add(roundButton);
//        frame.setVisible(true);
//    }
}

