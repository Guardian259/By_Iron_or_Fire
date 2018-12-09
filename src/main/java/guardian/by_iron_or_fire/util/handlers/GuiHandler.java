package guardian.by_iron_or_fire.util.handlers;

import guardian.by_iron_or_fire.client.gui.GuiArmorer;
import guardian.by_iron_or_fire.inventory.ContainerArmorer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final  int GUI_ARMORER_ID = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        //BlockPos pos = new BlockPos(x, y, z);

        switch (ID){
            case GUI_ARMORER_ID:

                return new ContainerArmorer(player.inventory, world, player);

            default: return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID){
            case GUI_ARMORER_ID:

                return new GuiArmorer(player.inventory, world);

            default: return null;
        }
    }
}
