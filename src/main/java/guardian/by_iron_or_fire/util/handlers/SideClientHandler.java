package guardian.by_iron_or_fire.util.handlers;

import guardian.by_iron_or_fire.client.gui.GuiAnvilToggle;
import guardian.by_iron_or_fire.init.ItemInit;
import guardian.by_iron_or_fire.item.DyeableArmor;
import guardian.by_iron_or_fire.util.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.List;


/**
 * Special thanks to Phanta,UpcraftLP,bright_spark,and aidie8 for there help on this code and patience!!!
 */

@Mod.EventBusSubscriber(modid = References.MODID, value= net.minecraftforge.fml.relauncher.Side.CLIENT)
public class SideClientHandler {

    static GuiAnvilToggle anvilToggle;
    public static Gui m;
    public static EntityPlayer p;
    public static World w;
    public static BlockPos posAnvil;

    final static int ANVILTOGGLE = 0;

    @SubscribeEvent
    public static void onAnvilActivated(PlayerInteractEvent event){

        Block thisBlock = Minecraft.getMinecraft().world.getBlockState(event.getPos()).getBlock();

        if (thisBlock instanceof BlockAnvil) {
            posAnvil = event.getPos();
        }
    }

    @SubscribeEvent
    public static void onGuiActivation(GuiScreenEvent.InitGuiEvent.Post event){

        m = event.getGui();
        p = Minecraft.getMinecraft().player;
        w = Minecraft.getMinecraft().world;
        int centerX = (event.getGui().width / 2)- 88;
        int centerY = (event.getGui().height / 2)- 83;

        /**An addition of a button into the Anvil's GUI*/
         if(m instanceof GuiRepair){
            event.getButtonList().add(anvilToggle = new GuiAnvilToggle(ANVILTOGGLE,(centerX + 15),(centerY + 5),""));
         }

    }

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event){
        if(event.getItemStack().hasTagCompound()) {
            /**shows there is a totem attached*/
            if (event.getItemStack().getTagCompound().hasKey("totemed")) {
                event.getToolTip().add(TextFormatting.GOLD + " Soulbound");
            }
            /**shows there is an elytra attached*/
            else if(event.getItemStack().getTagCompound().hasKey("attached_elytra")) {
                event.getToolTip().add(TextFormatting.GOLD + " Attached Elytra");
            }
        }
    }

    /**
     * i'm not sure what i expect these to do here... hopping to add elytra functionality to armor pieces
     * the desired flag to set is protected....
    @SubscribeEvent
    public static void onPlayerAttachedElytra (PlayerFlyableFallEvent event){
        List playerArmorInv = event.getEntityPlayer().inventory.armorInventory;
        if(event.getEntityPlayer().isAirBorne){
            for(int i =0; i < playerArmorInv.size(); i++) {
                hasAttachedEyltra(event.getEntityPlayer().inventory.armorInventory.get(i).getItem(),event.getEntity());
            }
        }
    }

    public static void hasAttachedEyltra(Item item, Entity entity){
        ItemStack thisStack = item.getDefaultInstance();
        ItemArmor.ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();
        if((item instanceof ItemArmor && ((ItemArmor) item).armorType == EntityEquipmentSlot.CHEST) && thisStack.hasTagCompound()){
            if((material == ItemArmor.ArmorMaterial.LEATHER || material == ItemInit.CHAINED_LEATHER) && thisStack.getTagCompound().hasKey("attached_elytra")){
                //entity.setFlag(7,true);
            }
        }
    }
    */


    @SubscribeEvent
    public static void onItemColorRegister (ColorHandlerEvent.Item event){

        event.getItemColors().registerItemColorHandler((new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if(tintIndex == 0){
                    return ((DyeableArmor)stack.getItem()).getColor(stack);
                }else{
                    return -1;
                }
            }
        }),ItemInit.CHAINED_LEATHER_BOOTS,ItemInit.CHAINED_LEATHER_LEGGINGS,ItemInit.CHAINED_LEATHER_CHESTPLATE,ItemInit.CHAINED_LEATHER_HELMET);

    }

}
