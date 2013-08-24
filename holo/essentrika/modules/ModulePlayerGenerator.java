package holo.essentrika.modules;

import holo.essentrika.grid.IGeneratorModule;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModulePlayerGenerator implements IModule, IGeneratorModule
{
	Image sprite;
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
		return id == ModuleCreator.moduleLandID ? -400: id == ModuleCreator.moduleStrongPlayerGeneratorID ? 1000 : 0;
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
	public int currentPower(World world, int x, int y)
	{
		return 0;
	}

	@Override
	public ArrayList<IModule> getPoweredModules(World world, int x, int y)
	{
		return null;
	}

	@Override
	public boolean requestPower(int request, IPowerReciever module)
	{
		return false;
	}

}
