package com.gardensmc.wings.spigot.player;

import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.spigot.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpigotPlayer extends GardensPlayer {

    private final Player player;

    public SpigotPlayer(Player player) {
        super(player.getUniqueId(), player.getName());
        this.player = player;
    }

    @Override
    public boolean isGliding() {
        return player.isGliding();
    }

    @Override
    public void setGliding(boolean isGliding) {
        player.setGliding(isGliding);
    }

    // todo - use nbt
    @Override
    public boolean hasWingsEquipped() {
        ItemStack chestPlate = player.getInventory().getChestplate();
        return chestPlate != null
                && chestPlate.getItemMeta() != null
                && chestPlate.getItemMeta().equals(ItemManager.wings.getItemMeta());
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    // todo allow custom type
    @Override
    public void spawnParticle(int count) {
        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), count);
    }

    // todo allow custom sound type
    @Override
    public void playSound() {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
    }

    @Override
    public void setVelocityMultiplier(double multiplier) {
        player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(multiplier));
    }

    @Override
    public boolean isChestplateSlotEmpty() {
        return player.getInventory().getChestplate() == null;
    }

    @Override
    public void addWingsToChestplateSlot() {
        player.getInventory().setChestplate(ItemManager.wings);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    // todo return a location instead of just pitch?
    @Override
    public float getPitch() {
        return player.getLocation().getPitch();
    }
}
