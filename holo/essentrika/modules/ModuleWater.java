package holo.essentrika.modules;

import holo.essentrika.map.World;

import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleWater implements IModule
{
	Image sprite;
	public ModuleWater() throws SlickException
	{
		sprite = new Image("res/Water.png");
	}

	@Override
	public int getID()
	{
		return ModuleCreator.moduleWaterID;
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
		return "Water";
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
