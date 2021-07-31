package io.github.wsnlance.oregenultimatex;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

//import org.black_ixx.bossshop.BossShop;
//import org.black_ixx.bossshop.api.BossShopAPI;

//import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import net.milkbowl.vault.economy.Economy;


public final class OreGenUltimateX extends JavaPlugin implements Listener{
	HashMap<String, PlayerInfo> playerInfo = new HashMap<String, PlayerInfo>();
	LevelInfo[] levelInfo;
	Gson gson = new Gson();
	public static Economy economy = null;
	
	@Override
    public void onEnable() {
		this.getCommand("ore").setExecutor(new OreGenUltimateXCommandExecutor(this));
		getServer().getPluginManager().registerEvents(this, this);
		if (!setupEconomy() ) {
			getLogger().info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		load();
    }
	
	@Override
    public void onDisable() {
		save();
    }
	
	@EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
    	String playerName = event.getPlayer().getName();
    	
    	if (!playerInfo.containsKey(playerName)) {
    		getLogger().info("Sign up new player: " + playerName);
    		playerInfo.put(playerName, new PlayerInfo());
    	}
    }
	
    @EventHandler
    public void onStoneGen(BlockFormEvent event) {
    	int level = 0;
    	Block b = event.getBlock();
    	Location l_b = b.getLocation();
    	for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
    		Location l_p = onlinePlayer.getLocation();
    		if (onlinePlayer.getWorld() == b.getWorld() && 
    			Math.abs(l_p.getX() - l_b.getX()) <= 10 && //10 is modifiable
    			Math.abs(l_p.getY() - l_b.getY()) <= 10 &&
    			Math.abs(l_p.getZ() - l_b.getZ()) <= 10) {
    			int playerLevel = playerInfo.get(onlinePlayer.getName()).getLevel();
    			if (playerLevel > level) {
    				level = playerLevel;
    			}
    		}
    	}

    	new OreGen(event.getBlock(), levelInfo[level]).runTaskLater(this, 1);
    }
    
    
    @SuppressWarnings("unchecked")
	private void load() {
    	try {
    		File file = new File("plugins/OreGenUltimateX");
    		if (!file.exists()) {
    			file.mkdir();
    		}
    		
    		FileInputStream fis;
    		BufferedInputStream bis;
    		String json;
    		byte[] buffer = new byte[1024];;
    		int len;
    		
    		//playerinfo
    		file = new File("plugins/OreGenUltimateX/playerinfo.txt");
    		if (file.exists()) {
    			fis=new FileInputStream("plugins/OreGenUltimateX/playerinfo.txt");
                bis=new BufferedInputStream(fis);
                json = "";
                len = 0;
                
                while((len=bis.read(buffer))!=-1){
                	json+=new String(buffer, 0, len);
                }
                
                bis.close();
                playerInfo = gson.fromJson(json, HashMap.class);
    		}

            //levelinfo
            file = new File("plugins/OreGenUltimateX/levelinfo.txt");
    		if (!file.exists()) {
    			HashMap<String, Double> product;
    			
    			getLogger().info("Create default levelinfo");
    			levelInfo = new LevelInfo[3];
    			levelInfo[0] = new LevelInfo();
    			levelInfo[1] = new LevelInfo();
    			levelInfo[1].setNeed(100);
    			product = levelInfo[1].getProduct();
    			product.put("COAL_ORE", 5000.0);
    			levelInfo[2] = new LevelInfo();
    			levelInfo[2].setNeed(500);
    			product = levelInfo[2].getProduct();
    			product.put("COAL_ORE", 5000.0);
    			product.put("IRON_ORE", 2500.0);
    			
    			json = gson.toJson(levelInfo);
    			
    			FileOutputStream fos=new FileOutputStream("plugins/OreGenUltimateX/levelinfo.txt");
                BufferedOutputStream bos=new BufferedOutputStream(fos);
                
                bos.write(json.getBytes(),0,json.getBytes().length);
                bos.flush();
                bos.close();
    		}
    		
    		fis=new FileInputStream("plugins/OreGenUltimateX/levelinfo.txt");
            bis=new BufferedInputStream(fis);
            json = "";
            len = 0;
            
            while((len=bis.read(buffer))!=-1){
            	json+=new String(buffer, 0, len);
            }
            
            bis.close();
            
            levelInfo = gson.fromJson(json, LevelInfo[].class);

    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void save() {
    	try {
    		String json = gson.toJson(playerInfo);
    		
    		File file = new File("plugins/OreGenUltimateX");
    		if (!file.exists()) {
    			file.mkdir();
    		}
    		
    		FileOutputStream fos=new FileOutputStream("plugins/OreGenUltimateX/playerinfo.txt");
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            
            bos.write(json.getBytes(),0,json.getBytes().length);
            bos.flush();
            bos.close();
            
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
