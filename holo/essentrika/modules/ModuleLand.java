package holo.essentrika.modules;

import holo.essentrika.grid.IConduit;
import holo.essentrika.grid.IGeneratorModule;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleLand implements IModule, IPowerReciever
{
	Image sprite;
	Image poweredSprite;
	public ModuleLand() throws SlickException
	{
		sprite = new Image("res/Land.png");
		poweredSprite = new Image("res/LandPowered.png");
	}
	
	@Override
	public int getID() 
	{
		return ModuleCreator.moduleLandID;
	}

	@Override
	public void update(World world, int x, int y)
	{
		if(isConnectedToPowerGrid(world, x, y) && currentPower(world, x, y) <= requiredPower())
		{
			IModule module = world.getModuleAt(x + 1, y);
			IModule module1 = world.getModuleAt(x - 1, y);
			IModule module2 = world.getModuleAt(x, y + 1);
			IModule module3 = world.getModuleAt(x, y - 1);
			
			if (module instanceof IConduit)
			{
				((IConduit) module).getClosestValidPowerPlant(world, this, x + 1, y, this.requiredPower() - this.currentPower(world, x, y));
			}
			else if (module1 instanceof IConduit)
			{
				((IConduit) module1).getClosestValidPowerPlant(world, this, x - 1, y, this.requiredPower() - this.currentPower(world, x, y));
			}
			else if (module2 instanceof IConduit)
			{
				((IConduit) module2).getClosestValidPowerPlant(world, this, x, y + 1, this.requiredPower() - this.currentPower(world, x, y));
			}
			else if (module3 instanceof IConduit)
			{
				((IConduit) module3).getClosestValidPowerPlant(world, this, x, y - 1, this.requiredPower() - this.currentPower(world, x, y));
			}
		}
	}

	@Override
	public int getUpgradeCost(IModule upgrade) 
	{
		int id = upgrade.getID();
		return id == ModuleCreator.modulePlayerGeneratorID ? 500 : id == ModuleCreator.moduleWorldGeneratorID ? 1500: id == ModuleCreator.moduleConduitID ? 50 : 0;
	}

	@Override
	public List<Integer> getUpgrades() 
	{
		List<Integer> modules = new ArrayList<Integer>();
		modules.add(ModuleCreator.modulePlayerGeneratorID);
		modules.add(ModuleCreator.moduleWorldGeneratorID);
		modules.add(ModuleCreator.moduleConduitID);
		return modules;
	}

	@Override
	public Image getIcon(World world, int x, int y)
	{
		return currentPower(world, x, y) >= requiredPower() ? poweredSprite : sprite;
	}

	@Override
	public String getModuleName()
	{
		return "Bare Land";
	}

	@Override
	public int requiredPower()
	{
		return 1;
	}

	@Override
	public boolean isConnectedToPowerGrid(World world, int x, int y)
	{
		return isGridType(world.getModuleAt(x + 1, y)) ? true : isGridType(world.getModuleAt(x, y + 1)) ? true : isGridType(world.getModuleAt(x - 1, y)) ? true : isGridType(world.getModuleAt(x, y - 1)) ? true : false;
	}
	
	public boolean isGridType(IModule mod)
	{
		if (mod instanceof IGeneratorModule || mod instanceof IConduit)
			return true;
		return false;
	}

	@Override
	public int currentPower(World world, int x, int y)
	{
		return 0;
	}

	@Override
	public IGeneratorModule getPowerSource(World world, int x, int y)
	{
		return null;
	}

}
