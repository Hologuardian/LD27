package holo.essentrika.map;

import holo.essentrika.modules.IModule;
import holo.essentrika.modules.ModuleCreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

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
			//			
			//			for (int i = 0; i < 1000; ++i)
			//			{
			//				Integer[] coords = new Integer[]{rand.nextInt(), rand.nextInt()};
			//				modules.put(coords, ModuleCreator.createModule(rand.nextInt(2)));
			//			}
		}
		else
			load();
	}

	public IModule getModuleAt(int x, int y)
	{
		Long coord = (long) ((x << 24) | y + (1 << 24) / 2);
		if (modules.containsKey(coord))
		{
			return modules.get(coord);
		}
		else
		{
			generateModuleAt((x << 24) | y + (1 << 24) / 2);
			return modules.get(coord);
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modules.put(coord, module);
	}

	public int getRandomModuleID(int x, int y)
	{
		int id = fastfloor(Math.abs(SimplexNoise.noise(x, y) * 15));
		if (id <= 0)
			id = 0;
		else if(id < 10)
			id = 0;
		else if(id < 13)
			id = 1;
		else
			id = 2;
		return id;
	}

	public void save()
	{
		try
		{

			File file = new File(saveFileLocation);
			file.createNewFile();

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

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

	// This method is a *lot* faster than using (int)Math.floor(x)
	private static int fastfloor(double x) 
	{
		return x > 0 ? (int) x : (int) x - 1;
	}
}
