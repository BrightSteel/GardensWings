package com.gardensmc.wings.spigot.wings;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.common.util.ChatUtil;
import de.tr7zw.changeme.nbtapi.*;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTList;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WingsFactory {

    public static ItemStack createWings() {
        WingsConfig wingsConfig = GardensWings.wingsConfig;
        ItemStack itemStack = new ItemStack(Material.ELYTRA);
        // nbt - writes to item
        NBT.modify(itemStack, nbt -> {
            ReadWriteNBT displayCompound = nbt.getOrCreateCompound("display");
            displayCompound.setString("Name", ChatUtil.translateMiniMessageToJson(wingsConfig.getWingsDisplayName()));
            ReadWriteNBTList<String> nbtLore = displayCompound.getStringList("Lore");
            nbtLore.addAll(wingsConfig.getWingsLore()
                    .stream()
                    .map(ChatUtil::translateMiniMessageToJson)
                    .toList()
            );
            nbt.setBoolean(GardensWings.WINGS_IDENTIFIER, true);
        });
        // metadata
        ItemMeta meta = itemStack.getItemMeta();
        if (wingsConfig.isWingsUnbreakable()) {
            meta.setUnbreakable(true);
        }
        // enchant glint
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        // save metadata
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
