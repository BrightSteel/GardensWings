package com.gardensmc.wings.spigot.listener;

import com.gardensmc.wings.common.listener.*;
import com.gardensmc.wings.common.listener.type.Listener;
import com.gardensmc.wings.spigot.listener.type.SpigotListener;
import com.gardensmc.wings.spigot.user.player.SpigotPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

@SuppressWarnings("unused") // events look unused but are automatically called by Spigot
public class PlayerListener extends SpigotListener {

    @EventHandler
    public void onPlayerElytraFall(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            SpigotPlayer spigotPlayer = new SpigotPlayer(player);
            Listener listener;
            switch (e.getCause()) {
                case FLY_INTO_WALL -> listener = new PlayerElytraCollisionListener(spigotPlayer);
                case FALL -> listener = new PlayerFallDamageListener(spigotPlayer);
                default -> { return; }
            }

            listener.callListener();
            if (listener.isShouldCancel()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerGlide(EntityToggleGlideEvent e) {
        if (e instanceof Player player) {
            PlayerGlideListener glideListener = new PlayerGlideListener(new SpigotPlayer(player));
            glideListener.callListener();
            if (glideListener.isShouldCancel()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSneakJump(PlayerMoveEvent e) {
        double velocity = Math.round(e.getPlayer().getVelocity().getY() * 100d) / 100d;
        if (velocity == 0.42) { // jump velocity when sneaking ?
            new PlayerSneakJumpListener(new SpigotPlayer(e.getPlayer())).callListener();
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        new PlayerSneakListener(new SpigotPlayer(e.getPlayer())).callListener();
    }
}
