package flappybird;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import java.sql.ResultSet;
public class FlappyBird implements ActionListener, KeyListener
{
    
    public static final int FPS = 60, WIDTH = 640, HEIGHT = 480;
    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer t;
    private int currentScreenIndex = 0;
    public int score = 0;
    private boolean paused;
    private AudioManager audioManager;
    private boolean isPlayingGameMusic;
    private BufferedImage candyImage;
    private BufferedImage attackImage;
    private boolean isMuted = false;
    private BufferedImage bossImage; 

    private ArrayList<Candy> candies;
    private static final int CANDY_SPAWN_INTERVAL = 1000;
    private Timer candySpawnTimer;
    
    private boolean select1;
    private boolean select2;
    
    private boolean showingDifficultyScreen;
    private boolean startthegame;
    private boolean showingInstructions1;
    private boolean showingInstructions2;
    private boolean showingInstructions3;
    private boolean showingResult1;
    private boolean showingResult2;
    private boolean showingResult3;
    private boolean leaderboard = false;
    private ArrayList<ScoreEntry> leaderboardScores;
    private ArrayList<Attack> attacks;
    private ArrayList<BossImage> bossImages;
     
     public FlappyBird()
     {
        candies= new ArrayList<>();
        audioManager= new AudioManager();
        isPlayingGameMusic= false;
        try{
        candyImage = ImageIO.read(new File("candy.png"));
        }catch (IOException e){
        e.printStackTrace();
    }
        candySpawnTimer= new Timer(CANDY_SPAWN_INTERVAL, e -> spawnCandy());
        candySpawnTimer.start();
        leaderboardScores= new ArrayList<>();
        attacks = new ArrayList<>();
        try{
        attackImage = ImageIO.read(new File("attack.png"));
        }catch (IOException e){
        e.printStackTrace();
    }
        try{
        bossImage= ImageIO.read(new File("boss.png"));
    }catch (IOException e){
        e.printStackTrace();
        }
        bossImages = new ArrayList<>();
    }
    public void go()
    {
        frame= new JFrame("Flappy Bird");
        bird= new Bird();
        rects= new ArrayList<Rectangle>();
        panel= new GamePanel(this, bird, rects);
        frame.add(panel);
        
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
        paused = true;
        
        audioManager.playMusic("lobby.wav", true);
        t= new Timer(1000/FPS, this);
        t.start();
        
    }
    
