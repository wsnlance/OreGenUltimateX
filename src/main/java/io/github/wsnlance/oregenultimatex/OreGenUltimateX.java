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
	@Override  //������java��һ��ע�⣬�����������onEnable�Ƿ���д
    public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		getServer().getPluginManager().registerEvents(this, this);
		
        // �������ʱҪִ�еĴ��루�ԣ�
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("ore")) { // ������������/basic��ִ����������...
	    	Block b = getServer().getWorld("Bskyblock_world").getBlockAt(802, 85, 0);
	    	getLogger().info(b.toString());
	    	getLogger().info(b.getType().toString());
	        getLogger().info(b.getLocation().toString());
	    	
	        return true;
	    } //����������ݳɹ�ִ�У��򷵻�true 
	    // ���ִ��ʧ�ܣ��򷵻�false.
	    return false;
	}
	
    @EventHandler
    public void onStoneGen(BlockFormEvent event) {
    	new OreGen(this, event.getBlock());
    	BukkitTask task = new OreGen(this, event.getBlock()).runTaskLater(this, 1);
    }
}
