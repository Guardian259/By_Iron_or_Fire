package guardian.by_iron_or_fire.util.handlers;

import guardian.by_iron_or_fire.client.renderer.entity.layers.LayerDynamicName;
import guardian.by_iron_or_fire.util.References;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
    public static LayerDynamicName dynamicName;

    public static final HashMap<String, int[]> idMap = new HashMap<>();
    public static final HashMap<String, int[]> invMap = new HashMap<>();

    PlayerLabelHandler(){
        /*
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


    /**
     * updates every tick to determine whether or not the players nameplate render range has changed
     */
    @SubscribeEvent
    public static void onPlayerLivingLabel(RenderPlayerEvent event) {

        //armor inventory (List) of the current player
        playerArmorInv = event.getEntityPlayer().inventory.armorInventory;

        int[] maxViewRange = invMap.get("NAME_RANGE");
        int[] defaultRange = idMap.get("IRON");

        //is checking to see if the player is even wearing anything
        //if (playerArmorInv != null) {
            //loops through all equipped items
            for (int i = 0; i < playerArmorInv.size(); i++) {
                //the current ItemStack in question
                ItemStack itemStack = event.getEntityPlayer().inventory.armorInventory.get(i);

                //double checks to ensure the item in the slot is an actual armor piece; for future mod compatibility
                if (itemStack != ItemStack.EMPTY && itemStack.getItem() instanceof ItemArmor) {
                    //gets the Armor Material, plus type, and returns the render range from that item
                    int armorType = ((ItemArmor) itemStack.getItem()).armorType.getSlotIndex();
                    ItemArmor.ArmorMaterial armorMaterial = ((ItemArmor) itemStack.getItem()).getArmorMaterial();
                    wornArmorMaterial(armorMaterial, armorType);

                    //simple check for if an eyltra is attached; variants of leather chestplates only
                    hasAttachedEyltra(itemStack);
                    //setting of the this particular armor piece's render range into the array
                    maxViewRange[armorType] = renderRange;
                }else {
                //safety net for if the item was either a modded item or the slot was actually empty
                maxViewRange[i] = defaultRange[i];
                }
                //once all item ranges have been compiled they are summed as the viewDistance
                maxViewRange[4] = maxViewRange[0] + maxViewRange[1] + maxViewRange[2] + maxViewRange[3];
                invMap.replace("NAME_RANGE", maxViewRange);
            }
        //}

        //this is actually wrong; redefine this; and the dynamic name is never actually set properly as well...
        //maybe...
        if (dynamicName == null) {
            //actually sets the nameplate render ranges for both the normal and slim player models
            final String s = ((AbstractClientPlayer) event.getEntityPlayer()).getSkinType();
            if (s.contains("slim")) {
                setRenderRange( maxViewRange[4], (maxViewRange[4] / 2));
            }else {
                setRenderRange(maxViewRange[4], (maxViewRange[4] / 2));
            }
        //if player is wearing nothing the vanilla range is used
        }else {
            setRenderRange(maxViewRange[4], (maxViewRange[4] / 2));
        }
    }

    /**
     * Runs a check for the NBT of an attached Elytra and doubles the render range if true
     * Is currently unused and may change in the future.
     * As of now it only takes the <code>ItemStack</code> in question
     *
     * @param stack the ItemStack in question; is used to check for NBT tags
     */
    public static void hasAttachedEyltra(ItemStack stack){
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("attached_elytra")){
            renderRange = renderRange * 2;
        }
    }

    /**
     * sets the DynamicPlayerRender ranges for the normal and sneak parameters
     */
    public static void setRenderRange(int range, int rangeSneak){
            dynamicName.DYNAMIC_NAME_TAG_RANGE = range;
            dynamicName.DYNAMIC_NAME_TAG_RANGE_SNEAK = rangeSneak;
    }

    /**
     * This will return the worn items nameplate render values when this is given the current armor material,
     * and armorType integer. These values are obtained from the HashMap <code>idMap</code>
     *
     * @param material the current ItemStack's ArmorMaterial; is converted to a string to be used as the HashMap Key
     * @param armorType the current ItemStack's armorType; this is the int value which determines what armor slot
     *                  said item is valid for and is used to determine which value from the HashMap is to be used
     */
    public static void wornArmorMaterial(ItemArmor.ArmorMaterial material, int armorType){
        int[] armorRanges = idMap.get(material.toString());
        renderRange = armorRanges[armorType];
    }
}
