package guardian.by_iron_or_fire.client.gui;

import guardian.by_iron_or_fire.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiArmorerSelect extends GuiButton {

    final ResourceLocation selectButton = new ResourceLocation(References.MODID,"textures/gui/container/anvil_advanced_gui.png");

    int u =0;
    int v =176;

    public GuiArmorerSelect(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y,108,19, "");
    }

    /**button used in the GuiArmorer functionally identical to the enchanting table buttons*/
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(selectButton);

        isMouseOver();

        if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height){
            hovered = true; }else{ hovered = false; }

        if(hovered && enabled){
            v = 204;
        }else if(!hovered && enabled){
            v = 167;
        }else{
            v = 185;
        }

        drawTexturedModalRect(x,y,u,v,width,height);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return super.mousePressed(mc, mouseX, mouseY);
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        super.playPressSound(soundHandlerIn);
    }
}
