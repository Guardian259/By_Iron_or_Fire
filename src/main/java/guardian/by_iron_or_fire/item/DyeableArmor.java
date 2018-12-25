package guardian.by_iron_or_fire.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static guardian.by_iron_or_fire.init.ItemInit.CHAINED_LEATHER;

/**
 * within this Class much of the codes body has been riped from ItemArmor.
 * Material Leather has been replace with Chained_Leather to override
 * the overlay and dyeing system and allow for the modded variants
 *
 * TODO: Possibly convert the use of CHAINED_LEATHER to HashMaps to allow for further Integration and compatibility
 */
public class DyeableArmor extends ArmorBase{

    public DyeableArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
    }

    //Returns whether the specified armor ItemStack has a color
    @Override
    public boolean hasColor(ItemStack stack)
    {
        if (this.getArmorMaterial() != CHAINED_LEATHER)
        {
            return false;
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
        }
    }


    //Returns the color for the specified armor ItemStack.
    @Override
    public int getColor(ItemStack stack)
    {
        if (this.getArmorMaterial() != CHAINED_LEATHER)
        {
            return 16777215;
        } else {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
                {
                    return nbttagcompound1.getInteger("color");
                }
            }
            return 10511680;
        }
    }


    //Removes the color from the specified armor ItemStack.
    @Override
    public void removeColor(ItemStack stack)
    {
        if (this.getArmorMaterial() == CHAINED_LEATHER)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1.hasKey("color"))
                {
                   nbttagcompound1.removeTag("color");
                }
            }
        }
    }

    //Sets the color of the specified armor ItemStack
    @Override
    public void setColor(ItemStack stack, int color)
    {
        if (this.getArmorMaterial() != CHAINED_LEATHER)
        {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound == null)
            {
                nbttagcompound = new NBTTagCompound();
                stack.setTagCompound(nbttagcompound);
            }

            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (!nbttagcompound.hasKey("display", 10))
            {
                nbttagcompound.setTag("display", nbttagcompound1);
            }

            nbttagcompound1.setInteger("color", color);
        }
    }

    /**
     * Determines if this armor will be rendered with the secondary 'overlay' texture.
     * If this is true, the first texture will be rendered using a tint of the color
     * specified by getColor(ItemStack)
     *
     * @param stack The stack
     * @return true/false
     */
    @Override
    public boolean hasOverlay(ItemStack stack)
    {
        return this.getArmorMaterial() == CHAINED_LEATHER || getColor(stack) != 0x00FFFFFF;
    }
}
