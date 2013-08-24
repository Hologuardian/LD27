package holo.essentrika.modules;

import holo.essentrika.map.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleLand implements IModule
{
	Image sprite;
	public ModuleLand() throws SlickException
	{
		sprite = new Image("res/Land.png");
	}
	
	@Override
	public int getID() 
	{
		return ModuleCreator.moduleLandID;
	}

	@Override
	public void update(World world)
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
		List<Integer> modules = new ArrayList<Integer>();
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
		return "Bare Land";
	}

}
