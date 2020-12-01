package hyko.servercore.events.Hub;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.janderup.inventory.api.InventoryGUI;
import com.janderup.inventory.api.InventoryItem;
import com.janderup.inventory.api.InventoryListener;
import hyko.servercore.ServerCore;
import hyko.servercore.ServerID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GameSelectEvent implements Listener {

    private final ServerCore plugin;

    public GameSelectEvent(ServerCore plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void event(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (ServerCore.isPlayerOnRequiredServer(p) != ServerID.HUB) {
            return;
        }
        if (p.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                final InventoryGUI gameSelector = new InventoryGUI("Game Selector", 27);

                plugin.getCount(p, "Creative");

                ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta meta = placeholder.getItemMeta();
                assert meta != null;
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.setDisplayName(ChatColor.GRAY + "Game Selector");
                placeholder.setItemMeta(meta);

                ItemStack creativeServer = new ItemStack(Material.WHITE_WOOL);
                ItemMeta creativeServerItemMeta = creativeServer.getItemMeta();
                assert creativeServerItemMeta != null;
                creativeServerItemMeta.setDisplayName(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + ChatColor.BOLD + "Creative" + ChatColor.DARK_GRAY + " -");
                creativeServerItemMeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------", ChatColor.GREEN + "Creative Server " + ChatColor.GOLD + "(1.16.4)", ChatColor.YELLOW + "Click to go to the creative server!", "\n", ChatColor.DARK_GRAY + "Build your ideas on your own private plot", ChatColor.DARK_GRAY + "and view other players builds.", "\n", ChatColor.GRAY + "# of Players: " + ChatColor.YELLOW + plugin.numOfPlayers[0], ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------"));
                creativeServer.setItemMeta(creativeServerItemMeta);

                ItemStack comingSoon = new ItemStack(Material.GRAY_DYE);
                ItemMeta comingSoonItemMeta = comingSoon.getItemMeta();
                assert comingSoonItemMeta != null;
                comingSoonItemMeta.setDisplayName(ChatColor.DARK_GRAY + "- " + ChatColor.RED + ChatColor.BOLD + "Coming Soon" + ChatColor.DARK_GRAY + " -");
                comingSoonItemMeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------", ChatColor.GREEN + "To be released in a future update.", ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------"));
                comingSoonItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                comingSoon.setItemMeta(comingSoonItemMeta);

                ItemStack comingSoon2 = comingSoon.clone();

                for (int i = 0; i < 27; i++) {
                    if (i % 2 == 0) {
                        ItemStack placeholder2 = placeholder.clone();
                        placeholder2.setType(Material.GRAY_STAINED_GLASS_PANE);
                        gameSelector.setItem(i, new InventoryItem(placeholder2, InventoryListener.cancelEvent()));
                        continue;
                    }
                    gameSelector.setItem(i, new InventoryItem(placeholder, InventoryListener.cancelEvent()));
                }
                gameSelector.setItem(11, new InventoryItem(comingSoon, InventoryListener.cancelEvent()));
                gameSelector.setItem(13, new InventoryItem(creativeServer, action -> {
                    action.setCancelled(true);
                    p.sendMessage(ChatColor.GREEN + "Going to Creative Server...");

                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF("Creative");

                    p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                }));
                comingSoon2.setType(Material.GRAY_DYE);
                gameSelector.setItem(15, new InventoryItem(comingSoon2, InventoryListener.cancelEvent()));

                gameSelector.openInventory(p);
            }
        }
    }

}
