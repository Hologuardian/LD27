package holo.essentrika.states;

import holo.essentrika.EssentrikaMain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class GameOverState extends BasicGameState
{
	public static final String saveFileLocation = "scoreSave";
	double totalTime;
	int totalMoney;
	int score;
	int highScore;
	int x;
	String scoreText;
	String highScoreText;

	public int[] titleCoords;
	public int[] menuCoords;
	
	public Image title;
	public Image menu;
	
	public float menuScale = 1.0F;
	
	private final int stateID;
	StateBasedGame game;
	
	public String[] text = new String[]
			{"You had one job...",
			"Now that you have failed the companies first task we have no choice but to file bankruptcy",
			"And just try this all again."};
	
	public GameOverState(int stateID, StateBasedGame game)
	{
		this.stateID = stateID;
		this.game = game;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game)throws SlickException 
	{
		totalTime = GameState.totalTime;
		totalMoney = GameState.totalMoney;
		score = (int)((totalTime)/1000) * totalMoney;
		scoreText = "Score: " + score;
		
		try
		{
			File file = new File(saveFileLocation);
			ArrayList<String> datums = new ArrayList<String>();
			
			if (file.exists())
			{
				Scanner sc = new Scanner(file);
				while(sc.hasNext())
				{
					String data = sc.nextLine();
					datums.add(data);
				}
				sc.close();
			}
			
			file = new File(saveFileLocation);
			file.createNewFile();

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			datums.add(String.valueOf(score));
			
			for(String data : datums)
			{
				out.println(data);
				if(Integer.valueOf(data) > highScore)
					highScore = Integer.valueOf(data);
			}

			highScoreText = "High Score: " + highScore;
			
			out.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)throws SlickException 
	{
		title = new Image("res/GameOver.png");
		menu = new Image("res/Return.png");

		x = gc.getWidth() / 2;
		
		titleCoords = new int[]{x - title.getWidth() / 2, 0};
		menuCoords = new int[]{x - menu.getWidth() / 2, 0};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)throws SlickException 
	{
		g.scale(EssentrikaMain.defaultWidth / gc.getWidth(), EssentrikaMain.defaultHeight / gc.getHeight());
		
		title.draw(titleCoords[0], titleCoords[1]);
		
		Font font = gc.getDefaultFont();
		int y = (int) (titleCoords[1] + title.getHeight());
		
		for(String line : text)
		{
			if(line.charAt(0) == '&')
			{
				y += font.getLineHeight();
				line = line.substring(1);
			}
			FontUtils.drawCenter(font, line, x - font.getWidth(line) / 2, y, font.getWidth(line));
			y += font.getLineHeight();
		}
		y += font.getLineHeight();
		FontUtils.drawCenter(font, scoreText, x - font.getWidth(scoreText) / 2, y, font.getWidth(scoreText));
		y += font.getLineHeight();
		FontUtils.drawCenter(font, highScoreText, x - font.getWidth(highScoreText) / 2, y, font.getWidth(highScoreText));
		
		menuCoords[1] = y;

		menu.draw(menuCoords[0], menuCoords[1], menuScale);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)throws SlickException 
	{
		int xPos = gc.getInput().getMouseX();
		int yPos = gc.getInput().getMouseY();
		
		if (xPos > menuCoords[0] && xPos < menuCoords[0] + menu.getWidth()
				&& yPos > menuCoords[1] && yPos < menuCoords[1] + menu.getHeight())
		{
			menuScale = 1.05F;
			menuCoords[0] = x - (int)(menu.getWidth() * 1.05F) / 2;
		}
		else
		{
			menuScale = 1.0F;
			menuCoords[0] = x - menu.getWidth() / 2;
		}
	}
	
	@Override
    public void keyPressed(int key, char c) 
    {
		if (key == Input.KEY_ESCAPE)
		{
			game.enterState(EssentrikaMain.MENUSTATEID);
		}
    }
	
	@Override
    public void mouseClicked(int b, int x, int y, int clickCount) 
    {
		if (x > menuCoords[0] && x < menuCoords[0] + menu.getWidth()
				&& y > menuCoords[1] && y < menuCoords[1] + menu.getHeight())
		{
			game.enterState(EssentrikaMain.MENUSTATEID);
		}
    }
	
	@Override
	public int getID() 
	{
		return this.stateID;
	}

}
