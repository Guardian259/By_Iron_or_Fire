package guardian.by_iron_or_fire.item;

import guardian.by_iron_or_fire.Main;
import guardian.by_iron_or_fire.init.ItemInit;
import guardian.by_iron_or_fire.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MortarBase extends Item implements IHasModel {


    public MortarBase(String name) {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);

        ItemInit.ITEMS.add(this);

    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand){
        return super.onItemRightClick(worldIn, player, hand);

    }

    public boolean isMortarCore(ItemStack stack){
        return stack.getItem() instanceof ItemMortarCore;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
