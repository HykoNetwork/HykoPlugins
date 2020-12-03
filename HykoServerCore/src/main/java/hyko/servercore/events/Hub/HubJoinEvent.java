package hyko.servercore.events.Hub;

import hyko.servercore.ServerCore;
import hyko.servercore.ServerID;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class HubJoinEvent implements Listener {

    @EventHandler
    public void event(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        if (ServerCore.getPlayerServerID(p) != ServerID.HUB) {
            return;
        }
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setInvulnerable(true);
        p.setExp(0);
        p.setLevel(0);
        p.setWalkSpeed(0.2F);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
        p.getInventory().clear();
        p.teleport(new Location(p.getWorld(), 0, 60, 0));
        p.setGameMode(GameMode.ADVENTURE);
        // Compass Selector
        ItemStack gameSelector = new ItemStack(Material.COMPASS);
        ItemMeta gameSelectorMeta = gameSelector.getItemMeta();
        assert gameSelectorMeta != null;
        gameSelectorMeta.setDisplayName(ChatColor.GREEN + "Game Selector" + ChatColor.GRAY + " (Right Click)");
        gameSelectorMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right-Click to open game selector."));
        gameSelectorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        gameSelector.setItemMeta(gameSelectorMeta);
        p.getInventory().addItem(gameSelector);

    }
}
