package holo.essentrika.modules;

import holo.essentrika.grid.IGeneratorModule;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleWorldGenerator implements IModule, IGeneratorModule
{
	Image sprite;
	public ModuleWorldGenerator() throws SlickException
	{
		sprite = new Image("res/WorldGenerator.png");
	}
	
	@Override
	public int getID()
	{
		return 2;
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
		return "Mysterious Generator";
	}

	@Override
	public int powerGenerated()
	{
		return 15;
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
