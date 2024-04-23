package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.command.handler.*;
import net.earthmc.xpmanager.util.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BottleCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            XPManagerMessaging.sendErrorMessage(sender, "Only players can use this command");
            return true;
        }

        if (args.length == 0) {
            XPManagerMessaging.sendErrorMessage(player, "No arguments were specified");
            return true;
        }

        String method = args[0];
        switch (method) {
            case "convert":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.convert"))
                    return true;

                ConvertMethodHandler cmh = new ConvertMethodHandler(player);
                cmh.handleMethod();
                break;
            case "get":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.get"))
                    return true;

                GetMethodHandler gmh = new GetMethodHandler(player, args, false);
                gmh.handleMethod();
                break;
            case "mend":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.mend"))
                    return true;

                MendMethodHandler mmh = new MendMethodHandler(player);
                mmh.handleMethod();
                break;
            case "stats":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.stats"))
                    return true;

                StatsMethodHandler smh = new StatsMethodHandler(player);
                smh.handleMethod();
                break;
            case "store":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.store"))
                    return true;

                StoreMethodHandler stmh = new StoreMethodHandler(player, args, false);
                stmh.handleMethod();
                break;
            case "toggle":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.toggle"))
                    return true;

                ToggleMethodHandler tmh = new ToggleMethodHandler(player, args);
                tmh.handleMethod();
                break;
            case "until":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.until"))
                    return true;

                UntilMethodHandler umh = new UntilMethodHandler(player, args);
                umh.handleMethod();
                break;
            default:
                XPManagerMessaging.sendErrorMessage(player, "Invalid first argument: " + method + ". Valid first arguments are {convert/get/mend/stats/store/toggle/until}");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                return List.of("convert", "get", "mend", "stats", "store", "toggle", "until");

            case 2:
                switch (args[0]) {
                    case "get": return List.of("max", "{quantity}");
                    case "store": return List.of("max", "{amount}");
                    case "toggle": return List.of("thrown");
                    case "until": return List.of("{level}");
                }

            case 3:
                if (args[0].equals("store")) {
                    return List.of("max", "{quantity}");
                }
        }

        return null;
    }
}
