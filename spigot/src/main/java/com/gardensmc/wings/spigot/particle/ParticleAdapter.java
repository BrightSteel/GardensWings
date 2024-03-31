package com.gardensmc.wings.spigot.particle;

import com.gardensmc.wings.common.particle.GardensParticle;
import org.bukkit.Particle;

public class ParticleAdapter {

    public static Particle adaptParticle(GardensParticle.ParticleType particleType) {
        return Particle.valueOf(particleType.getSpigotName());
    }
}
