package me.xsenny.msbox.history;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.xsenny.msbox.staffUtilities.Staff;
import me.xsenny.msbox.utils.PaginatedMenus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class HistoryMenu extends PaginatedMenus {


    public HistoryMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "History";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {

    }

    @Override
    public void setMenuItems() {
        addMenuBorder1();
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Staff.getStaff(p).getHistory().getDisplayName() + "'s history:");
        item.setItemMeta(meta);
        inventory.setItem(4, item);
        ArrayList<History> list = HistoryMethods.getHistoryOfPlayer(Staff.getStaff(p).getHistory().getUniqueId().toString());
        if (!list.isEmpty()){
            for (int i = 0; i < getMaxItemsPerPage1(); i++) {
                index = getMaxItemsPerPage1() * page + i;
                if (index >= list.size()) break;
                if (list.get(index) != null){
                    ItemStack item1 = new ItemStack(Material.PAPER);
                    ItemMeta itemMeta = item1.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GREEN+list.get(index).getWhen());
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "-----------------------");
                    lore.add(ChatColor.GRAY + "Time: " + ChatColor.GREEN + list.get(index).getHowTime());
                    lore.add(ChatColor.GRAY + "Type: " + ChatColor.GREEN + list.get(index).getType());
                    lore.add(ChatColor.GRAY + "Reason: " + ChatColor.GREEN + list.get(index).getReason());
                    lore.add(ChatColor.GRAY + "-----------------------");
                }
            }
        }
        for (int i = 0; i < getMaxItemsPerPage1(); i++){
            if (inventory.getItem(i) == null){
                inventory.setItem(i, makeItem(Material.BLACK_STAINED_GLASS_PANE, " "));
            }
        }
    }
}
