package holo.essentrika.grid;

import holo.essentrika.map.World;
import holo.essentrika.modules.IModule;

public interface IConduit
{
	public void update(World world, int x, int y);
	
	public IGenerator getClosestValidPowerPlant(World world, IModule askingModule, int x, int y, int power, int distance);
}
