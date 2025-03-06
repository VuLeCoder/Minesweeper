package org.game.Minesweeper;

public class SetAttribute {
	public SetAttribute(int diff) {
		switch (diff) {
	        case 0 :  { Global.setRow(8);  Global.setCol(10); Global.setMines(10); break;}
	        case 1 :  { Global.setRow(12); Global.setCol(16); Global.setMines(30); break;}
	        default : { Global.setRow(18); Global.setCol(21); Global.setMines(80); break;}
		}
	}
}
