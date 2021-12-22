package com.minecraftgardens.wings.events;

import com.minecraftgardens.wings.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class WingsEvents implements Listener {

    // in flight shift boost
    @EventHandler
    public static void onSneakToggle(PlayerToggleSneakEvent e) {

        Player player = e.getPlayer();
        if (e.isSneaking() && player.isGliding()) {
            ItemStack chestPlate = player.getInventory().getChestplate();

            if (chestPlate != null && chestPlate.getItemMeta().equals(ItemManager.wings.getItemMeta())) {
                if (player.hasPermission("gardens.wings"))
                    player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 5);
                    player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(2.5));
            }
        }

    }

    // jump launcher
    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        double velocity = Math.round(player.getVelocity().getY()*100d) / 100d;
        float pitch = player.getLocation().getPitch();
        ItemStack chestPlate = player.getInventory().getChestplate();

        // player has jump velocity
        if (pitch <= -50 && pitch >= -90 && velocity == 0.42) {
            if (chestPlate != null && chestPlate.getItemMeta().equals(ItemManager.wings.getItemMeta())) {
                if (player.hasPermission("gardens.wings")) {
//                    System.out.println("player velocity: " + velocity);
                    player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 5);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
                    player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(3.0));
                    player.setGliding(true);
                }
            }
        }
    }

    // remove fall and wall damage using wings
    @EventHandler
    public static void entityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            ItemStack chestPlate = player.getInventory().getChestplate();
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL) || e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                if (chestPlate != null && chestPlate.getItemMeta().equals(ItemManager.wings.getItemMeta())) {
                    if (player.hasPermission("gardens.wings")) {
                        e.setCancelled(true);
                    }
                }
        }
    }

    // only allow those with permission to glide with the wings
    @EventHandler
    public static void entityToggleGlide(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            ItemStack chestPlate = player.getInventory().getChestplate();
            if (chestPlate != null && chestPlate.getItemMeta().equals(ItemManager.wings.getItemMeta())) {
                if (!player.hasPermission("gardens.wings")) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You do not have permission to use wings!");
                }
            }
        }
    }
}
