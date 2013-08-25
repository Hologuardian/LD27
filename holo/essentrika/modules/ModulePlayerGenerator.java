package holo.essentrika.modules;

import holo.essentrika.grid.IGenerator;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ModulePlayerGenerator implements IModule, IGenerator
{
	Image sprite;
	int power = 0;
	ArrayList<IPowerReciever> powerRecievers = new ArrayList<IPowerReciever>();
	public ModulePlayerGenerator() throws SlickException
	{
		sprite = new Image("res/PlayerGenerator.png");
	}

	@Override
	public int getID()
	{
		return ModuleCreator.modulePlayerGeneratorID;
	}

	@Override
	public void update(World world, int x, int y)
	{
		
	}

	@Override
	public int getUpgradeCost(IModule upgrade)
	{
		int id = upgrade.getID();
		return id == ModuleCreator.moduleLandID ? -400: id == ModuleCreator.moduleStrongPlayerGeneratorID ? 500 : 0;
	}

	@Override
	public List<Integer> getUpgrades()
	{
		List<Integer> modules = new ArrayList<Integer>();
		modules.add(ModuleCreator.moduleLandID);
		modules.add(ModuleCreator.moduleStrongPlayerGeneratorID);
		return modules;
	}

	@Override
	public Image getIcon(World world, int x, int y)
	{
		return sprite;
	}

	@Override
	public String getModuleName()
	{
		return "Power Generator";
	}

	@Override
	public int powerGenerated()
	{
		return 10;
	}

	@Override
	public int currentPower()
	{
		return power;
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
		return key == Input.KEY_G ? ModuleCreator.moduleStrongPlayerGeneratorID : key == Input.KEY_S ? ModuleCreator.moduleLandID : -1;
	}

	@Override
	public int getKeyFromUpgradeID(int id)
	{
		return id == ModuleCreator.moduleStrongPlayerGeneratorID ? Input.KEY_G : id == ModuleCreator.moduleLandID ? Input.KEY_S : -1;
	}

	@Override
	public void removeModule(World world, int x, int y)
	{
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
}
