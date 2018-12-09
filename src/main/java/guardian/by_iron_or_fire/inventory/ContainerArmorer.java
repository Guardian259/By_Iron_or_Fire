package guardian.by_iron_or_fire.inventory;

import guardian.by_iron_or_fire.item.ArmorBase;
import net.minecraft.enchantment.EnchantmentVanishingCurse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import java.util.HashMap;

import static guardian.by_iron_or_fire.init.ItemInit.CHAINED_LEATHER;
import static net.minecraft.item.ItemArmor.ArmorMaterial.*;

public class ContainerArmorer extends Container {


    public final IInventory armorerSlots;
    public final IInventory secondarySlot;

    public final EntityPlayer player;
    public final BlockPos selfPosition;
    public final World world;
    public static boolean primaryActive;
    public static boolean secondaryActive;
    public boolean bonusActive;
    public static ItemStack itemStackPrimary;
    public ItemStack itemStackSecondary;

    public static Item primaryItem;

    public static final HashMap<String, String> chainedItemMap = new HashMap<>();
    public static final HashMap<String, String[]> armorTypes = new HashMap<>();


    public int maximumCost;

    @SideOnly(Side.CLIENT)
    public ContainerArmorer(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {

        this(playerInventory, worldIn, BlockPos.ORIGIN, player);
        chainedItemMap.put("LEATHER","CHAINED_LEATHER");
        chainedItemMap.put("GOLD","CHAINED_GOLD");
        chainedItemMap.put("IRON","CHAINED_IRON");
        chainedItemMap.put("DIAMOND","CHAINED_DIAMOND");
        chainedItemMap.put("EMERALD","CHAINED_EMERALD");
        armorTypes.put("TYPES",new String[]{"_BOOTS","_LEGGINGS","_CHESTPLATE","_HELMET"});

    }

    public ContainerArmorer(InventoryPlayer inventoryPlayer, World worldIn, BlockPos blockPosIn, EntityPlayer player) {

        this.world = worldIn;
        this.player = player;
        this.selfPosition = blockPosIn;

        this.armorerSlots = new InventoryBasic("",false,2){

            /**
             * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't
             * think it hasn't changed and skip it.
             */
            public void markDirty()
            {
                super.markDirty();
                ContainerArmorer.this.onCraftMatrixChanged(this);
            }
        };

        this.secondarySlot = new InventoryBasic("",false,1){

            /**
             * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't
             * think it hasn't changed and skip it.
             */
            public void markDirty()
            {
                super.markDirty();
                ContainerArmorer.this.onCraftMatrixChanged(this);
            }
        };


        /**Container Inventory*/
        this.addSlotToContainer(new ArmorerSlots(armorerSlots, 0, 26, 47));
        this.addSlotToContainer(new ArmorerSlots(armorerSlots, 1, 49, 59));
        this.addSlotToContainer(new SecondaryArmorerSlot(secondarySlot, 0, 72, 59));

        /**Player Inventory*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {

                this.addSlotToContainer(new Slot(inventoryPlayer, j + (i * 9) + 9, 8 + j * 18, 84 + i * 18));

            }
        }

        /**Hotbar Inventory*/
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (i * 18), 142));
        }

    }

    /**
     * Callback for when the recipes matrix is changed.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {

        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn == this.armorerSlots || inventoryIn == this.secondarySlot)
        {
            this.updateArmorerOptions();
        }

    }

    public static void chainedVariants(ItemArmor.ArmorMaterial primaryMaterial, int armorType){
        String[] types = armorTypes.get("TYPES");
        /**this generates the needed item from its name*/
        String chainedArmorName = ("bif:" + (chainedItemMap.get(primaryMaterial.toString()) + types[armorType]));
        primaryItem = Item.getByNameOrId(chainedArmorName.toLowerCase());
        primaryActive = true;
        if(primaryMaterial == LEATHER){
            secondaryActive = true;
        }
    }

    /**
     * called when the Armorer Input Slot changes, calculates the new result and displays the options on the buttons
     *
     * call this to integrate into the container
     */
    public void updateArmorerOptions() {

        /**grabs what is in the armorer's slots*/
        ItemStack itemStackMain = this.armorerSlots.getStackInSlot(0);
        ItemStack itemStackUnder = this.armorerSlots.getStackInSlot(1);
        ItemStack itemStackBonus = this.secondarySlot.getStackInSlot(0);

        /**Checks if both item are Armor AND makes sure that it is not a chained variant*/
        if ((itemStackMain.getItem() instanceof ItemArmor && itemStackUnder.getItem() instanceof ItemArmor) && !(itemStackMain.getItem() instanceof ArmorBase)) {
                /**Checks if both armors are of the same type*/
            if (((ItemArmor) itemStackMain.getItem()).armorType == ((ItemArmor) itemStackUnder.getItem()).armorType) {
                if (((ItemArmor) itemStackUnder.getItem()).getArmorMaterial() == CHAIN) {
                        /**determines the actual output and sends it to the GUI for display*/
                        chainedVariants(((ItemArmor) itemStackMain.getItem()).getArmorMaterial(), ((ItemArmor) itemStackMain.getItem()).armorType.getIndex());
                        itemStackPrimary = primaryItem.getDefaultInstance();
                        if(((ItemArmor) itemStackMain.getItem()).getArmorMaterial() == LEATHER){
                             itemStackSecondary = primaryItem.getDefaultInstance();
                        }
                }
            }
        }

        /**this checks for elytra's and attaches the 'attached elytra' NBT to the item*/
        else if((((ItemArmor) itemStackMain.getItem()).armorType == EntityEquipmentSlot.CHEST) && itemStackUnder.getItem() instanceof ItemElytra){
            if((((ItemArmor) itemStackMain.getItem()).getArmorMaterial() == LEATHER) || (((ItemArmor) itemStackMain.getItem()).getArmorMaterial() == CHAINED_LEATHER)){
                ItemStack itemStackNew = itemStackMain.copy();
                /**NBT set up for attaching elytra to leather chestplates*/
                NBTTagCompound attachedElytra = itemStackNew.getTagCompound();
                if (attachedElytra == null)
                    attachedElytra = new NBTTagCompound();
                attachedElytra.setInteger("attached_elytra", 0);
                itemStackNew.setTagCompound(attachedElytra);

                itemStackPrimary = itemStackNew;
                primaryActive = true;
                secondaryActive = true;
            }                primaryActive = false;
            secondaryActive = false;
            bonusActive = false;
            itemStackPrimary = ItemStack.EMPTY;
            itemStackSecondary = ItemStack.EMPTY;

        }

        /**this checks for totems and attaches the 'totemed' NBT to the item*/
        else if (itemStackBonus.getItem() == Items.TOTEM_OF_UNDYING && itemStackMain.getItem() instanceof ItemArmor) {
            if (!itemStackMain.getEnchantmentTagList().equals(EnchantmentVanishingCurse.REGISTRY)) {

                ItemStack itemStackNew = itemStackMain.copy();
                /**NBT set up for totemed equipment*/
                NBTTagCompound totemedEquipment = itemStackNew.getTagCompound();
                if (totemedEquipment == null)
                    totemedEquipment = new NBTTagCompound();
                totemedEquipment.setInteger("totemed", 0);
                itemStackNew.setTagCompound(totemedEquipment);

                itemStackPrimary = itemStackNew;
                primaryActive = true;
                bonusActive = true;
            } else {
                primaryActive = false;
                secondaryActive = false;
                bonusActive = false;
                itemStackPrimary = ItemStack.EMPTY;
                itemStackSecondary = ItemStack.EMPTY;
            }
            /**redundancy for the sake of redundancies sake*/
        } else {
            primaryActive = false;
            secondaryActive = false;
            bonusActive = false;
            itemStackPrimary = ItemStack.EMPTY;
            itemStackSecondary = ItemStack.EMPTY;
        }
    }



    /**
     * Handles the given Button-click on the server, currently only used by enchanting and this Container. Name is for legacy.
     */
    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id) {
        /**performs the combination and creation of the generated Item*/
        if(((primaryActive || secondaryActive)&& !bonusActive) /**&& (itemStackPrimary != null || itemStackSecondary != null)*/) {
            /**when button one is pressed*/
            if (id == 0) {
                this.armorerSlots.setInventorySlotContents(0, itemStackPrimary);
                this.armorerSlots.setInventorySlotContents(1, ItemStack.EMPTY);
                itemStackPrimary = null;
                primaryActive = false;
                secondaryActive = false;
            /**when button two is pressed*/
            } else if (id == 1) {
                this.armorerSlots.setInventorySlotContents(0, itemStackSecondary);
                this.armorerSlots.setInventorySlotContents(1, ItemStack.EMPTY);
                itemStackSecondary = null;
                primaryActive = false;
                secondaryActive = false;
            }
        /**when button one is pressed and the bonus item is a totem*/
        }else if((primaryActive && bonusActive) /**&& (itemStackPrimary != null)*/){
            if(id == 0){
                this.armorerSlots.setInventorySlotContents(0, itemStackPrimary);
                this.secondarySlot.setInventorySlotContents(0, ItemStack.EMPTY);
                itemStackPrimary = null;
                primaryActive = false;
                bonusActive = false;
            }
        }

        return super.enchantItem(playerIn, id);
    }

    /**
     * most of the remained is simply core setup for the container
     */
    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            int containerSlots = inventorySlots.size() - playerIn.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(stackInSlot, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stackInSlot, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            slot.onTake(playerIn, stackInSlot);

        }
        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        if (id == 0)
        {
            this.maximumCost = data;
        }
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.armorerSlots);
            this.armorerSlots.closeInventory(player);
            this.clearContainer(playerIn, this.world, this.secondarySlot);
            this.secondarySlot.closeInventory(player);
            this.clearContainer(playerIn, this.world, this.player.inventory);
            this.player.inventory.closeInventory(player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
