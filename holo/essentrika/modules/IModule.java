package holo.essentrika.modules;

import java.util.List;

import org.newdawn.slick.Image;

public interface IModule 
{
	public int getID();
	
	public void update();
	
	public int getUpgradeCost(IModule upgrade);
	
	public List<Integer> getUpgrades();
	
	public Image getIcon();
}
