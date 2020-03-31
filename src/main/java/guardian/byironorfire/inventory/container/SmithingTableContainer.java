package guardian.byironorfire.inventory.container;

import guardian.byironorfire.lists.InitLists;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;

/**
 * Once 1.16+ launches this shall be merged, using Mixin's, into the de facto Smithing Table
 */
public class SmithingTableContainer extends Container {

    private IWorldPosCallable worldPos;
    public PlayerEntity player;

    public SmithingTableContainer(int id,PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.DUMMY);
    }

    public SmithingTableContainer ( int id, PlayerInventory playerInventory, final IWorldPosCallable thisPos) {
        super(InitLists.SMITHING_TABLE, id);
        this.worldPos = thisPos;
        this.player = playerInventory.player;


        //Player Inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {

                this.addSlot(new Slot(playerInventory, j + (i * 9) + 9, 8 + j * 18, 84 + i * 18));

            }
        }

        //Hotbar Inventory
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + (i * 18), 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPos, player, Blocks.SMITHING_TABLE);
    }


    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        super.onCraftMatrixChanged(inventoryIn);
    }

}
