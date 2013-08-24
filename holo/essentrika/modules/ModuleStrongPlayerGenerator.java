package holo.essentrika.modules;

import holo.essentrika.grid.IGeneratorModule;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleStrongPlayerGenerator implements IModule, IGeneratorModule
{
	Image sprite;
	public ModuleStrongPlayerGenerator() throws SlickException
	{
		sprite = new Image("res/StrongPlayerGenerator.png");
	}
	
	@Override
	public int getID()
	{
		return ModuleCreator.moduleStrongPlayerGeneratorID;
	}

	@Override
	public void update(World world, int x, int y)
	{
		
	}

	@Override
	public int getUpgradeCost(IModule upgrade)
	{
		int id = upgrade.getID();
		return id == ModuleCreator.moduleLandID ? -1200: id == ModuleCreator.modulePlayerGeneratorID ? -800 : 0;
	}

	@Override
	public List<Integer> getUpgrades()
	{
		List<Integer> modules = new ArrayList<Integer>();
		modules.add(ModuleCreator.moduleLandID);
		modules.add(ModuleCreator.modulePlayerGeneratorID);
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
		return "Upgraded Generator";
	}

	@Override
	public int powerGenerated()
	{
		return 0;
	}

	@Override
	public int currentPower(World world, int x, int y)
	{
		return 0;
	}

	@Override
	public boolean requestPower(int request, IPowerReciever module)
	{
		return false;
	}

	@Override
	public ArrayList<IModule> getPoweredModules(World world, int x, int y)
	{
		return null;
	}

}
