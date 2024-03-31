package com.gardensmc.wings.common.particle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GardensParticle {
    private boolean enabled;
    private ParticleType particleType;
    private int count;
    private double speed;

    @NoArgsConstructor
    @AllArgsConstructor
    public enum ParticleType {

        ASH,
        FLAME,
        BUBBLE_COLUMN_UP,
        BUBBLE_POP,
        CAMPFIRE_COSY_SMOKE,
        CAMPFIRE_SIGNAL_SMOKE,
        CLOUD,
        CRIT,
        ELECTRIC_SPARK,
        ENCHANT("ENCHANT", "ENCHANTMENT_TABLE"),
        EXPLOSION("EXPLOSION", "EXPLOSION_NORMAL"),
        HEART,
        LAVA,
        PORTAL,
        SPELL("WITCH", "SPELL");

        private String fabricName, spigotName;

        public String getFabricName() {
            return fabricName != null ? fabricName : this.name().toUpperCase();
        }

        public String getSpigotName() {
            return spigotName != null ? spigotName : this.name().toUpperCase();
        }
    }
}
