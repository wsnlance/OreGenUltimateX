package io.github.wsnlance.oregenultimatex;

public class PlayerInfo{
	private double level = 0;
	private boolean cobble_stone = true;
	private boolean stone = false;
	
	int getLevel() {
		return (int) this.level;
	}
	
	void setLevel(int level) {
		this.level = level;
	}
	
	boolean getCobbleStone() {
		return this.cobble_stone;
	}
	
	void CobbleStoneOn() {
		this.cobble_stone = true;
	}
	
	void CobbleStoneOff() {
		this.cobble_stone = false;
	}
	
	boolean getStone() {
		return this.stone;
	}
	
	void StoneOn() {
		this.stone = true;
	}
	
	void StoneOff() {
		this.stone = false;
	}
}