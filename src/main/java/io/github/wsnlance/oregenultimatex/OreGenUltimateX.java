package io.github.wsnlance.oregenultimatex;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class OreGenUltimateX extends JavaPlugin implements Listener{
	@Override  //这里是java的一种注解，用来检测下面onEnable是否被重写
    public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		getServer().getPluginManager().registerEvents(this, this);
		
        // 插件载入时要执行的代码（略）
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("ore")) { // 如果玩家输入了/basic则执行如下内容...
	    	Block b = getServer().getWorld("Bskyblock_world").getBlockAt(802, 85, 0);
	    	getLogger().info(b.toString());
	    	getLogger().info(b.getType().toString());
	        getLogger().info(b.getLocation().toString());
	    	
	        return true;
	    } //如果以上内容成功执行，则返回true 
	    // 如果执行失败，则返回false.
	    return false;
	}
	
    @EventHandler
    public void onStoneGen(BlockFormEvent event) {
    	new OreGen(this, event.getBlock());
    	BukkitTask task = new OreGen(this, event.getBlock()).runTaskLater(this, 1);
    }
}
