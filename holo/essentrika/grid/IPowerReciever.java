package holo.essentrika.grid;

import holo.essentrika.map.World;

public interface IPowerReciever
{
	public int requiredPower();
	
	public boolean isConnectedToPowerGrid(World world, int x, int y);
	
	public int currentPowerLevel();
	
	public IGenerator getPowerSource(World world, int x, int y);
}
