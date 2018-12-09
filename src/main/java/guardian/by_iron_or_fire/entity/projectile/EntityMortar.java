package guardian.by_iron_or_fire.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMortar extends Entity implements IProjectile {

    public int xTile;
    public int yTile;
    public int zTile;
    public Block inTile;

    /** The owner of this shell. */
    public Entity shootingEntity;
    public int ticksInGround;
    public int ticksInAir;
    public double damage;
    /**placeholder damage value*/
    public double damageMax = 20;

    /**distance from mortar impact*/
    public double distFrom;

    public EntityMortar(World worldIn) {
        super(worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
    }

    /**
     * determines how much damage a player will take
     * based off of there distance from the impact
     *
     * not sure i need this; keeping for legacy
     */
    public void entityDamaged(Entity entity){
        distFrom = entity.getDistanceSq(this.posX,this.posY,this.posZ);
        if(entity == shootingEntity){
            damage = ((damageMax * (1 / distFrom)) * 1.25);
        }else{
            damage = damageMax * (1 / distFrom);
        }
    }

    /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {

    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

}
