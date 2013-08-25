package holo.essentrika.grid;

import holo.essentrika.map.World;
import holo.essentrika.modules.IModule;

import java.util.ArrayList;

public interface IGenerator
{
	public int powerGenerated();
	
	public int currentPower();
	
	public boolean requestPower(int request, IPowerReciever module);
	
	public ArrayList<IPowerReciever> getPoweredModules(World world, int x, int y);
	
	public void unregisterReciever(IPowerReciever module);
}
