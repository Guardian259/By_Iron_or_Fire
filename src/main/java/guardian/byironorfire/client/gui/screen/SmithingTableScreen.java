package guardian.byironorfire.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import guardian.byironorfire.inventory.container.SmithingTableContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SmithingTableScreen extends ContainerScreen<SmithingTableContainer> {

    private static final ResourceLocation SMITHY_GUI_TEXTURE = new ResourceLocation("textures/gui/smithy_table_screen.png");

    public SmithingTableScreen(SmithingTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(SMITHY_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

    }
}
