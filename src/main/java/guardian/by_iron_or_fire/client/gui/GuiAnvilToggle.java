package guardian.by_iron_or_fire.client.gui;

import guardian.by_iron_or_fire.Main;
import guardian.by_iron_or_fire.util.References;
import guardian.by_iron_or_fire.util.handlers.GuiHandler;
import guardian.by_iron_or_fire.util.handlers.SideClientHandler;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiAnvilToggle extends GuiButton {

    final ResourceLocation guiAnvilAdvanced = new ResourceLocation(References.MODID,"textures/gui/container/anvil_advanced_gui.png");

    int u =176;
    int v =21;
    public World world = Minecraft.getMinecraft().world;
    public InventoryPlayer playerIn = Minecraft.getMinecraft().player.inventory;
    public EntityPlayer player = Minecraft.getMinecraft().player;
    public BlockPos posThis = SideClientHandler.posAnvil;

    public GuiAnvilToggle(int buttonId, int x, int y, String buttonTex) {
        super(buttonId, x, y, 32,32, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(visible){
            mc.renderEngine.bindTexture(guiAnvilAdvanced);

            isMouseOver();

            if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height){
                hovered = true; }else{ hovered = false; }

            if(hovered){
                v = 55;
            }else{
                v = 21;
            }

            drawTexturedModalRect(x,y,u,v,width,height);
        }
    }





    @Override
    public boolean isMouseOver() {
        return super.isMouseOver();
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {

        isMouseOver();

        if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height){
            hovered = true; }else{ hovered = false; }


        if(hovered){
            if(Minecraft.getMinecraft().currentScreen instanceof GuiRepair){

                /**Closes the Anvil_GUI and generates the Armorer_GUI*/

                Minecraft.getMinecraft().player.openGui(Main.instance, GuiHandler.GUI_ARMORER_ID, Minecraft.getMinecraft().world, posThis.getX(), posThis.getY() , posThis.getZ());


                /**partially funational, deprecated*/
                //Minecraft.getMinecraft().player.closeScreen();
                //Minecraft.getMinecraft().displayGuiScreen(new GuiArmorer(playerIn, Minecraft.getMinecraft().world));

            }else{

                /**Closes the Armorer_GUI and generates the Anvil_GUI*/
                Minecraft.getMinecraft().player.displayGui(new BlockAnvil.Anvil(world, posThis));


                /**partially funational, deprecated*/
                //Minecraft.getMinecraft().player.closeScreen();
                //Minecraft.getMinecraft().displayGuiScreen(new GuiRepair(playerIn, Minecraft.getMinecraft().world));

            }
        }

        return super.mousePressed(mc, mouseX, mouseY);

    }
}
