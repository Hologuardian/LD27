package holo.essentrika.modules;

import holo.essentrika.grid.IConduit;
import holo.essentrika.grid.IGenerator;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ModuleLand implements IModule, IPowerReciever
{
	Image sprite;
	Image poweredSprite;
	int powerValue = 0;
	IGenerator powerSource;
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
		if(isConnectedToPowerGrid(world, x, y) && currentPowerLevel() < requiredPower())
		{
			IModule module = world.getModuleAt(x + 1, y);
			IModule module1 = world.getModuleAt(x - 1, y);
			IModule module2 = world.getModuleAt(x, y + 1);
			IModule module3 = world.getModuleAt(x, y - 1);
			
			IGenerator power = null;
			
			if (module instanceof IConduit && power == null)
			{
				 power = ((IConduit) module).getClosestValidPowerPlant(world, this, x + 1, y, this.requiredPower() - this.currentPowerLevel(), 0);
			}
			
			if (module1 instanceof IConduit && power == null)
			{
				power = ((IConduit) module1).getClosestValidPowerPlant(world, this, x - 1, y, this.requiredPower() - this.currentPowerLevel(), 0);
			}
			
			if (module2 instanceof IConduit && power == null)
			{
				power = ((IConduit) module2).getClosestValidPowerPlant(world, this, x, y + 1, this.requiredPower() - this.currentPowerLevel(), 0);
			}
			
			if (module3 instanceof IConduit && power == null)
			{
				power = ((IConduit) module3).getClosestValidPowerPlant(world, this, x, y - 1, this.requiredPower() - this.currentPowerLevel(), 0);
			}
			
			if (power != null)
			{
				if(power.requestPower(requiredPower(), this))
				{
					powerSource = power;
					powerValue = requiredPower();
				}
				
			}
		}
	}

	@Override
	public int getUpgradeCost(IModule upgrade) 
	{
		int id = upgrade.getID();
		return id == ModuleCreator.modulePlayerGeneratorID ? 500 : id == ModuleCreator.moduleConduitID ? 50 : 0;
	}

	@Override
	public List<Integer> getUpgrades() 
	{
		List<Integer> modules = new ArrayList<Integer>();
		modules.add(ModuleCreator.modulePlayerGeneratorID);
		modules.add(ModuleCreator.moduleConduitID);
		return modules;
	}

	@Override
	public Image getIcon(World world, int x, int y)
	{
		return currentPowerLevel() >= requiredPower() ? poweredSprite : sprite;
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
		if (mod instanceof IGenerator || mod instanceof IConduit)
			return true;
		return false;
	}

	@Override
	public int currentPowerLevel()
	{
		return powerValue;
	}

	@Override
	public IGenerator getPowerSource(World world, int x, int y)
	{
		return powerSource;
	}

	@Override
	public int getUpgradeFromKey(int key)
	{
		return key == Input.KEY_G ? ModuleCreator.modulePlayerGeneratorID : key == Input.KEY_C ? ModuleCreator.moduleConduitID : -1;
	}

	@Override
	public void removeModule(World world, int x, int y)
	{
		if(getPowerSource(world, x, y) != null)
			getPowerSource(world, x, y).unregisterReciever(this);
	}

	@Override
	public void denyPower()
	{
		powerSource = null;
		powerValue = 0;
	}
}
