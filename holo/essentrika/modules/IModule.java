package holo.essentrika.modules;

import holo.essentrika.map.World;

import java.util.List;

import org.newdawn.slick.Image;

public interface IModule 
{
	public int getID();
	
	public void update(World world);
	
	public int getUpgradeCost(IModule upgrade);
	
	public List<Integer> getUpgrades();
	
	public Image getIcon(World world, int x, int y);
	
	public String getModuleName();
}