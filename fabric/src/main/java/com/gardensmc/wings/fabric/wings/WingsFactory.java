package com.gardensmc.wings.fabric.wings;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.config.WingsConfig;
import com.gardensmc.wings.common.util.ChatUtil;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.stream.Collectors;

public class WingsFactory {

    public static ItemStack createWings() {
        WingsConfig wingsConfig = GardensWings.wingsConfig;
        ItemStack itemStack = new ItemStack(Items.ELYTRA);
        // properties
        NbtCompound nbt = itemStack.getOrCreateNbt();
        nbt.putBoolean(GardensWings.WINGS_IDENTIFIER, true);
        if (wingsConfig.isWingsUnbreakable()) {
            nbt.putBoolean("Unbreakable", true);
        }
        NbtCompound displayNbt = new NbtCompound();
        // lore
        displayNbt.put("Lore", wingsConfig.getWingsLore()
                .stream()
                .map(s -> NbtString.of(ChatUtil.translateMiniMessageToJson(s)))
                .collect(Collectors.toCollection(NbtList::new))
        );
        // name
        displayNbt.put("Name", NbtString.of(ChatUtil.translateMiniMessageToJson(wingsConfig.getWingsDisplayName())));
        // save display nbt
        nbt.put("display", displayNbt);
        // enchant glint
        nbt.putInt("HideFlags", 1); // hide enchants
        itemStack.addEnchantment(Enchantments.UNBREAKING, 1);
        return itemStack;
    }
}
