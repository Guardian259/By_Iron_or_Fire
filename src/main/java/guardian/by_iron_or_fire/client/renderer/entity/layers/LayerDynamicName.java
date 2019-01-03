package guardian.by_iron_or_fire.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.NonNullList;

import java.util.HashMap;
import java.util.List;

public class LayerDynamicName implements LayerRenderer<AbstractClientPlayer> {

    // Instance of the player renderer.
    protected final RenderLivingBase<?> renderPlayer;

    public static int viewDistance;
    public static int feet;
    public static int legs;
    public static int chest;
    public static int head;

    public static final HashMap<String, int[]> idMap = new HashMap<>();
    public static final HashMap<String, int[]> invMap = new HashMap<>();


    public LayerDynamicName (RenderLivingBase<?> p_i47185_1_) {
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

        this.renderPlayer = p_i47185_1_;
    }


    //reminder, when the render range changes it does not update the check for is the player is outside the range; this needs fixed
    @Override
    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        double viewingEntityDist = renderPlayer.getRenderManager().renderViewEntity.getDistance(entitylivingbaseIn.posX,entitylivingbaseIn.posY,entitylivingbaseIn.posZ);

        int[] maxViewRange = invMap.get("NAME_RANGE");
        int[] defaultRanges = idMap.get("IRON");
        int[] leatherRanges = idMap.get("LEATHER");

        NonNullList armorInv = entitylivingbaseIn.inventory.armorInventory;
        for (int i = 0; i < armorInv.size(); i++) {
            ItemStack stack = (ItemStack) armorInv.get(i);
            //initial check for base armor types
            if(stack.getItem() != Items.AIR || stack != ItemStack.EMPTY) {
                int slotIn = ((ItemArmor) stack.getItem()).armorType.getIndex();
                int[] armorRanges = idMap.get(((ItemArmor) stack.getItem()).getArmorMaterial().toString().toUpperCase());

                //check used only for leather variants which doubles the chestplates value if an elytra is attached
                if(stack.hasTagCompound() && stack.getTagCompound().hasKey("attached_elytra")){
                    maxViewRange[slotIn] = (armorRanges[slotIn] * 2);
                }
                //default result for most cases
                else {
                    maxViewRange[slotIn] = armorRanges[slotIn];
                }
            }
            //safety check for if the chestplate is an elytra
            else if(stack.getItem() instanceof ItemElytra){
                maxViewRange[i] = leatherRanges[1];
            }
            //final check for if the player has no armor equipped
            else{
                maxViewRange[i] = defaultRanges[i];
            }
            maxViewRange[4] = (maxViewRange[0] + maxViewRange[1] + maxViewRange[2] + maxViewRange[3]);
        }

        //sets the rendering range of the vanilla nameplate render range
        if((maxViewRange[4] <= 64) && (viewingEntityDist <= maxViewRange[4])){
            RenderLivingBase.NAME_TAG_RANGE = (float) maxViewRange[4];
            RenderLivingBase.NAME_TAG_RANGE_SNEAK = (float) (maxViewRange[4]/2);
        }
        //sets the rendering range when outside of the vanilla ranges
        else if((maxViewRange[4] > 64) && (viewingEntityDist <= maxViewRange[4])){
            //copied and pasted from dark's tutorial to see if this system works
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.scale(0.6, 0.6, 0.6);
            GlStateManager.rotate((ageInTicks) / 20.0F * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0, entitylivingbaseIn.height - 0.3, 0);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.APPLE), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            /* temporally deactivated; doesn't function
            // taken from Class Render; modified to fit here, though it does not yet function
            boolean flag = entitylivingbaseIn.isSneaking();
            float f = renderPlayer.getRenderManager().playerViewY;
            float f1 = renderPlayer.getRenderManager().playerViewX;
            boolean flag1 = renderPlayer.getRenderManager().options.thirdPersonView == 2;
            float f2 = entitylivingbaseIn.height + 0.5F - (flag ? 0.25F : 0.0F);
            String str = entitylivingbaseIn.getDisplayNameString();
            int i = "deadmau5".equals(str) ? -10 : 0;
            EntityRenderer.drawNameplate(renderPlayer.getRenderManager().getFontRenderer(), str, (float)entitylivingbaseIn.posX, (float)entitylivingbaseIn.posY + f2, (float)entitylivingbaseIn.posZ, i, f, f1, flag1, flag);

             */

        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
