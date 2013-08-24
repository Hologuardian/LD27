package holo.essentrika.grid;

import holo.essentrika.map.World;
import holo.essentrika.modules.IModule;

import java.util.ArrayList;

public interface IGeneratorModule
{
	public int powerGenerated();
	
	public int currentPower(World world, int x, int y);
	
	public boolean requestPower(int request, IPowerReciever module);
	
	public ArrayList<IModule> getPoweredModules(World world, int x, int y);
}