    public static void main(String[] args) 
    {
        new FlappyBird().go();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        panel.repaint();
        if (!paused && !isPlayingGameMusic)
        {
            audioManager.playMusic("theme.wav", true);
            isPlayingGameMusic= true;
        }
        if (paused && isPlayingGameMusic)
        {
            audioManager.playMusic("lobby.wav", true);
            isPlayingGameMusic= false;
        }
        if(select1 && (!showingResult1 && !showingResult2 && !showingResult3)){ //easy mode
            bird.physics();
            if(scroll % 90 == 0)
            {
                Rectangle r= new Rectangle(WIDTH, 0, GamePanel.PIPE_W , (int) ((Math.random()*HEIGHT)/6f + (0.2f)*HEIGHT));
                int h2= (int) ((Math.random()*HEIGHT)/6f + (0.2f)*HEIGHT);
                Rectangle r2= new Rectangle(WIDTH, HEIGHT- h2, GamePanel.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }     
            ArrayList<Rectangle> toRemove= new ArrayList<Rectangle>();
            boolean game = true;
            for(Rectangle r : rects)
            {
                r.x-=3;
                if(r.x + r.width <= 0)
                {
                    toRemove.add(r);
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 																																																																								
                if(r.intersects(Math.round(bird.x- (bird.getWidth() / 2)), Math.round(bird.y - (bird.getHeight() / 2)), bird.getWidth(), bird.getHeight())) {                	bird.playHitSound();
                    bird.playHitSound();
                    showingResult1= true;
                    showingResult2= false;
                    showingResult3= false;
                    game = false;
                }
            }
             if(scroll% 90== 0)
             {
        int randomY = (int)(Math.random()*(HEIGHT - Candy.HEIGHT));
        candies.add(new Candy(WIDTH, randomY, candyImage));
    }
            ArrayList<Candy> toRemoveCandies = new ArrayList<Candy>();
            for(int i= 0; i< candies.size(); i++) {
                 Candy candy= candies.get(i);
                candy.x-= 13;
        if(candy.getBounds().intersects(Math.round(bird.x- (bird.getWidth() / 2)), Math.round(bird.y - (bird.getHeight() / 2)), bird.getWidth(), bird.getHeight()))
        {
            bird.playHitSound();
            time+= 100;
            toRemoveCandies.add(candy);
        }
        if (candy.x + Candy.WIDTH < 0)
        {
            toRemoveCandies.add(candy); 
        }
    }
            rects.removeAll(toRemove);
            candies.removeAll(toRemoveCandies);
            time++;
            scroll++;
            if(bird.y > HEIGHT)
            {
            	bird.playHitSound();
                showingResult1= false;
                showingResult2= true;
                showingResult3= false;
                game = false;
               
            }
            if(bird.y + bird.RAD < 0)
            {
            	bird.playHitSound();
                showingResult1= false;
                showingResult2= false;
                showingResult3= true;
                game = false;
            }
            if(!game)
            {
            	paused = false;
                rects.clear();
                bird.reset();
                scroll = 0;
            }     	
        }
        if(select2 &&(!showingResult1 &&!showingResult2 &&!showingResult3)){ // little hard mode
            bird.physics();
            if(scroll% 30== 0) 
            {
                Rectangle r= new Rectangle(WIDTH, 0, GamePanel.PIPE_W , (int) ((Math.random()*HEIGHT)/4.5f + (0.17f)*HEIGHT));
                int h2= (int) ((Math.random()*HEIGHT)/4.5f + (0.17f)*HEIGHT);
                Rectangle r2= new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }     
            ArrayList<Rectangle> toRemove= new ArrayList<Rectangle>();
            boolean game= true;
            for(Rectangle r: rects) 
            {
                r.x-=13;
                if(r.x + r.width <= 0) 
                {
                    toRemove.add(r);
                }
                if(r.intersects(Math.round(bird.x - (bird.getWidth() / 2)), Math.round(bird.y - (bird.getHeight() / 2)), bird.getWidth(), bird.getHeight())) 
                {                
                    bird.playHitSound();
                    showingResult1= true;
                    showingResult2= false;
                    showingResult3= false;
                    game = false;                   
                }
            }
            if(scroll % 90 == 0)
            {
        int randomY = (int)(Math.random()*(HEIGHT - Candy.HEIGHT));
        candies.add(new Candy(WIDTH, randomY, candyImage));
    }
            ArrayList<Candy> toRemoveCandies = new ArrayList<Candy>();
            for(int i=0; i< candies.size(); i++) {
                 Candy candy=candies.get(i);
                candy.x-=13;
        if(candy.getBounds().intersects(Math.round(bird.x - (bird.getWidth() / 2)), Math.round(bird.y - (bird.getHeight() / 2)), bird.getWidth(), bird.getHeight())) 
        {
            bird.playEatSound();
            time+= 100;
            toRemoveCandies.add(candy);
        }

        if(candy.x+ Candy.WIDTH < 0) 
        {
            toRemoveCandies.add(candy); 
        }
    }
            if(scroll % 250 == 0)
            {
        attacks.add(new Attack(WIDTH, 340, attackImage));
        attacks.add(new Attack(WIDTH, 20, attackImage));
        bossImages.add(new BossImage(350, 73 , bossImage, 2000));
        bird.playBossSound();
    }
            ArrayList<Attack> toRemoveAttacks = new ArrayList<Attack>();
            for(int i=0; i< attacks.size(); i++) {
                 Attack attack=attacks.get(i);
                attack.x-=9;
        if(attack.getBounds().intersects(Math.round(bird.x - (bird.getWidth() / 2)), Math.round(bird.y - (bird.getHeight() / 2)), bird.getWidth(), bird.getHeight())) 
        {
                    bird.playHitSound();
                    showingResult1= true;
                    showingResult2= false;
                    showingResult3= false;
                    game = false;  
                 toRemoveAttacks.add(attack);
        }
    }
           
            rects.removeAll(toRemove);
            candies.removeAll(toRemoveCandies);
            attacks.removeAll(toRemoveAttacks);
            time++;
            scroll++;

            if(bird.y > HEIGHT)
            {
            	bird.playHitSound();
                showingResult1= false;
                showingResult2= true;
                showingResult3= false;
                game= false;
               
            }
            if(bird.y + bird.RAD < 0) 
            {
            	bird.playHitSound();
                showingResult1= false;
                showingResult2= false;
                showingResult3= true;
                game= false; 
            }
            if(!game)
            {
            	paused = false;
                rects.clear();
                bird.reset();
                scroll = 0;   
                showInputDialog();
            }     
        }
    }
    public int getScore()
    {
        return time;
    }
       
    public void keyPressed(KeyEvent e)
    {
    	if(e.getKeyCode() == KeyEvent.VK_UP && select1 && (!showingResult1 && !showingResult2 && !showingResult3) )
        {
    		bird.jump();
    	}else if (e.getKeyCode() == KeyEvent.VK_UP && select2 && (!showingResult1 && !showingResult2 && !showingResult3) ) {
    		bird.jump();
    	} 
        if(e.getKeyCode() == KeyEvent.VK_X && paused)
        {
        leaderboard = !leaderboard;
        if(leaderboard)
        {
                showLeaderboard();
            }
        }
       if(e.getKeyCode() == KeyEvent.VK_S && paused && leaderboard)
       {
        showSearchDialog();
    }
    	if(e.getKeyCode() == KeyEvent.VK_SPACE )
        {
    	showingDifficultyScreen= true;
    	showingInstructions1= false;
    	showingInstructions2= false;
    	showingInstructions3= false;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_A&& showingDifficultyScreen )
        {
        showingDifficultyScreen = false;
        time=0;
        paused= false;
        select1= true;
        select2= false;
    	}
        if (e.getKeyCode() == KeyEvent.VK_B&& showingDifficultyScreen) 
        {
        showingDifficultyScreen= false;
        time=0;
        paused= false;
        select1= false;
        select2= true;
        
        }
    	if (e.getKeyCode() == KeyEvent.VK_SPACE && (showingResult1 || showingResult2 || showingResult3) )
        {
        paused= true;
        showingDifficultyScreen= false;
		showingInstructions1= false;
	    showingInstructions2= false;
	    showingInstructions3= false;
	    showingResult1= false;
        showingResult2= false;
        showingResult3= false;
        select1= false;
        select2= false;
        }
    	if (e.getKeyCode() == KeyEvent.VK_Z && paused && !showingInstructions3 && !showingInstructions2 && !showingInstructions1 && !showingDifficultyScreen)
        {
        showingInstructions1= true;
        } else if (e.getKeyCode() == KeyEvent.VK_Z && paused && showingInstructions1) {
        showingInstructions1= false;
        showingInstructions2= true;
        } else if (e.getKeyCode() == KeyEvent.VK_Z && paused && showingInstructions2) {
        showingInstructions2= false;
        showingInstructions3= true;
        } else if (e.getKeyCode() == KeyEvent.VK_Z && paused && showingInstructions3) {
        showingInstructions3= false;
        showingInstructions1= true;
        }
    }
   public void keyReleased(KeyEvent e)
   {

   }
   public void keyTyped(KeyEvent e) 
   {

   }
   public boolean showingResult1() 
   {
	   return showingResult1;
   }
   public boolean showingResult2() 
   {
	   return showingResult2;
   }
   public boolean showingResult3() 
   {
	   return showingResult3;
   }
   public boolean showingInstructions1() 
   {
	   return showingInstructions1;
   }
   public boolean showingInstructions2() 
   {
	   return showingInstructions2;
   }
   public boolean showingInstructions3() 
   {
	   return showingInstructions3;
   }

   public int getCurrentScreenIndex() 
   {
	   	return currentScreenIndex;
   }
   public boolean paused() 
   {
	   	return paused;
   }
   public boolean select1() 
   {
	   	return select1;
   }
   public boolean select2() 
   {
	   	return select2;
   }

   public boolean startthegame() 
   {
	   return startthegame;
   }
   public boolean showingDifficultyScreen() 
   {
   return showingDifficultyScreen;
   }
   private void spawnCandy() 
   {
        int randomY= (int) (Math.random() * (HEIGHT - Candy.HEIGHT));
        candies.add(new Candy(WIDTH, randomY, candyImage)); 
    }
   public ArrayList<Candy> getCandies() 
   {
    return candies;
    }

    public boolean leaderboard()
    {
        return leaderboard;
    }
     private void showInputDialog()
     {
        String playerName= JOptionPane.showInputDialog(frame, "enter your name to save score:");
        if (playerName!= null && !playerName.trim().isEmpty()) {
            saveScore(playerName, time);
        }
    }
    private void saveScore(String playerName, int score)
    {
    String DB_URL = "jdbc:mysql://localhost:3306/leaderboard_db";
    String DB_USER = "root";
    String DB_PASSWORD = "minhcuong04";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try{
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String query = "INSERT INTO leaderboard (player_name, score) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, playerName);
        preparedStatement.setInt(2, score);
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Score saved successfully.");
        }
    }catch (SQLException e) {
        System.out.println("Error saving score: " + e.getMessage());
    }finally{
        try{
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }catch (SQLException e){
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    }
    public ArrayList<ScoreEntry> getLeaderboardScores()
    {
        return leaderboardScores;
    }
    private void showSearchDialog() 
    {
    String playerName = JOptionPane.showInputDialog(frame, "Enter your name to find in leaderboard:");
    if(playerName != null && !playerName.trim().isEmpty())
    {
        searchPlayer(playerName);
        }
    }
    private void fetchLeaderboardScores()
    {
    String DB_URL= "jdbc:mysql://localhost:3306/leaderboard_db";
    String DB_USER= "root";
    String DB_PASSWORD= "minhcuong04";
    try(Connection connection= DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
        String query= "SELECT player_name, score FROM leaderboard ORDER BY score DESC LIMIT 36";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){
            leaderboardScores.clear();
            while (resultSet.next()){
                String playerName= resultSet.getString("player_name");
                int score= resultSet.getInt("score");
                leaderboardScores.add(new ScoreEntry(playerName, score));
                }
            }
        }catch (SQLException e){
        System.out.println("Error fetching leaderboard scores: "+e.getMessage());
        }
    }
    private void showLeaderboard()
    {
        fetchLeaderboardScores();
        panel.repaint();
    }
    private void searchPlayer(String playerName)
    {
    boolean found= false;
    for(ScoreEntry entry : leaderboardScores)
    {
        if(entry.playerName.equalsIgnoreCase(playerName))
        {
            found= true;
            JOptionPane.showMessageDialog(frame, "Player found: " + entry.playerName + " with score: " + entry.score, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            break;
            }
        }
    if(!found)
    {
        JOptionPane.showMessageDialog(frame, "Player not found in leaderboard.", "Search Result", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void sortleaderboardbyname()
    {
    int n = leaderboardScores.size();
    for(int i = 0; i < n - 1; i++)
    {
        for(int j = 0; j < n - 1 - i; j++)
        {
            if(leaderboardScores.get(j).playerName.compareToIgnoreCase(leaderboardScores.get(j + 1).playerName) > 0) {               
                ScoreEntry temp = leaderboardScores.get(j);
                leaderboardScores.set(j, leaderboardScores.get(j + 1));
                leaderboardScores.set(j + 1, temp);
                }
            }
        }
    }
    public ArrayList<Attack> getAttacks() 
   {
    return attacks;
    }
    public ArrayList<BossImage> getBossImages() {
        return bossImages;
    }
}