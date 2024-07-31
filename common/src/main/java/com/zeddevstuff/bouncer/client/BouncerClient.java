package com.zeddevstuff.bouncer.client;

import com.zeddevstuff.bouncer.Bouncer;
import com.zeddevstuff.bouncer.ModInfo;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import static com.zeddevstuff.bouncer.Bouncer.MOD_ID;

public class BouncerClient
{
    public static void init()
    {
        dev.architectury.platform.Platform.getMods().forEach((mod) -> {
            Bouncer.addMod(new ModInfo(mod.getModId(), mod.getName(), mod.getVersion()));
        });
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register((player) -> {
            FriendlyByteBuf data = Bouncer.writeModList();
            NetworkManager.sendToServer(new ResourceLocation(MOD_ID, "modlist"), data);
        });
    }
}
