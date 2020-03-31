package guardian.byironorfire.util.handlers;

import guardian.byironorfire.ByIronOrFire;
//import guardian.byironorfire.client.gui.SmithyContainer;
//import guardian.byironorfire.client.renderer.entity.layers.LayerLeatheredElytra;
import net.minecraft.block.Block;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class SideClientHandler {

    /**
     * This handles the application of the Elytra's models onto both the <code>PlayerRenderer</code> and <code>ArmorStandEntity</code>
     * when using leather and chained leather variants
     */
    public static void clientRegistries(FMLClientSetupEvent event){
        ByIronOrFire.logger.info("By Iron Or Fire Initializing Client");

        /**renders the Elytra models for chestplates with the tag `Leathered Elytra` on players; no actual functionality, yet
        for(PlayerRenderer playerRender : Minecraft.getInstance().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new LayerLeatheredElytra(playerRender));
        }

        // renders the Elytra models for chestplates with the tag `Leathered Elytra` on armor stands; no actual functionality
        //this feels incomplete/ incorrectly ported to 1.14.4; ask before pushing!!!!
        EntityRenderer renderObject =  Minecraft.getInstance().getRenderManager().getRenderer(ArmorStandEntity.class);
        if(renderObject instanceof EntityRenderer) {
            ((LivingRenderer<?,?>) renderObject).addLayer(new LayerLeatheredElytra(((LivingRenderer) renderObject)));
        }
        */

        ByIronOrFire.logger.info("By Iron Or Fire Client Initialized, Success!");
    }

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event){
        if(event.getItemStack().hasTag()) {
            /**shows there is a totem attached*/
            if (event.getItemStack().getTag().hasUniqueId("totemed")) {
                //Former 1.12 code Depreciated
                //event.getToolTip().add(TextFormatting.GOLD + " Soulbound");

                //this really should be using TranslationTextComponent but i am unsure how to implement atm
                event.getToolTip().add(new StringTextComponent(TextFormatting.GOLD + " Soulbound"));
            }
            /**shows there is an elytra attached*/
            else if(event.getItemStack().getTag().hasUniqueId("attached_elytra")) {
                //Former 1.12 code Depreciated
                //event.getToolTip().add(TextFormatting.GOLD + " Attached Elytra");

                //this really should be using TranslationTextComponent but i am unsure how to implement atm
                event.getToolTip().add(new StringTextComponent(TextFormatting.GOLD + " Attached Elytra"));
            }
        }
    }

}
