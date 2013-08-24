package holo.essentrika.modules;

import org.newdawn.slick.SlickException;

public class ModuleCreator 
{
	public static final int moduleLandID = 0;
	public static final int modulePlayerGeneratorID = 1;
	public static final int moduleWorldGeneratorID = 2;
	public static final int moduleStrongPlayerGeneratorID = 3;
	public static final int moduleConduitID = 4;
	
	public static IModule createModule(int id) throws SlickException
	{
		switch(id)
		{
		case moduleLandID:
			return new ModuleLand();
		case modulePlayerGeneratorID:
			return new ModulePlayerGenerator();
		case moduleWorldGeneratorID:
			return new ModuleWorldGenerator();
		case moduleStrongPlayerGeneratorID:
			return new ModuleStrongPlayerGenerator();
		case moduleConduitID:
			return new ModuleConduit();
		}
		
		return null;
	}
}
