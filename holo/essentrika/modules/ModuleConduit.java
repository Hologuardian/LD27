package holo.essentrika.modules;

import holo.essentrika.map.World;

import java.util.List;

import org.newdawn.slick.Image;

public class ModuleConduit implements IModule
{

	@Override
	public int getID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(World world)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getUpgradeCost(IModule upgrade)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getUpgrades()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getIcon(World world, int x, int y)
	{
		return null;
	}

	@Override
	public String getModuleName()
	{
		return "Power Conduit";
	}

}
