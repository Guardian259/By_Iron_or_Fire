package guardian.by_iron_or_fire.util.proxy;


import guardian.by_iron_or_fire.client.renderer.entity.layers.LayerDynamicName;
import guardian.by_iron_or_fire.client.renderer.entity.layers.LayerLeatheredElytra;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ClientProxy extends ServerProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

        //renders the Elytra model for chestplates with the tag `Leathered Elytra` on players; no actual functionality
        for(RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new LayerLeatheredElytra(playerRender));
        }


        // renders the Elytra model for chestplates with the tag `Leathered Elytra` on armor stands; no actual functionality
        Render<Entity> renderObject =  Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityArmorStand.class);
        if(renderObject instanceof RenderLivingBase) {
            ((RenderLivingBase<?>) renderObject).addLayer(new LayerLeatheredElytra(((RenderLivingBase) renderObject)));
        }

        /*
         * adds a new instance of the custom Nameplate into the rendering system
         * should only render in MP, as that's the only time names are generated, but currently non-functional
         */
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new LayerDynamicName(playerRender));
        }

    }

    @Override
    public void postinit(FMLPostInitializationEvent event) {

    }

    //item RegistryClient-Side
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

    }

}
