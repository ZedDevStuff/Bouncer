package com.zeddevstuff.bouncer;

import com.zeddevstuff.bouncer.utils.Version;

public class ModInfo
{
    public String MOD_ID = "bouncer";
    public String MOD_NAME = "Bouncer";
    public Version MOD_VERSION = new Version("1.0.0");

    public ModInfo(String id, String name, String version) {
        this.MOD_ID = id;
        this.MOD_NAME = name;
        this.MOD_VERSION = new Version(version);
    }
}
