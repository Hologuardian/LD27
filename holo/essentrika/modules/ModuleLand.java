package holo.essentrika.modules;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;

public class ModuleLand implements IModule
{
	
	@Override
	public int getID() 
	{
		return ModuleCreator.moduleLandID;
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
		List<Integer> modules = new ArrayList<Integer>();
		modules.add(ModuleCreator.modulePlayerGeneratorID);
		return modules;
	}

	@Override
	public Image getIcon()
	{
		return null;
	}

}
