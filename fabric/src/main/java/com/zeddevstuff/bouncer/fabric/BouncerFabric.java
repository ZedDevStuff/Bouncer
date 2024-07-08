package com.zeddevstuff.bouncer.fabric;

import com.zeddevstuff.bouncer.ModInfo;
import net.fabricmc.api.ModInitializer;

import com.zeddevstuff.bouncer.Bouncer;
import net.fabricmc.loader.api.FabricLoader;

public final class BouncerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Bouncer.init();
    }
}
