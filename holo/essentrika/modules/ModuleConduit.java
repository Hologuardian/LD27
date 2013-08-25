package holo.essentrika.modules;

import holo.essentrika.grid.IConduit;
import holo.essentrika.grid.IGenerator;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleConduit implements IModule, IConduit
{
	Image[] sprite = new Image[16];
	public ModuleConduit() throws SlickException
	{
		sprite[0] = new Image("res/Conduit.png");
		sprite[1] = new Image("res/Conduit1.png");
		sprite[2] = new Image("res/Conduit2.png");
		sprite[3] = new Image("res/Conduit3.png");
		sprite[4] = new Image("res/Conduit4.png");
		sprite[5] = new Image("res/Conduit5.png");
		sprite[6] = new Image("res/Conduit6.png");
		sprite[7] = new Image("res/Conduit7.png");
		sprite[8] = new Image("res/Conduit8.png");
		sprite[9] = new Image("res/Conduit9.png");
		sprite[10] = new Image("res/Conduit10.png");
		sprite[11] = new Image("res/Conduit11.png");
		sprite[12] = new Image("res/Conduit12.png");
		sprite[13] = new Image("res/Conduit13.png");
		sprite[14] = new Image("res/Conduit14.png");
		sprite[15] = new Image("res/Conduit15.png");
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
		int id = upgrade.getID();
		return id == ModuleCreator.moduleLandID ? -20 : 0;
	}

	@Override
	public List<Integer> getUpgrades() 
	{
		List<Integer> modules = new ArrayList<Integer>();
		modules.add(ModuleCreator.moduleLandID);
		return modules;
	}

	@Override
	public Image getIcon(World world, int x, int y)
	{
		IModule module = world.getModuleAt(x + 1, y);
		IModule module1 = world.getModuleAt(x - 1, y);
		IModule module2 = world.getModuleAt(x, y - 1);
		IModule module3 = world.getModuleAt(x, y + 1);
		
		byte dir = 0;
		if(isGridType(module))
			dir += 2;
		if(isGridType(module1))
			dir += 8;
		if(isGridType(module2))
			dir += 1;
		if(isGridType(module3))
			dir += 4;
		
		return sprite[dir];
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
		
		if(module != askingModule && isGenerator(module)&& ((IGenerator)module).powerGenerated() - ((IGenerator)module).currentPower() >= power)
		{
			generator = (IGenerator) module;
		}
		else if(module != askingModule && module instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module).getClosestValidPowerPlant(world, this, x + 1, y, power, ++distance);
		}
		
		
		if(module1 != askingModule && isGenerator(module1)&& ((IGenerator)module1).powerGenerated() - ((IGenerator)module1).currentPower() >= power)
		{
			generator =  (IGenerator) module1;
		}
		else if(module1 != askingModule && module1 instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module1).getClosestValidPowerPlant(world, this, x - 1, y, power, ++distance);
		}
		
		
		if(module2 != askingModule && isGenerator(module2)&& ((IGenerator)module2).powerGenerated() - ((IGenerator)module2).currentPower() >= power)
		{
			generator =  (IGenerator) module2;
		}
		else if(module2 != askingModule && module2 instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module2).getClosestValidPowerPlant(world, this, x, y + 1, power, ++distance);
		}
		
		
		if(module3 != askingModule && isGenerator(module3)&& ((IGenerator)module3).powerGenerated() - ((IGenerator)module3).currentPower() >= power)
		{
			generator =  (IGenerator) module3;
		}
		else if(module3 != askingModule && module3 instanceof IConduit && generator == null)
		{
			generator =  ((IConduit)module3).getClosestValidPowerPlant(world, this, x, y - 1, power, ++distance);
		}
		
		return generator;
	}
	
	public boolean isGenerator(IModule mod)
	{
		if (mod instanceof IGenerator)
			return true;
		return false;
	}
	
	public boolean isGridType(IModule mod)
	{
		if (mod instanceof IGenerator || mod instanceof IConduit)
			return true;
		return false;
	}

	@Override
	public int getUpgradeFromKey(int key)
	{
		return -1;
	}

	@Override
	public void removeModule(World world, int x, int y)
	{
		
	}
}
