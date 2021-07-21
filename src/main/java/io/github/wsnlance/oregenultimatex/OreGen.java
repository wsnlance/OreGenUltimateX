package io.github.wsnlance.oregenultimatex;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class OreGen extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final Block b;

    public OreGen(JavaPlugin plugin, Block b) {
        this.plugin = plugin;
        this.b = b;
    }

    @Override
    public void run() {
    	plugin.getLogger().info(b.getType().toString());
        b.setType(Material.SAND);
    }
}
