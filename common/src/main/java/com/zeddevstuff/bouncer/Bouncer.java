package com.zeddevstuff.bouncer;

import com.zeddevstuff.bouncer.client.BouncerClient;
import com.zeddevstuff.bouncer.utils.Scheduler;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Bouncer
{
    public static final String MOD_ID = "bouncer";
    private static List<ModInfo> mods = new ArrayList<>();
    private static ModFilter modFilter = new ModFilter();
    private static List<String> received = new ArrayList<>();

    public static void init() {
        PlayerEvent.PLAYER_JOIN.register((player) -> {
            Scheduler.getServerScheduler().schedule(() -> {
                if(!received.contains(player.getUUID().toString())) {
                    ((ServerPlayer)player).connection.disconnect(Component.translatable("bouncer.modlist_timeout"));
                } else {
                    received.remove(player.getUUID().toString());
                }
            }, 5.0f);
        });
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, new ResourceLocation(Bouncer.MOD_ID, "modlist"), (buffer, context) -> {
            received.add(context.getPlayer().getUUID().toString());
            int modCount = buffer.readInt();
            for(int i = 0; i < modCount; i++) {
                String modId = buffer.readUtf();
                String modName = buffer.readUtf();
                String modVersion = buffer.readUtf();
                ModInfo mod = new ModInfo(modId, modName, modVersion);
                System.out.println("Mod: " + mod.MOD_ID + " " + mod.MOD_VERSION);
                if(!modFilter.isModAllowed(mod.MOD_ID)) {
                    System.out.println("Kicking player because of mod: " + mod.MOD_ID);
                    ((ServerPlayer)context.getPlayer()).connection.disconnect(Component.translatable("bouncer.forbidden_mod_detected", mod.MOD_NAME));
                }
            }
        });
        TickEvent.Server.SERVER_POST.register(server -> {
            Scheduler.getServerScheduler().tick();
        });
        if(dev.architectury.platform.Platform.getEnv().toString().equals("CLIENT"))
            BouncerClient.init();
        else {
            Path gameFolder = dev.architectury.platform.Platform.getGameFolder();
            var configPath = gameFolder.resolve("bouncer.config");
            if(!configPath.toFile().exists()) {
                System.out.println("Creating bouncer.config file");
                try {
                    configPath.toFile().createNewFile();
                    var wr = new BufferedWriter(new java.io.FileWriter(configPath.toFile()));
                    wr.append("# Add mods to this file to filter them\n# Each line is a different mod in this format 'MOD_ID, MOD_VERSION'\n# Example: modid, 1.0.0\n");
                    wr.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Reading bouncer.config file");
                try {
                    modFilter = ModFilter.fromConfig(java.nio.file.Files.readString(configPath));
                    System.out.println("Loaded " + modFilter.getMods().size() + " mods from config");
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public static FriendlyByteBuf writeModList() {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        buffer.writeInt(mods.size());
        for(ModInfo mod : mods) {
            buffer.writeUtf(mod.MOD_ID);
            buffer.writeUtf(mod.MOD_NAME);
            buffer.writeUtf(mod.MOD_VERSION.source);
        }
        return buffer;
    }
    public static void addMod(ModInfo mod) {
        mods.add(mod);
    }
}