package holo.essentrika.grid;

import holo.essentrika.map.World;

public interface IConduit
{
	public void update(World world, int x, int y);
	
	public void getClosestValidPowerPlant(World world, int x, int y);
}
