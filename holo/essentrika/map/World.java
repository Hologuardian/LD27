package holo.essentrika.map;

import holo.essentrika.modules.IModule;
import holo.essentrika.modules.ModuleCreator;
import holo.essentrika.states.GameState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.newdawn.slick.SlickException;

public class World 
{
	public HashMap<Long, IModule> modules;
	public static final String saveFileLocation = "save";
	Random rand = new Random();

	public World(boolean load)
	{
		modules = new HashMap<Long, IModule>();
		if (!load)
		{
			try
			{
				setModule(ModuleCreator.createModule(ModuleCreator.modulePlayerGeneratorID), 0, 0);
				setModule(ModuleCreator.createModule(ModuleCreator.moduleConduitID), 0, 1);
			} catch (SlickException e)
			{
				e.printStackTrace();
			}
		}
		else
			load();
	}
	
	public void setModule(IModule module, Long coord)
	{
		int x = fastfloor(coord >> 24);
		int y = fastfloor(coord - (x << 24)) - (1 << 24) / 2;
		if (modules.containsKey(coord))
		{
			modules.get(coord).removeModule(this, x, y);
		}
		modules.put(coord, module);
	}
	
	public void setModule(IModule module, int x, int y)
	{
		Long coord = hashCoord(x, y);
		if (modules.containsKey(coord))
		{
			modules.get(coord).removeModule(this, x, y);
		}
		modules.put(coord, module);
	}
	
	public IModule getModuleAt(Long coord)
	{
		if (!modules.containsKey(coord))
			generateModuleAt(coord);
		return modules.get(coord);
	}

	public IModule getModuleAt(int x, int y)
	{
		Long coord = hashCoord(x, y);
		if (!modules.containsKey(coord))
			generateModuleAt(coord);
		return modules.get(coord);
	}

	public void generateModuleAt(long coord)
	{
		int x = fastfloor(coord >> 24);
		int y = fastfloor(coord - (x << 24)) - (1 << 24) / 2;
		IModule module = null;
		try
		{
			module = ModuleCreator.createModule(getRandomModuleID(x, y));
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
		modules.put(coord, module);
	}

	public int getRandomModuleID(int x, int y)
	{
		int id = 0;
		double weight = Math.abs(SimplexNoise.noise(x * rand.nextInt(255), y * rand.nextInt(255)) * 15);
		if(weight < 8)
			id = ModuleCreator.moduleLandID;
		else if(weight < 8.5)
			id = ModuleCreator.moduleWorldGeneratorID;
		else
			id = ModuleCreator.moduleWaterID;
		return id;
	}

	public void save()
	{
		try
		{

			File file = new File(saveFileLocation);
			file.createNewFile();

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			out.println(GameState.totalTime);
			out.println(GameState.money);
			out.println(GameState.totalMoney);
			out.println(GameState.requiredPoweredTiles);
			out.println(GameState.requiredDifference);
			out.println(GameState.timer);
			
			for(Long coord : modules.keySet())
			{
				//				System.out.println(coord[0] + ":" + coord[1] + ":" + modules.get(coord).getID());
				out.println(coord + ":" + modules.get(coord).getID());
			}

			out.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void load()
	{
		try
		{
			File file = new File(saveFileLocation);
			if (!file.exists())
				return;

			Scanner sc = new Scanner(file);
			if(sc.hasNext())
				GameState.totalTime = Double.parseDouble(sc.nextLine());
			if(sc.hasNext())
				GameState.money = Integer.parseInt(sc.nextLine());
			if(sc.hasNext())
				GameState.totalMoney = Integer.parseInt(sc.nextLine());
			if(sc.hasNext())
				GameState.requiredPoweredTiles = Integer.parseInt(sc.nextLine());
			if(sc.hasNext())
				GameState.requiredDifference = Float.parseFloat(sc.nextLine());
			if(sc.hasNext())
				GameState.timer = Double.parseDouble(sc.nextLine());
			while(sc.hasNext())
			{
				String data = sc.nextLine();
				String[] datums = data.split(":");
				Long coord = Long.parseLong(datums[0]);
				int id = Integer.parseInt(datums[1]);
				modules.put(coord, ModuleCreator.createModule(id));
			}
			sc.close();
		}
		catch(IOException | SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public Set<Long> getKeySet()
	{
		return modules.keySet();
	}
	
	private long hashCoord(int x, int y)
	{
		return (long) ((x << 24) | y + (1 << 24) / 2);
	}
	
	private int fastfloor(double x) 
	{
		return x > 0 ? (int) x : (int) x - 1;
	}
}
