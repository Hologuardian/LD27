package holo.essentrika.modules;

import java.util.List;

import org.newdawn.slick.Image;

public class ModulePlayerGenerator implements IModule
{

	@Override
	public int getID()
	{
		return ModuleCreator.modulePlayerGeneratorID;
	}

	@Override
	public void update()
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
	public Image getIcon()
	{
		return null;
	}

}
