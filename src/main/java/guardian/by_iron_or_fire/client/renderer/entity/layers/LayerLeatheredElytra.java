package guardian.by_iron_or_fire.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerLeatheredElytra implements LayerRenderer<EntityLivingBase> {

    // The basic Elytra texture.
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    // Instance of the player renderer.
    protected final RenderLivingBase<?> renderPlayer;
    // The model used by the Elytra.
    private final ModelElytra modelElytra = new ModelElytra();

    public LayerLeatheredElytra(RenderLivingBase<?> p_i47185_1_)
    {
        this.renderPlayer = p_i47185_1_;
    }


    /*
     * Framework is from LayerRenderer
     * Preforms the attachment of the Eyltra model to the Player model.
     * This is largely the vanilla code for Eyltra rendering, the catch is the initial if
     * statement has been replace with a check for the NBTTagCompound attached_elytra
     */
    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("attached_elytra")) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            if (entitylivingbaseIn instanceof AbstractClientPlayer) {
                AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer) entitylivingbaseIn;

                if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null) {
                    this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());
                } else if (abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE)) {
                    this.renderPlayer.bindTexture(abstractclientplayer.getLocationCape());
                } else {
                    this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
                }
            } else {
                this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.modelElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            if (itemstack.isItemEnchanted()) {
                LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }


    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

}
