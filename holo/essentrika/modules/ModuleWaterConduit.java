package holo.essentrika.modules;

import holo.essentrika.grid.IConduit;
import holo.essentrika.grid.IGenerator;
import holo.essentrika.map.World;

import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ModuleWaterConduit implements IModule, IConduit
{
	Image sprite;
	public ModuleWaterConduit() throws SlickException
	{
		sprite = new Image("res/WaterConduit.png");
	}
	
	@Override
	public int getID()
	{
		return ModuleCreator.moduleConduitID;
	}

	@Override
	public void update(World world, int x, int y)
	{
		
	}

	@Override
	public int getUpgradeCost(IModule upgrade)
	{
		return 0;
	}

	@Override
	public List<Integer> getUpgrades()
	{
		return null;
	}

	@Override
	public Image getIcon(World world, int x, int y)
	{
		return sprite;
	}

	@Override
	public String getModuleName()
	{
		return "Power Conduit";
	}

	@Override
	public IGenerator getClosestValidPowerPlant(World world, IModule askingModule, int x, int y, int power, int distance)
	{
		if (distance >= 16)
			return null;
		IModule module = world.getModuleAt(x + 1, y);
		IModule module1 = world.getModuleAt(x - 1, y);
		IModule module2 = world.getModuleAt(x, y + 1);
		IModule module3 = world.getModuleAt(x, y - 1);
		
		IGenerator generator  = null;
		
		if(module != askingModule && isGridType(module)&& ((IGenerator)module).powerGenerated() - ((IGenerator)module).currentPower() >= power)
		{
			generator = (IGenerator) module;
		}
		else if(module != askingModule && module instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module).getClosestValidPowerPlant(world, this, x + 1, y, power, ++distance);
		}
		
		
		if(module1 != askingModule && isGridType(module1)&& ((IGenerator)module1).powerGenerated() - ((IGenerator)module1).currentPower() >= power)
		{
			generator =  (IGenerator) module1;
		}
		else if(module1 != askingModule && module1 instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module1).getClosestValidPowerPlant(world, this, x - 1, y, power, ++distance);
		}
		
		
		if(module2 != askingModule && isGridType(module2)&& ((IGenerator)module2).powerGenerated() - ((IGenerator)module2).currentPower() >= power)
		{
			generator =  (IGenerator) module2;
		}
		else if(module2 != askingModule && module2 instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module2).getClosestValidPowerPlant(world, this, x, y + 1, power, ++distance);
		}
		
		
		if(module3 != askingModule && isGridType(module3)&& ((IGenerator)module3).powerGenerated() - ((IGenerator)module3).currentPower() >= power)
		{
			generator =  (IGenerator) module3;
		}
		else if(module3 != askingModule && module3 instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module3).getClosestValidPowerPlant(world, this, x, y - 1, power, ++distance);
		}
		
		return generator;
	}
	
	public boolean isGridType(IModule mod)
	{
		if (mod instanceof IGenerator)
			return true;
		return false;
	}

	@Override
	public int getUpgradeFromKey(int key)
	{
		return -1;
	}

	@Override
	public int getKeyFromUpgradeID(int id)
	{
		return id == ModuleCreator.moduleLandID ? Input.KEY_S : -1;
	}

	@Override
	public void removeModule(World world, int x, int y)
	{
		
	}
}
