package holo.essentrika.states;

import holo.essentrika.grid.IGeneratorModule;
import holo.essentrika.grid.IPowerReciever;
import holo.essentrika.map.World;
import holo.essentrika.modules.IModule;
import holo.essentrika.modules.ModuleCreator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class GameState extends BasicGameState
{
	private final int stateID;
	StateBasedGame game;
	public static int money = 50000;
	
	IModule selectedModule = null;
	ArrayList<Integer[]> selectedModuleUpgrades = new ArrayList<Integer[]>(10);
	int[] selectedModuleCoords = new int[]{0, 0};
	
	GradientFill fill;
	
	public static World world;
	int[] cameraCoords = new int[]{0, 0};
	
	int screenWidth;
	int screenHeight;
	
	int timer = 10000;
	
	
	public GameState(int stateID, StateBasedGame game, boolean load)
	{
		this.stateID = stateID;
		this.game = game;
		GameState.world = new World(load);
		System.out.println(load);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)throws SlickException 
	{
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)throws SlickException 
	{
		g.setBackground(Color.black);
		g.clear();
		g.setColor(Color.white);
//		g.drawRect(0, 0 + gc.getHeight() / 16, gc.getWidth(), gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8);
		
		int moduleWidth = gc.getWidth() / 64;
		int moduleHeight = (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64;
		
		for(int i = 0; i < gc.getWidth() / 64; ++i)
		{
			float x = i * 64;
			for (int j = 0; j < (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64; ++j)
			{
				float y = gc.getHeight() / 16 + j * 64;
				g.drawRect(x, y, 64, 64);
				Shape box = new Rectangle(x, y, 64, 64);
				Image sprite = world.getModuleAt(i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]).getIcon(world, i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]);
				g.drawImage(sprite, x, y);
				g.draw(box);
			}
		}

		Font font = gc.getDefaultFont();
		
		FontUtils.drawLeft(font, "Funds: " + money, gc.getWidth() / 8, 0);
		FontUtils.drawLeft(font, "Time Until Next Update: " + timer / 1000, gc.getWidth() / 8, font.getLineHeight());
		
		int x;
		int y;
		
		if (selectedModule != null)
		{
			Image sprite = selectedModule.getIcon(world, selectedModuleCoords[0], selectedModuleCoords[1]);
			g.drawImage(sprite, gc.getWidth() / 50, gc.getHeight() - gc.getHeight() / 9);
			
			x = gc.getWidth() / 50 + 70;
			y = gc.getHeight() - gc.getHeight() / 9;
			int textY = gc.getHeight() - gc.getHeight() / 9;
			
			String title = selectedModule.getModuleName() + " (" + selectedModuleCoords[0] + "," + selectedModuleCoords[1] + ")";
			FontUtils.drawLeft(font, title, x, textY);
			
			int textWidth = font.getWidth(title);
			
			if(selectedModule instanceof IGeneratorModule)
			{
				IGeneratorModule module = (IGeneratorModule)selectedModule;
				int lineY = textY + font.getLineHeight();
				String max = "Max Power: " + module.powerGenerated();
				String used = "Current Output: " + module.currentPower(world, selectedModuleCoords[0], selectedModuleCoords[1]);
				FontUtils.drawLeft(font, max, x, lineY);
				lineY += font.getLineHeight();
				FontUtils.drawLeft(font, used, x, lineY);
				textWidth = Math.max(textWidth, Math.max(font.getWidth(max), font.getWidth(used)));
			}
			else if (selectedModule instanceof IPowerReciever)
			{
				IPowerReciever mod = (IPowerReciever)selectedModule;
				int lineY = textY + font.getLineHeight();
				String required = "Power Needed: " + mod.requiredPower();
				String used = "Current Power: " + mod.currentPower(world, selectedModuleCoords[0], selectedModuleCoords[1]);
				String connected = "Connected to Grid: " + mod.isConnectedToPowerGrid(world, selectedModuleCoords[0], selectedModuleCoords[1]);
				FontUtils.drawLeft(font, required, x, lineY);
				lineY += font.getLineHeight();
				FontUtils.drawLeft(font, used, x, lineY);
				lineY += font.getLineHeight();
				FontUtils.drawLeft(font, connected, x, lineY);
				textWidth = Math.max(textWidth, Math.max(font.getWidth(required), Math.max(font.getWidth(used), font.getWidth(connected))));
			}
			
			x += 5 + textWidth;
			g.drawLine(x, gc.getHeight() - gc.getHeight() / 8, x, gc.getHeight());
			
			x += 5;

			selectedModuleUpgrades.clear();
			
			if (selectedModule.getUpgrades() != null)
			{
				FontUtils.drawLeft(font, "Upgrades", x, textY - font.getLineHeight());
				x += 5 + font.getWidth("Upgrades");
				for(Integer moduleID: selectedModule.getUpgrades())
				{
					IModule module = ModuleCreator.createModule(moduleID);
					sprite = module.getIcon(world, selectedModuleCoords[0], selectedModuleCoords[1]);
					g.drawImage(sprite, x, y);
					selectedModuleUpgrades.add(new Integer[]{x, y, x + sprite.getWidth(), y + sprite.getHeight()});
					x += 70;
					FontUtils.drawLeft(font, module.getModuleName(), x, textY);
					int lineY = textY + font.getLineHeight();
					FontUtils.drawLeft(font, "$" + selectedModule.getUpgradeCost(module), x, lineY);
					textWidth = Math.max(font.getWidth(module.getModuleName()), font.getWidth("$" + selectedModule.getUpgradeCost(module)));
					
					if(module instanceof IGeneratorModule)
					{
						IGeneratorModule mod = (IGeneratorModule)module;
						lineY += font.getLineHeight();
						String max = "Max Power: " + mod.powerGenerated();
						FontUtils.drawLeft(font, max, x, lineY);
						textWidth = Math.max(textWidth, font.getWidth(max));
					}
					
					x += 5 + textWidth;
				}
			}
		}
		
		String locationTitle = "Camera Location";
		String location = cameraCoords[0] + "," + -cameraCoords[1];
		
		
		y = gc.getHeight() / 32 - font.getLineHeight();
		x = gc.getWidth() - Math.max(font.getWidth(location), font.getWidth(locationTitle));
		FontUtils.drawLeft(font, locationTitle, x, y);
		y = gc.getHeight() / 32;
		FontUtils.drawLeft(font, location, x, y);
		
		
		if (getBoxFromMouseCoords(gc.getInput().getMouseX(), gc.getInput().getMouseY()) != null)
		{
			int[] coord = getBoxFromMouseCoords(gc.getInput().getMouseX(), gc.getInput().getMouseY());
			String mLocationTitle = "Cursor Location";
			String mLocation = coord[0] + "," + -coord[1];

			y = gc.getHeight() / 32 - font.getLineHeight();
			x -= Math.max(font.getWidth(mLocationTitle), font.getWidth(mLocation));
			FontUtils.drawLeft(font, mLocationTitle, x, y);
			y = gc.getHeight() / 32;
			FontUtils.drawLeft(font, mLocation, x, y);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)throws SlickException 
	{
		screenWidth = gc.getWidth();
		screenHeight = gc.getHeight();
		
//		int moduleWidth = gc.getWidth() / 64;
//		int moduleHeight = (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64;
		timer -= delta;
		if (timer <= 0)
		{
			updateAllModules();
			timer += 10000;
		}
		
	}
	
	public void updateAllModules()
	{
		ArrayList<Long> keys = new ArrayList<Long>();
		for(Long coord : world.getKeySet())
		{
			keys.add(coord);
		}
		
		for (Long coord: keys)
		{
			int x = fastfloor(coord >> 24);
			int y = fastfloor(coord - (x << 24)) - (1 << 24) / 2;
			IModule module = world.getModuleAt(coord);
			if(module instanceof IPowerReciever)
			{
				module.update(world, x, y);
			}
		}
	}
	
	public int[] getBoxFromMouseCoords(int x, int y)
	{
		int moduleWidth = screenWidth / 64;
		int moduleHeight = (screenHeight - screenHeight / 16 - screenHeight / 8) / 64;
		
		if (y < (screenHeight - screenHeight / 16 - screenHeight / 8) && y > screenHeight / 16)
		{
			int i = x / 64;
			int j = (y - screenHeight / 16) / 64;
			
			return new int[]{i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]};
		}
		return null;
	}
    
    public void mouseClicked(int b, int x, int y, int clickCount) 
    {
    	if (getBoxFromMouseCoords(x, y) != null)
    	{
    		selectedModuleCoords = getBoxFromMouseCoords(x, y);
    		selectedModule = world.getModuleAt(selectedModuleCoords[0], selectedModuleCoords[1]);
    	}
    	else
    	{
    		int i = 0;
    		for(Integer[] coord : selectedModuleUpgrades)
    		{
    			int boxX = coord[0];
    			int boxY = coord[1];
    			if (x > boxX && x < boxX + 64
    					&& y > boxY && y < boxY + 64)
    			{
    				int moduleID = selectedModule.getUpgrades().get(i);
    				try
					{
						IModule module = ModuleCreator.createModule(moduleID);
						int cost = selectedModule.getUpgradeCost(module);
						if (cost <= money)
						{
							world.setModule(module, selectedModuleCoords[0], selectedModuleCoords[1]);
							selectedModule = module;
							money -= cost;
							return;
						}
					} catch (SlickException e)
					{
						e.printStackTrace();
					}
    			}
    			++i;
    		}
    	}
    }
	
	@Override
    public void keyPressed(int key, char c) 
    {
		if (key == Input.KEY_LEFT)
			--cameraCoords[0];
		else if (key == Input.KEY_RIGHT)
			++cameraCoords[0];
		else if (key == Input.KEY_DOWN)
			++cameraCoords[1];
		else if (key == Input.KEY_UP)
			--cameraCoords[1];
    }

	@Override
	public int getID() 
	{
		return this.stateID;
	}
	
	private int fastfloor(double x) 
	{
		return x > 0 ? (int) x : (int) x - 1;
	}
}