package com.gardensmc.wings.spigot.player;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.particle.GardensParticle;
import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.util.ChatUtil;
import com.gardensmc.wings.spigot.WingsSpigot;
import com.gardensmc.wings.spigot.particle.ParticleAdapter;
import com.gardensmc.wings.spigot.wings.WingsFactory;
import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.*;
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

    @Override
    public boolean hasWingsEquipped() {
        ItemStack chestPlate = player.getInventory().getChestplate();
        return chestPlate != null
                && chestPlate.getType() == Material.ELYTRA
                && chestPlate.getItemMeta() != null
                && NBT.readNbt(chestPlate).hasTag(GardensWings.WINGS_IDENTIFIER);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.isOp() || player.hasPermission(permission);
    }

    @Override
    public void spawnParticle(GardensParticle particle) {
        player.getWorld().spawnParticle(
                ParticleAdapter.adaptParticle(particle.getParticleType()),
                player.getLocation(),
                particle.getCount()
        );
    }

    @Override
    public void playSound(float volume) {
        player.getWorld().playSound(
                player.getLocation(),
                Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,
                volume,
                1.0f
        );
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
        player.getInventory().setChestplate(WingsFactory.createWings());
    }

    @Override
    public void addWingsToInventory() {
        player.getInventory().addItem(WingsFactory.createWings());
    }

    @Override
    public void sendMessage(String message) {
        WingsSpigot.getAdventure().player(player).sendMessage(ChatUtil.translateMiniMessage(message));
    }

    @Override
    public float getPitch() {
        return player.getLocation().getPitch();
    }
}
