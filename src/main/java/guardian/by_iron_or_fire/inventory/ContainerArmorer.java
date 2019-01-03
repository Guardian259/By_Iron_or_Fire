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
    public boolean primaryActive;
    public boolean secondaryActive;
    public boolean bonusActive;
    public ItemStack itemStackPrimary;
    public ItemStack itemStackSecondary;

    public Item primaryItem;
    public int chainedDamage;

    public static final HashMap<String, String> chainedItemMap = new HashMap<>();
    public static final HashMap<String, String[]> armorTypes = new HashMap<>();


    public int forgingCost;

    @SideOnly(Side.CLIENT)
    public ContainerArmorer(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {

        this(playerInventory, worldIn, BlockPos.ORIGIN, player);
        chainedItemMap.put("LEATHER","bif:CHAINED_LEATHER");
        chainedItemMap.put("GOLD","bif:CHAINED_GOLD");
        chainedItemMap.put("IRON","bif:CHAINED_IRON");
        chainedItemMap.put("DIAMOND","bif:CHAINED_DIAMOND");
        chainedItemMap.put("EMERALD","bif:CHAINED_EMERALD");
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

        //Container Inventory
        this.addSlotToContainer(new ArmorerSlots(armorerSlots, 0, 26, 47));
        this.addSlotToContainer(new ArmorerSlots(armorerSlots, 1, 49, 59));
        this.addSlotToContainer(new SecondaryArmorerSlot(secondarySlot, 0, 72, 59));

        //Player Inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {

                this.addSlotToContainer(new Slot(inventoryPlayer, j + (i * 9) + 9, 8 + j * 18, 84 + i * 18));

            }
        }

        //Hotbar Inventory
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

    /**
     * Takes the <code>ArmorMaterial</code> and the <code>armorType</code> from the primary item
     * and uses them to generate the name of the resulting  armor.
     * From this string the <code>primaryItem</code> is able to be set,
     * giving subsequent code the ability to generate the required ItemStack.
     *
     * All modded and vanilla armorMaterials are valid so long as they conform to the {@link ItemArmor.ArmorMaterial} enum
     * All armors, including modded ones, must contain an <code>armorType</code>, in order to be valid wearable equipment.
     *
     * There are two HashMaps which contain the resulting String parts necessary to generate the name,
     * one of which can be used to integrate modded Materials into this system.
     * One stores pairs of stings, and the other the list of Armor Types.
     * To integrate any further Armor Materials simply create a new map for the <code>chainedItemMap</code>.
     * I.E.: <code>chainedItemMap.put(SOME_NAME,YOUR_MOD_ID:CHAINED_SOME_NAME);</code>
     *
     * @param primaryMaterial this is the actual material of the primary armor; used to determine the first half of the new name.
     * @param armorType this is an int which identifies what type of armor the primary piece is; used to determine the second half of the new name
     *
     * TODO: Possibly implement a system which will allow for a chained render Layer to be added to an items image and its 3D model
     */
    //this name might be better if the name is changed to armorVariants, as this can be made more generic
    public void chainedVariants(ItemArmor.ArmorMaterial primaryMaterial, int armorType){
        String[] types = armorTypes.get("TYPES");

        //this generates the needed item from its name
        String chainedArmorName = ((chainedItemMap.get(primaryMaterial.toString()) + types[armorType]));
        primaryItem = Item.getByNameOrId(chainedArmorName.toLowerCase());
        primaryActive = true;

        //placeholder; may be deprecated upon GUI rework
        if(primaryMaterial == LEATHER){
            secondaryActive = true;
        }
    }

    /**
     * Takes a given <code>ItemStack</code>, a NBT String name, and the corresponding integer and adds it to the <code>ItemStack</code> for later use.
     * This particular method specifically adds an integer NBT Tag to the <code>ItemStack</code>.
     * For more information on this particular NBT set up see:
     * @see NBTTagCompound#setInteger(String, int)
     *
     * @param stack The Stack in question
     * @param nbtName The particular sting name of the NBT Tag; used to identify the Tag on the ItemStack
     * @param val The integer value of the NBT tag; Is set to zero for my tags but can be whatever is needed
     */
    public void nbtIntGen(ItemStack stack, String nbtName, int val){

        ItemStack itemStackNew = stack.copy();
        //NBT set up for totemed equipment
        NBTTagCompound nbtTag = itemStackNew.getTagCompound();
        if (nbtTag == null)
            nbtTag = new NBTTagCompound();
        nbtTag.setInteger(nbtName, val);
        itemStackNew.setTagCompound(nbtTag);

        itemStackPrimary = itemStackNew;
        itemStackSecondary = itemStackNew;

        primaryActive = true;
        if(nbtName.equals( "attached_elytra")){
            secondaryActive = true;
        }
        if(nbtName.equals("totemed")){
            bonusActive = true;
        }
    }
    /**
     * Takes two given ItemStacks, determines the current remaining health of each healthpool,
     * and uses said values to determine the resulting health of a new Items healthpool.
     * Assumes the integer value <code>chainedDamage</code> is to be used as the damage value of a resulting ItemStack.
     *
     * @param mainStack The first <code>ItemStack</code> in question; is used to obtain its Damage and MaxDamage
     * @param underStack The second <code>ItemStack</code> in question; is used to obtain its Damage and MaxDamage
     * @param resultingItem The new <code>ItemStack</code> to be created; must have been generated prior to this methods runtime
     */
    public void damageContribution(ItemStack mainStack, ItemStack underStack, ItemStack resultingItem){
        //Grabs the decimal version of the remaining health in the healthpool of each item
        float itemMainContribution = (((float) mainStack.getItemDamage()) / ((float) mainStack.getMaxDamage()));
        float itemSecondContribution = (((float) underStack.getItemDamage()) / ((float) underStack.getMaxDamage()));

        //grabs the new items healthpool and combines the above into the new items health is
        chainedDamage = (int) (((((float) resultingItem.getMaxDamage()) / 2) * itemMainContribution) + ((((float) resultingItem.getMaxDamage()) / 2) * itemSecondContribution));
    }

    /**
     * Called when the Armorer Input Slot changes, calculates the new result, and displays the options on the buttons.
     * This is the same framework from all vanilla containers and thus acts accordingly.
     * There are three primary checks within this method: one for the creating ChainedArmors,
     * one for attaching a totemed NBT, and one for attaching an attached_elytra NBT, all of whom have there own methods they call.
     * For these method's see:
     * @see #chainedVariants(ItemArmor.ArmorMaterial, int)
     * @see #nbtIntGen(ItemStack, String, int)
     * @see #damageContribution(ItemStack, ItemStack, ItemStack)
     *
     * TODO: make use of the forgingCost int in order to charge experience for combining; have been using the ContainerRepair as the example
     * TODO: develop a way to preserve the enchants of an armor piece and adding its costs to the forgingCost; ContainerRepair appears to contain examples of this
     * TODO: develop a way to add multiple NBT tags; both at once and consecutively. there seems to be an issue where once one of the custom NBT's is added the other cannot be.
     */
    public void updateArmorerOptions() {

        //grabs what is in the armorer's slots
        ItemStack itemStackMain = this.armorerSlots.getStackInSlot(0);
        ItemStack itemStackUnder = this.armorerSlots.getStackInSlot(1);
        ItemStack itemStackBonus = this.secondarySlot.getStackInSlot(0);

        //This checks if the first item is an instance of ItemArmor
        if(itemStackMain.getItem() instanceof ItemArmor) {
            //this checks ifthe second item is an instance of ItemArmor AND makes sure that it is not a chained variant
            if (itemStackUnder.getItem() instanceof ItemArmor && !(itemStackMain.getItem() instanceof ArmorBase)) {

                //Checks if both armors are of the same type
                if (((ItemArmor) itemStackMain.getItem()).armorType == ((ItemArmor) itemStackUnder.getItem()).armorType && (((ItemArmor) itemStackUnder.getItem()).getArmorMaterial() == CHAIN)) {

                    //Determines the actual output item, generates said item, and then sets the new items damage
                    chainedVariants(((ItemArmor) itemStackMain.getItem()).getArmorMaterial(), ((ItemArmor) itemStackMain.getItem()).armorType.getIndex());
                    itemStackPrimary = new ItemStack(primaryItem);
                    damageContribution(itemStackMain, itemStackUnder, itemStackPrimary);
                    itemStackPrimary.setItemDamage(chainedDamage);


                    //Placeholder check for leather; may get removed on GUI rework
                    if (((ItemArmor) itemStackMain.getItem()).getArmorMaterial() == LEATHER) {
                        itemStackSecondary = new ItemStack(primaryItem);
                        itemStackSecondary.setItemDamage(chainedDamage);
                    }
                }
            }

            //This checks for elytra's and attaches the 'attached elytra' NBT to the item
            else if (itemStackUnder.getItem() instanceof ItemElytra) {
                if (((ItemArmor) itemStackMain.getItem()).armorType == EntityEquipmentSlot.CHEST) {
                    if ((((ItemArmor) itemStackMain.getItem()).getArmorMaterial() == LEATHER) || (((ItemArmor) itemStackMain.getItem()).getArmorMaterial() == CHAINED_LEATHER)) {
                        nbtIntGen(itemStackMain,"attached_elytra",432);
                    }
                }
            }

            //This checks for totems and attaches the 'totemed' NBT to the item
            else if (itemStackBonus.getItem() == Items.TOTEM_OF_UNDYING) {
                if (!itemStackMain.getEnchantmentTagList().equals(EnchantmentVanishingCurse.REGISTRY)) {
                    nbtIntGen(itemStackMain,"totemed",0);
                }
            }

            //this triggers if only the first slot is filled; used to ensure deactivation of the buttons
            else {
                primaryActive = false;
                secondaryActive = false;
                bonusActive = false;
                itemStackPrimary = ItemStack.EMPTY;
                itemStackSecondary = ItemStack.EMPTY;
            }
        }

        //redundancy for the sake of redundancies sake
        else {
            primaryActive = false;
            secondaryActive = false;
            bonusActive = false;
            itemStackPrimary = ItemStack.EMPTY;
            itemStackSecondary = ItemStack.EMPTY;
        }
    }

    /*
     * Handles the given Button-click on the server, currently only used by enchanting, and this Container, name is for legacy.
     */
    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id) {
        //performs the combination and creation of the generated Item
        if(((primaryActive || secondaryActive) && !bonusActive)) {
            //when button one is pressed
            if (id == 0) {
                this.armorerSlots.setInventorySlotContents(0, itemStackPrimary);
                this.armorerSlots.setInventorySlotContents(1, ItemStack.EMPTY);
            }
            //when button two is pressed
            else if (id == 1) {
                this.armorerSlots.setInventorySlotContents(0, itemStackSecondary);
                this.armorerSlots.setInventorySlotContents(1, ItemStack.EMPTY);
            }
        /*
         * when button one is pressed and the bonus item is a totem;
         * might be able to merge this into the first one
         */
        }else if((primaryActive && bonusActive)){
            if(id == 0){
                this.armorerSlots.setInventorySlotContents(0, itemStackPrimary);
                this.secondarySlot.setInventorySlotContents(0, ItemStack.EMPTY);

            }
        }
        //runs after all button actions have been done ensuring a hard reset of the variables
        itemStackSecondary = null;
        primaryActive = false;
        secondaryActive = false;
        bonusActive = false;

        return super.enchantItem(playerIn, id);
    }

    //Most of the remaining code is simply core setup for the container
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

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendWindowProperty(this,0,this.forgingCost);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        if (id == 0)
        {
            this.forgingCost = data;
        }
    }

    /**
     * Called when the container is closed.
     *
     * I am getting very specific and semi non-repeatable inventory dupes which i believe are coming from here;
     * Not entirely sure what is needed to resolve it as the seem to be irregular.
     * I commented out some of the code in the hopes of resolving this; To no avail.... I think...
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.armorerSlots);
            //this.armorerSlots.closeInventory(player);
            this.clearContainer(playerIn, this.world, this.secondarySlot);
            //this.secondarySlot.closeInventory(player);
            //this.clearContainer(playerIn, this.world, this.player.inventory);
            //this.player.inventory.closeInventory(player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
