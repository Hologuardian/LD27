package holo.essentrika.modules;

import holo.essentrika.grid.IConduit;
import holo.essentrika.grid.IGenerator;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleWorldGenerator implements IModule, IGenerator, IPowerReciever
{
	Image sprite;
	int powerValue = 0;
	IGenerator powerSource;
	private int power = 0;
	ArrayList<IPowerReciever> powerRecievers = new ArrayList<IPowerReciever>();
	
	public ModuleWorldGenerator() throws SlickException
	{
		sprite = new Image("res/WorldGenerator.png");
	}
	
	@Override
	public int getLandValue()
	{
		return 5;
	}
	
	@Override
	public int getID()
	{
		return ModuleCreator.moduleWorldGeneratorID;
	}

	@Override
	public void update(World world, int x, int y)
	{
		if(getPowerSource(world, x, y) == null)
			powerValue = 0;
		
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
		return "Mysterious Generator";
	}

	@Override
	public int powerGenerated()
	{
		return 10;
	}

	@Override
	public int currentPower()
	{
		return power ;
	}

	@Override
	public ArrayList<IPowerReciever> getPoweredModules(World world, int x, int y)
	{
		return powerRecievers;
	}

	@Override
	public boolean requestPower(int request, IPowerReciever module)
	{
		if (this.powerGenerated() - this.currentPower() >= request)
		{
			powerRecievers.add(module);
			power += request;
			return true;
		}
		return false;
	}

	@Override
	public void unregisterReciever(IPowerReciever module)
	{
		powerRecievers.remove(module);
		module.denyPower();
		this.power -= module.requiredPower();
	}

	@Override
	public int getUpgradeFromKey(int key)
	{
		return -1;
	}

	@Override
	public int getKeyFromUpgradeID(int id)
	{
		return -1;
	}

	@Override
	public int requiredPower()
	{
		return 5;
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
	public void removeModule(World world, int x, int y)
	{
		if(getPowerSource(world, x, y) != null)
			getPowerSource(world, x, y).unregisterReciever(this);
		

		ArrayList<IPowerReciever> recievers = new ArrayList<IPowerReciever>();
		
		for(IPowerReciever module: powerRecievers)
		{
			recievers.add(module);
		}
		
		for(IPowerReciever module : recievers)
		{
			unregisterReciever(module);
		}
	}

	@Override
	public void denyPower()
	{
		powerSource = null;
		powerValue = 0;
	}
}
