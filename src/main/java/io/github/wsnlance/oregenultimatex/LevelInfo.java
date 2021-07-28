package io.github.wsnlance.oregenultimatex;

import java.util.HashMap;

public class LevelInfo {
	double need = 0;
	HashMap<String, Double> product;
	
	double getNeed() {
		return this.need;
	}
	
	void setNeed(double need) {
		this.need = need;
	}
	
	HashMap<String, Double> getProduct(){
		return product;
	}
}
