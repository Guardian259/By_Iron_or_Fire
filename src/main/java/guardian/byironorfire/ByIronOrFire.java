package guardian.byironorfire;

import guardian.byironorfire.util.handlers.SideClientHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("bif")
public class ByIronOrFire {

    public static ByIronOrFire instance;
    public static String modId = "bif";

    public static final Logger logger = LogManager.getLogger(modId);

    public ByIronOrFire(){

        instance = this;

        logger.info("By Iron Or Fire Initializing Mod");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideClientHandler::clientRegistries);


        MinecraftForge.EVENT_BUS.register(this);
    }
}