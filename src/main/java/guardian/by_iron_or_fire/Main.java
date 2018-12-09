package guardian.by_iron_or_fire;

import guardian.by_iron_or_fire.util.References;
import guardian.by_iron_or_fire.util.handlers.GuiHandler;
import guardian.by_iron_or_fire.util.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static net.minecraftforge.fml.common.Mod.EventHandler;
import static net.minecraftforge.fml.common.Mod.Instance;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION, acceptedMinecraftVersions = References.ACCEPTED_VERSIONS)
public class Main {

    @Instance
    public static Main instance;


    @SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY)
    public static ServerProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){

        proxy.preInit(event);

    }

    @EventHandler
    public void init(FMLInitializationEvent event){

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        proxy.init(event);


    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event){

        proxy.postinit(event);

    }
}
