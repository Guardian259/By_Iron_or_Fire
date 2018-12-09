package guardian.by_iron_or_fire.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class SecondaryArmorerSlot extends Slot {
    public SecondaryArmorerSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        if(stack.getItem() instanceof ItemPotion){
            return true;
        }else if(stack.getItem() == Items.TOTEM_OF_UNDYING){
            return true;
        }else{
            return false;
        }

    }
}
