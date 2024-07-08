package com.zeddevstuff.bouncer;

import java.util.ArrayList;
import java.util.List;

public final class Bouncer
{
    public static final String MOD_ID = "bouncer";
    private static List<ModInfo> mods = new ArrayList<>();

    public static void init() {
        me.shedaniel.architectury.platform.Platform.getMods().forEach((mod) -> {
            Bouncer.addMod(new ModInfo(mod.getModId(), mod.getName(), mod.getVersion()));
        });
        // Write common init code here.
    }
    public static void addMod(ModInfo mod) {
        System.out.println("Adding mod: " + mod.MOD_NAME + " (" + mod.MOD_ID + ") v" + mod.MOD_VERSION);
        mods.add(mod);
    }
}
