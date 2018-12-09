package guardian.by_iron_or_fire.item;

import guardian.by_iron_or_fire.Main;
import guardian.by_iron_or_fire.init.ItemInit;
import guardian.by_iron_or_fire.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorBase extends ItemArmor implements IHasModel {


    public ArmorBase(String name,ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {

        super(materialIn, renderIndexIn, equipmentSlotIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);

        ItemInit.ITEMS.add(this);
    }


    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}