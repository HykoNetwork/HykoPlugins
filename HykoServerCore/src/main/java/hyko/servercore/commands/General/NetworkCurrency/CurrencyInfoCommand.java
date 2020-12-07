package hyko.servercore.commands.General.NetworkCurrency;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.MessageType;
import hyko.servercore.ServerCore;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.net.ServerSocket;
import java.util.ArrayList;

public class CurrencyInfoCommand implements CommandExecutor {
    private ServerCore plugin;
    private HykoConfigMessagesConvert messagesConvert;

    public CurrencyInfoCommand(ServerCore plugin) {
        this.plugin = plugin;
        messagesConvert = new HykoConfigMessagesConvert(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("currencyinfo")) {

            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage(messagesConvert.getMessage(MessageType.badExecute));
                return true;
            }

            Player p = Bukkit.getPlayerExact(args[0]);

            assert p != null;
            if(!args[0].equals(p.getName())) {
                return true;
            }
            if(ServerCore.playersUsingCurrencyInfo.contains(p.getUniqueId())) {
                commandSender.sendMessage(ChatColor.RED + "You cannot use this command right now!");
                return true;
            }


            ServerCore.playersUsingCurrencyInfo.add(p.getUniqueId());
            for(int i = 0; i < 15; i++) {
                assert p != null;
                p.sendMessage("\n");
            }


            int counter = 5;
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, () -> {
                // Do something

            }, 20L, 160L);

            ShowPlayerCurrencyInfo playerCurrencyInfo = new ShowPlayerCurrencyInfo(this.plugin, 5, p);
            playerCurrencyInfo.runTaskTimer(plugin, 160L, 160L);
            playerCurrencyInfo.run();

        }

        return false;
    }
}
class ShowPlayerCurrencyInfo extends BukkitRunnable {

    private final JavaPlugin plugin;

    private int counter;

    private final Player p;

    public ShowPlayerCurrencyInfo(JavaPlugin plugin, int counter, Player p) {
        this.plugin = plugin;
        if (counter <= 0) {
            throw new IllegalArgumentException("counter must be greater than 0");
        } else {
            this.counter = counter;
        }
        this.p = p;
    }

    @Override
    public void run() {//this.cancel()
        // What you want to schedule goes here

        if(counter == 5) {

            p.sendMessage(ChatColor.GREEN + "Network Coins are Hyko Network's specific custom made currency!");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 3.5F, 1.0F);
            --counter;
            p.sendMessage("\n");
            return;
        }
        if(counter == 4) {

            p.sendMessage(ChatColor.GREEN + "Network Coins can be used to purchase ranks, items, cosmetics, and other things from our shop!");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 3.5F, 1.0F);
            --counter;
            p.sendMessage("\n");
            return;
        }
        if(counter == 3) {

            p.sendMessage(ChatColor.GREEN + "Network Coins value varies depending on your gamemode. For example in Hub you can get Hyko Coins to get Cosmetics while in Creative you can use them to unlock upgrades to you plot!");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 3.5F, 1.0F);
            --counter;
            p.sendMessage("\n");
            return;
        }
        if(counter == 2) {

            TextComponent message = new TextComponent("Network Coins can be purchased at our BuyCraft. ");
            message.setColor(net.md_5.bungee.api.ChatColor.GREEN);

            TextComponent message2 = new TextComponent("CLICK HERE");
            message2.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
            message2.setBold(true);
            message2.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.buycraft.org" ) );
            message2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text( "Visit our Server Store!" ) ) );
            message.addExtra(message2);

            p.spigot().sendMessage(message);
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 3.5F, 1.0F);
            p.sendMessage("\n");
            --counter;
            return;
        }
        if(counter == 1) {

            p.sendMessage(ChatColor.GREEN + "Network coins can also be gained by completing Achievements and simply just playing on our server!");
            p.sendMessage("\n");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 3.5F, 1.0F);
            --counter;
            return;
        }
        if(counter == 0) {
            ServerCore.playersUsingCurrencyInfo.remove(p.getUniqueId());
            p.sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.ITALIC + "Thank you for reading about Network Coins!");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 3.5F, 1.0F);
            this.cancel();
        }

    }
}
