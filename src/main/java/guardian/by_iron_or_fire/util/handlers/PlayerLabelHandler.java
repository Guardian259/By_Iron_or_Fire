package guardian.by_iron_or_fire.util.handlers;

import guardian.by_iron_or_fire.client.renderer.entity.DynamicRenderPlayer;
import guardian.by_iron_or_fire.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;


@Mod.EventBusSubscriber(modid = References.MODID, value= net.minecraftforge.fml.relauncher.Side.CLIENT)
public class PlayerLabelHandler {

    public static List playerArmorInv;

    public static int renderRange;

    public static int viewDistance;
    public static int feet;
    public static int legs;
    public static int chest;
    public static int head;
    private static DynamicRenderPlayer dynamicName;

    public static final HashMap<String, int[]> idMap = new HashMap<>();
    public static final HashMap<String, int[]> invMap = new HashMap<>();

    PlayerLabelHandler(){
        /**
         * each # corresponds to the individual render range of each
         * armor piece, going from: head, chest, legs, feet, and the total range (which is not used)
         */
        idMap.put("CHAINED_DIAMOND", new int[] {16,48,40,24,128});
        idMap.put("DIAMOND", new int[] {14,42,36,20,112});
        idMap.put("CHAINED_IRON", new int[] {10,30,24,16,80});
        idMap.put("IRON", new int[] {8,24,20,12,64});
        idMap.put("CHAINMAIL", new int[] {8,24,20,12,64});
        idMap.put("CHAINED_GOLD", new int[] {6,17,15,10,48});
        idMap.put("GOLD", new int[] {4,12,10,6,32});
        idMap.put("CHAINED_LEATHER", new int[] {2,6,5,3,16});
        idMap.put("LEATHER", new int[] {1,4,2,1,8});
        invMap.put("NAME_RANGE", new int[]{head, chest, legs, feet, viewDistance});
    }

    @SubscribeEvent
    public void renderPlayerPre(RenderPlayerEvent.Pre event){
        GL11.glPushMatrix();
    }

    @SubscribeEvent
    public void renderPlayerPost(){
        GL11.glPopMatrix();
    }


    @SubscribeEvent
    public static void onPlayerLivingLabel(RenderPlayerEvent event) {

            playerArmorInv = event.getEntityPlayer().inventory.armorInventory;

            int[] maxViewRange = invMap.get("NAME_RANGE");
            int[] defaultRange = idMap.get("IRON");

            /**is checking to see if the player is even wearing anything*/
            if (playerArmorInv != null) {
                for (int i = 0; i < playerArmorInv.size(); i++) {
                    /**double checks to ensure the item in the slot is an Armor piece*/
                    if (playerArmorInv.get(i) != ItemStack.EMPTY && playerArmorInv.get(i) instanceof ItemArmor) {
                        int armorType = ((ItemArmor) event.getEntityPlayer().inventory.armorInventory.get(i).getItem()).armorType.getSlotIndex();
                        /**gets the Armor Material and returns the render range from that item*/
                        ItemArmor.ArmorMaterial armorMaterial = ((ItemArmor) event.getEntityPlayer().inventory.armorInventory.get(i).getItem()).getArmorMaterial();
                        wornArmorMaterial(armorMaterial, armorType);
                        maxViewRange[armorType] = renderRange;
                    } else {
                        maxViewRange[i] = defaultRange[i];
                    }
                    /**once all item ranges have been compiled they are summed as the viewDistance*/
                    maxViewRange[4] = maxViewRange[0] + maxViewRange[1] + maxViewRange[2] + maxViewRange[3];
                    invMap.replace("NAME_RANGE", maxViewRange);
                }
            }
            if (dynamicName == null) {
                /**actually sets the nameplate render ranges for both the normal and slim player models*/
                final String s = ((AbstractClientPlayer) event.getEntityPlayer()).getSkinType();
                if (s.contains("slim")) {
                    setRenderRange(true, maxViewRange[4], (maxViewRange[4] / 2));
                } else {
                    setRenderRange(false, maxViewRange[4], (maxViewRange[4] / 2));
                }
            } else {
                dynamicName.DYNAMIC_NAME_TAG_RANGE = maxViewRange[4];
                dynamicName.DYNAMIC_NAME_TAG_RANGE_SNEAK = (maxViewRange[4] / 2);
            }
    }

    /**
     * sets the DynamicPlayerRender ranges for the normal and sneak parameters
     */
    public static void setRenderRange(boolean smallUse,int range,int rangeSneak){
        if (smallUse) {
            dynamicName = new DynamicRenderPlayer(Minecraft.getMinecraft().getRenderManager(), smallUse);
            dynamicName.DYNAMIC_NAME_TAG_RANGE = range;
            dynamicName.DYNAMIC_NAME_TAG_RANGE_SNEAK = (rangeSneak / 2);
        }else {
            dynamicName = new DynamicRenderPlayer(Minecraft.getMinecraft().getRenderManager());
            dynamicName.DYNAMIC_NAME_TAG_RANGE = range;
            dynamicName.DYNAMIC_NAME_TAG_RANGE_SNEAK = (rangeSneak / 2);
        }
    }

    /**
     * when given the current armor material and type will return said items nameplate render value
     */
    public static void wornArmorMaterial(ItemArmor.ArmorMaterial material, int armorType){
        int[] armorRanges = idMap.get(material.toString());
        renderRange = armorRanges[armorType];
    }
}
