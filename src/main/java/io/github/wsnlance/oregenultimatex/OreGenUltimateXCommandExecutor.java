package io.github.wsnlance.oregenultimatex;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OreGenUltimateXCommandExecutor implements CommandExecutor{
	private final OreGenUltimateX plugin;
	
	public OreGenUltimateXCommandExecutor(OreGenUltimateX plugin) {
        this.plugin = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("ore")) {
	    	if (args.length == 0) {
	    		
	    	}else if (args.length == 1) {
	    		if(args[0].equalsIgnoreCase("level")) {
	    			if (sender instanceof Player) {
	    				ore_level(sender);
	    				return true;
	    			} else {
	    				sender.sendMessage("这个指令只能让玩家使用。");
	    			}
	    		}else if (args[0].equalsIgnoreCase("upgrade")) {
	    			if (sender instanceof Player) {
	    				ore_upgrade((Player)sender);
	    				return true;
	    			} else {
	    				sender.sendMessage("这个指令只能让玩家使用。");
	    			}
	    		}else {
	    			
	    		}
	    		
	    	}else if (args.length == 2) {
	    		Player player;
	    		if(args[0].equalsIgnoreCase("level")) {
	    			if((player = Bukkit.getPlayer(args[1])) == null) {
	    				sender.sendMessage("no this player");
	    			} else {
	    				ore_level(player);
	    			}
	    			return true;
	    		}else if (args[0].equalsIgnoreCase("upgrade")) {
	    			if((player = Bukkit.getPlayer(args[1])) == null) {
	    				sender.sendMessage("no this player");
	    			} else {
	    				ore_upgrade(player);
	    			}
	    			return true;
	    		}else {
	    			
	    		}
	    		
	    	}else {
	    		sender.sendMessage("Too many arguments!");
	    	}
	    }
	    
	    return false;
	}
	
	private void ore_level(CommandSender sender) {
		int level = (int) plugin.playerInfo.get(sender.getName()).getLevel();
		sender.sendMessage("Your Ore Generator is in level " + level);
	}
	
	private void ore_upgrade(Player player) {
    	String playerName = player.getName();
    	int level = (int) plugin.playerInfo.get(playerName).getLevel();

    	if(plugin.levelInfo == null || plugin.levelInfo.length - 1 <= level) {
    		player.sendMessage("There is no higher level");
    		return;
    	}

    	if(OreGenUltimateX.economy.getBalance(player) < plugin.levelInfo[level + 1].getNeed()) {
    		player.sendMessage("You don't have enough money.");
    		player.sendMessage("" + plugin.levelInfo[level + 1].getNeed() + " is needed");
    		player.sendMessage("You have " + OreGenUltimateX.economy.getBalance(player));
    		return;
    	}

    	OreGenUltimateX.economy.withdrawPlayer(player, plugin.levelInfo[++level].getNeed());
    	plugin.playerInfo.get(playerName).setLevel(level);
    	player.sendMessage("Your Ore Generator level up. It's in level " + level);
    }
}

