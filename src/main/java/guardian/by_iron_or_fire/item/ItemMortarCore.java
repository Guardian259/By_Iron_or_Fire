package guardian.by_iron_or_fire.item;

import guardian.by_iron_or_fire.Main;
import guardian.by_iron_or_fire.entity.projectile.EntityMortar;
import guardian.by_iron_or_fire.init.ItemInit;
import guardian.by_iron_or_fire.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemMortarCore extends Item implements IHasModel {

    public ItemMortarCore (String name){

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);

        ItemInit.ITEMS.add(this);
    }

    public EntityMortar createBomb(World worldIn){
        EntityMortar entityMortar = new EntityMortar(worldIn);
        return entityMortar;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
