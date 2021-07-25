package io.github.wsnlance.oregenultimatex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
//import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class OreGen extends BukkitRunnable {

    //private final JavaPlugin plugin;
    private final Block b;
    private final Map<String, Double> levelInfo;
    

    public OreGen(Block b, HashMap<String, Double> levelInfo) {
        //this.plugin = plugin;
        this.b = b;
        this.levelInfo = levelInfo;
    }

    @Override
    public void run() {
    	//测试是否对圆石或石头生成
    	//待做
    	try {
    		Random r = new Random();
        	int i = r.nextInt(10000) + 1;
        	Iterator<Entry<String, Double>> iter = levelInfo.entrySet().iterator();
        	while(iter.hasNext()) {
        		Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iter.next();
        		double prob = entry.getValue();
        		//to be changed
        		i -= prob;
        		if (i <= 0) {
        			String materialType = entry.getKey();
        			b.setType(Material.valueOf(materialType));
        			return;
        		}
        	}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
