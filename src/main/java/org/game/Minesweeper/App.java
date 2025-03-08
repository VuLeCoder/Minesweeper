package org.game.Minesweeper;

public class App {
	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0");
		Global.diff = 1;
		new Game().setGame(Global.diff);
	}
}
