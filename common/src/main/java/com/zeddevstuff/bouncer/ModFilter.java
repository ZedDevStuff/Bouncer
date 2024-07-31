package com.zeddevstuff.bouncer;

import dev.architectury.platform.Mod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModFilter
{
    private Map<String, ModInfo> mods = new HashMap<>();
    public Map<String, ModInfo> getMods() {
        return mods;
    }

    public ModFilter() {
    }
    public ModFilter(Map<String, ModInfo> mods) {
        this.mods = mods;
    }

    public void addMod(ModInfo mod) {
        mods.put(mod.MOD_ID, mod);
    }
    public void removeMod(String modId) {
        mods.remove(modId);
    }
    public boolean isModAllowed(String modId) {
        return !mods.containsKey(modId);
    }

    public static ModFilter fromConfig(String config) {
        Map<String, ModInfo> mods = new HashMap<>();
        String[] lines = config.split("\n");
        for(String line : lines) {
            if(line.startsWith("#"))
                continue;
            // Split the line by commas and remove any leading/trailing whitespace
            String[] parts = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
            if(parts.length != 0) {
                ModInfo mod = null;
                if(parts.length == 1)
                    mod = new ModInfo(parts[0], parts[0], "0.0.0");
                else if(parts.length == 2)
                    mod = new ModInfo(parts[0], parts[0], parts[1]);
                if(mod != null) mods.put(mod.MOD_ID, mod);
            }
        }
        return new ModFilter(mods);
    }
}
