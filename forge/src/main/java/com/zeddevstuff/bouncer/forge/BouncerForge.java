package com.zeddevstuff.bouncer.forge;

import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.zeddevstuff.bouncer.Bouncer;

@Mod(Bouncer.MOD_ID)
public final class BouncerForge
{
    public BouncerForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Bouncer.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Bouncer.init();
    }
}
