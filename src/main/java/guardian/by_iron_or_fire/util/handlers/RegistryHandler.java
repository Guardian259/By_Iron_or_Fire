package guardian.by_iron_or_fire.util.handlers;

import guardian.by_iron_or_fire.init.ItemInit;
import guardian.by_iron_or_fire.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){

        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));

    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){

        for(Item item : ItemInit.ITEMS){

            if(item instanceof IHasModel){
                ((IHasModel)item).registerModels();
            }
        }
    }

    /**
     * Tiviacz1337's example code
     * I most likely will not be using this....
     * keeping for legacy...
     * for now...
     *
     * if (player.getItemStackFromSlot(EntityEquipmentSlot.slot).getItem() == Your_Item)
     * {
     *      code
     * }
    */

}
