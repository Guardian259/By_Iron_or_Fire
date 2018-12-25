package guardian.by_iron_or_fire.client.gui;

import guardian.by_iron_or_fire.inventory.ContainerArmorer;
import guardian.by_iron_or_fire.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class GuiArmorer extends GuiContainer implements IContainerListener {

    /**Extended container within the vanilla anvil, is toggled by the GuiAnvilToggle button*/

    final ResourceLocation guiAnvilArmorer = new ResourceLocation(References.MODID,"textures/gui/container/anvil_advanced_gui.png");

    public static int guiWidth;
    public static int guiHeight;

    public Random rand = new Random();
    public String primaryString = "";
    public int primaryStringWidth;
    public String secondaryString = "";
    public int secondaryStringWidth;

    int centerX;
    int centerY;

    public final ContainerArmorer anvil;
    public final InventoryPlayer playerInventory;
    public final static int ANVIL_TOGGLE = 0;
    public final static int SELECT_PRIMARY = 1;
    public final static int SELECT_SECONDARY = 2;

    public static GuiButton selectPrimary;
    public static GuiButton selectSecondary;
    public ItemStack primaryOutItemStack;
    public ItemStack secondaryOutItemStack;



    public GuiArmorer(InventoryPlayer inventoryPlayer, World worldIn) {
        super(new ContainerArmorer(inventoryPlayer, worldIn, Minecraft.getMinecraft().player));
        this.playerInventory = inventoryPlayer;
        this.anvil = (ContainerArmorer)this.inventorySlots;

        guiWidth = 176;
        guiHeight = 166;

    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        this.inventorySlots.removeListener(this);
        this.inventorySlots.addListener(this);
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeListener(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    /**
     * will eventually add in experience cost and ToolTips
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        fontRenderer.drawString(new TextComponentTranslation("Armourer").getFormattedText(), 90, 4 , Color.darkGray.getRGB());
        centerX = (width / 2)- guiWidth / 2;
        centerY = (height / 2)- guiHeight / 2;
        buttonList.clear();
        buttonList.add(new GuiAnvilToggle(ANVIL_TOGGLE,(centerX + 15),(centerY + 5),""));
        updateButtons();

        GlStateManager.pushMatrix();
        {

            //Displays the item to be made for button 1
            if (selectPrimary.enabled) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(primaryOutItemStack.getItem()), 61, 14);
            }
            GlStateManager.popMatrix();
            {
                if(selectPrimary.enabled && primaryString.isEmpty()) {
                    String s = "" + (rand.nextInt(8) / 3);
                    primaryStringWidth = 86 - this.fontRenderer.getStringWidth(s);
                    primaryString = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, primaryStringWidth);
                }else{
                    if (!selectPrimary.enabled) {
                        primaryString = "";
                    }
                }
                FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
                fontrenderer.drawSplitString(primaryString, centerX - 42, guiHeight - 151 /*centerY - 25*/, primaryStringWidth, (6839882 & 16711422) >> 1);
            }
            GlStateManager.pushMatrix();
            //Displays the item to be made for button 2
            if (selectSecondary.enabled) {
                mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(secondaryOutItemStack.getItem()), 61, 33);
            }
            GlStateManager.popMatrix();
            {
                if(selectSecondary.enabled && secondaryString.isEmpty()) {
                    String s = "" + (rand.nextInt(8) / 3);
                    secondaryStringWidth = 86 - this.fontRenderer.getStringWidth(s);
                    secondaryString = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, secondaryStringWidth);
                }else{
                    if (!selectSecondary.enabled) {
                        secondaryString = "";
                    }
                }
                FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
                fontrenderer.drawSplitString(secondaryString, centerX - 42, guiHeight - 132 /*centerY - 6*/, secondaryStringWidth, (6839882 & 16711422) >> 1);

            }
            GlStateManager.pushMatrix();
        }
        GlStateManager.popMatrix();

        //this generates the tooltips for the button items. currently they seem to require an offset to appear correct. may need changed
        if(selectPrimary.enabled && selectPrimary.isMouseOver()){
            this.renderToolTip(primaryOutItemStack,(mouseX - 124), (mouseY - 30));
        }else {
            //this is using the primary item due to the secondary item not yet being rendered. will need to be changed
            if (selectSecondary.enabled && selectSecondary.isMouseOver()) {
                this.renderToolTip(primaryOutItemStack, (mouseX - 124), (mouseY - 30));
            }
        }
    }

    /**
     * server container to client gui information transfer.
     * this tells the gui what buttons to activate and what output item to show
     */
    public void updateButtons(){
        selectPrimary.enabled = this.anvil.primaryActive;
        selectSecondary.enabled = this.anvil.secondaryActive;

        primaryOutItemStack = this.anvil.itemStackPrimary;
        secondaryOutItemStack = this.anvil.itemStackSecondary;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().renderEngine.bindTexture(guiAnvilArmorer);
        buttonList.add(selectPrimary = new GuiArmorerSelect(SELECT_PRIMARY,(centerX + 60),(centerY + 14),""));
        buttonList.add(selectSecondary = new GuiArmorerSelect(SELECT_SECONDARY,(centerX + 60),(centerY + 33),""));
        updateButtons();
        drawTexturedModalRect(guiLeft, guiTop,0,0, guiWidth, guiHeight);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        //checks what button was moused over during the click and tells the container which button it was
        if(selectPrimary.isMouseOver() && this.anvil.enchantItem(this.mc.player,0)){
            this.mc.playerController.sendEnchantPacket(this.anvil.windowId,0);
        }else if(selectSecondary.isMouseOver() &&  this.anvil.enchantItem(this.mc.player,0)){
            this.mc.playerController.sendEnchantPacket(this.anvil.windowId,1);
        }
    }

    @Override
    public void sendAllContents(net.minecraft.inventory.Container containerToSend, NonNullList<ItemStack> itemsList) {
        this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
    }

    @Override
    public void sendSlotContents(net.minecraft.inventory.Container containerToSend, int slotInd, ItemStack stack) {

    }

    @Override
    public void sendWindowProperty(net.minecraft.inventory.Container containerIn, int varToUpdate, int newValue) {

    }

    @Override
    public void sendAllWindowProperties(Container containerIn, IInventory inventory) {

    }

}
