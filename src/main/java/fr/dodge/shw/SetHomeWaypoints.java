package fr.dodge.shw;

import fr.dodge.shw.client.SHWEventHandler;
import fr.dodge.shw.command.CommandHome;
import fr.dodge.shw.command.CommandSetHome;
import fr.dodge.shw.command.CommandWaypoint;
import fr.dodge.shw.config.SHWConfiguration;
import fr.dodge.shw.network.SHWPacketHandler;
import fr.dodge.shw.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = Reference.MINECRAFT_VERSION)
public class SetHomeWaypoints {

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY, modId = Reference.MODID)
    public static ServerProxy proxy;

    public SetHomeWaypoints() {
        MinecraftForge.EVENT_BUS.register(SHWEventHandler.class);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SHWPacketHandler.registerMessages(Reference.MODID);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        if (SHWConfiguration.ENABLE) {
            if (SHWConfiguration.HOME.enable) {
                event.registerServerCommand(new CommandHome());
                event.registerServerCommand(new CommandSetHome());
            }

            if (SHWConfiguration.WAYPOINTS.enable) {
                event.registerServerCommand(new CommandWaypoint());
            }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.register();
        ConfigManager.sync(Reference.MODID, Type.INSTANCE);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
