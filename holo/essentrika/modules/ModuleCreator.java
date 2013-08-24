package holo.essentrika.modules;

public class ModuleCreator 
{
	public static final int moduleLandID = 0;
	public static final int modulePlayerGeneratorID = 1;
	public static final int moduleWorldGeneratorID = 2;
	
	public static IModule createModule(int id)
	{
		switch(id)
		{
		case moduleLandID:
			return new ModuleLand();
		case modulePlayerGeneratorID:
			return new ModulePlayerGenerator();
		case moduleWorldGeneratorID:
			return new ModuleWorldGenerator();
		}
		
		return null;
	}
}
