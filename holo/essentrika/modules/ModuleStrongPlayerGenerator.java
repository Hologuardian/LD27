package holo.essentrika.modules;

import holo.essentrika.map.World;

import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ModuleStrongPlayerGenerator implements IModule
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
		return "Upgraded Generator";
	}

}
