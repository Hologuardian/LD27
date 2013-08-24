package holo.essentrika.modules;

import holo.essentrika.grid.IConduit;
import holo.essentrika.grid.IGeneratorModule;
import holo.essentrika.map.World;

import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleConduit implements IModule, IConduit
{
	Image sprite;
	public ModuleConduit() throws SlickException
	{
		sprite = new Image("res/Conduit.png");
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
	public IGeneratorModule getClosestValidPowerPlant(World world, IModule askingModule, int x, int y, int power)
	{
		IModule module = world.getModuleAt(x + 1, y);
		IModule module1 = world.getModuleAt(x - 1, y);
		IModule module2 = world.getModuleAt(x, y + 1);
		IModule module3 = world.getModuleAt(x, y - 1);
		if(isGridType(module)&& ((IGeneratorModule)module).powerGenerated() - ((IGeneratorModule)module).currentPower(world, x + 1, y) > power)
		{
			return (IGeneratorModule) module;
		}
		else if(module != askingModule && module instanceof IConduit)
		{
			return ((IConduit)module).getClosestValidPowerPlant(world, this, x + 1, y, power);
		}
		
		
		if(isGridType(module1)&& ((IGeneratorModule)module1).powerGenerated() - ((IGeneratorModule)module1).currentPower(world, x - 1, y) > power)
		{
			return (IGeneratorModule) module1;
		}
		else if(module1 != askingModule && module1 instanceof IConduit)
		{
			return ((IConduit)module1).getClosestValidPowerPlant(world, this, x - 1, y, power);
		}
		
		
		if(isGridType(module2)&& ((IGeneratorModule)module2).powerGenerated() - ((IGeneratorModule)module2).currentPower(world, x, y + 1) > power)
		{
			return (IGeneratorModule) module2;
		}
		else if(module1 != askingModule && module1 instanceof IConduit)
		{
			return ((IConduit)module1).getClosestValidPowerPlant(world, this, x, y + 1, power);
		}
		
		
		if(isGridType(module3)&& ((IGeneratorModule)module3).powerGenerated() - ((IGeneratorModule)module3).currentPower(world, x, y - 1) > power)
		{
			return (IGeneratorModule) module3;
		}
		else if(module2 != askingModule && module2 instanceof IConduit)
		{
			return ((IConduit)module2).getClosestValidPowerPlant(world, this, x, y - 1, power);
		}
		
		return null;
	}
	
	public boolean isGridType(IModule mod)
	{
		if (mod instanceof IGeneratorModule)
			return true;
		return false;
	}

}
