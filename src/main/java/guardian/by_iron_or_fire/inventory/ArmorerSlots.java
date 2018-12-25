package guardian.by_iron_or_fire.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ArmorerSlots extends Slot {

    public ArmorerSlots(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (stack.getItem() instanceof ItemArmor) {
            return true;
        } else if(stack.getItem() instanceof ItemElytra){
            return true;
        } else {
            return false;
        }
    }

}
