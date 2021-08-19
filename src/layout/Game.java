package layout;

import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

public class Game implements Runnable {
	private PageController page;
	
	public Game(PageController page) {
		this.page = page;
	}
	
	public void run() {
		System.out.println("GAME START");
//		playSound(false);
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		level();
	}
  
	public void level() {
		int x,y = 0;
		while (true) {
			x = (int)(Math.random() * 3);
			y = (int)(Math.random() * 3);
			page.check[x][y] = true;
			if (page.lvl == 3) {
				page.btn[x][y].setStyle("-fx-background-color: red;");
				time(450);
			} else if (page.lvl == 2) {
				page.btn[x][y].setStyle("-fx-background-color: blue;");
				time(650);
			} else {
				page.btn[x][y].setStyle("-fx-background-color: green;");
				time(1000);
			} 
			Checked(x, y);
		} 
	}
  
	public void hpUp() {
		if (page.lvl == 3) {
			if (page.whack % 10 == 0) {
				page.hp++;
				page.txtHp.setText("HP : " + Integer.toString(page.hp));
			} 
		} else if (page.lvl == 2) {
			if (page.whack % 30 == 0) {
				page.hp++;
				page.txtHp.setText("HP : " + Integer.toString(page.hp));
			} 
		} else if (page.whack % 40 == 0) {
			page.hp++;
			page.txtHp.setText("HP : " + Integer.toString(page.hp));
		} 
	}
  
	public void time(int speed) {
		try {
			if (page.lvl == 3) {
				Thread.sleep(speed);
			} else if (page.lvl == 2) {
				Thread.sleep(speed);
			} else {
				Thread.sleep(speed);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
  
	public void playSound(boolean p) {
		String play = "src/res/main.mp3";
		String dead = "src/res/Die.mp3";
		Media m = new Media(Paths.get(play).toUri().toString());
		Media m2 = new Media(Paths.get(dead).toUri().toString());
		
//		오디오가 깨지는문제로 사용X
//		MediaPlayer mp = new MediaPlayer(m);
		AudioClip player = new AudioClip(m.getSource());
    	AudioClip player2 = new AudioClip(m2.getSource());
    	player.setVolume(0.1);
    	player2.setVolume(1.0);
    	if (!p) {
    		player2.stop();
//    		mp.setAutoPlay(true);
    		player.play();
    	} else {
    		player.stop();
    		player2.play();
    	} 
	}
  
	public void Missed(int x, int y) {
		page.hp--;
		if (page.lvl == 3) {
			page.btn[x][y].setStyle("-fx-background-color: blue;");
			time(100);
		} else if (page.lvl == 2) {
			page.btn[x][y].setStyle("-fx-background-color: red;");
			time(100);
		} else {
			page.btn[x][y].setStyle("-fx-background-color: red;");
			time(100);
		} 
		page.txtHp.setText("HP : " + Integer.toString(page.hp));
		page.btn[x][y].setStyle("-fx-background-color: white;");
		if (page.hp <= 0) {
			Die(); 
		}
		
	}
  
	public void Whack(int x, int y) {
		page.whack++;
		page.txtCount.setText("SCORE : " + Integer.toString(page.whack));
		hpUp();
	}
	private void Checked(int x, int y) {
		if (!page.check[x][y]) {
			Whack(x, y);
	    } else {
	    	Missed(x, y);
	    } 
	}
  
	@SuppressWarnings("deprecation")
	public void Die() {
	    Platform.runLater(() -> {
//	          playSound(true);
	          page.EndLoad();
	          page.txtName.setText("Game Over");
	          page.endScore.setText("SCORE : " + Integer.toString(page.whack));
	          page.newPane.setOpacity(0.45);
	        });
	    Thread.currentThread().stop();
	  }
}
