package hyko.servercore;

import net.md_5.bungee.api.ChatColor;

public class HykoStatic {
    /*
    Class for static variables for Hyko Network Core
     */
    public static final String badExecute = ChatColor.RED + "[HykoNetworkCore] Please execute this command in-game...";
    public static final String badPermission = ChatColor.RED + "[HykoNetwork] You do not have access to perform this command.";

    // Player must be on Hub server to execute this command.
    public static final String badServer_Hub = ChatColor.RED + "[HykoNetwork] You must be on the Hub server to execute this command!";

    // The player must be on creative server to execute this command.
    public static final String badServer_Creative = ChatColor.RED + "[HykoNetwork] You must be on the Creative server to execute this command!";
}
