package net.crazysnailboy.mods.cobbleores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.crazysnailboy.mods.cobbleores.util.RandomDropList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod(modid = CobbleOresMod.MODID, name = CobbleOresMod.NAME, version = CobbleOresMod.VERSION)
public class CobbleOresMod
{

	public static final String MODID = "csb_cobbleores";
	public static final String NAME = "Cobblestone Generators Drop Ores";
	public static final String VERSION = "1.0";

	@Instance(MODID)
	public static CobbleOresMod INSTANCE;

	public static Logger logger = LogManager.getLogger(MODID);

	private static RandomDropList droplist = new RandomDropList();


	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		droplist.buildDropsList();
	}


	@EventBusSubscriber
	public static class EventHandlers
	{

		@SubscribeEvent
		public static void onHarvestDrops(HarvestDropsEvent event)
		{
			if (event.getState().getBlock() == Blocks.COBBLESTONE)
			{
				if (!event.isSilkTouching())
				{
					boolean foundWater = false; boolean foundLava = false;
					for ( BlockPos pos : new BlockPos[] { event.getPos().east(), event.getPos().west(), event.getPos().north(),event.getPos().south() })
					{
						Block block = event.getWorld().getBlockState(pos).getBlock();
						if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) foundWater = true;
						if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) foundLava = true;
					}
					if (foundWater && foundLava)
					{
						ItemStack stack = droplist.getRandomDrop();
						event.getDrops().clear();
						event.getDrops().add(stack);
						event.setDropChance(1.0F);
					}
				}
			}
		}

	}


}
