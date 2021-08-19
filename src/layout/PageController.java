package layout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class PageController {
	//메인창
	@FXML
	private AnchorPane root;
	@FXML
	private AnchorPane mainPane;
	@FXML
	AnchorPane newPane;	
	@FXML
	private GridPane grid;
	//엔딩창
	@FXML 
	private AnchorPane endPane;
	@FXML
	private Button btnLoad;
	@FXML
	Text endScore;
	//기타
	@FXML
	Button bt;
	@FXML
	Text txtName;
	@FXML
	Text txtCount;
	@FXML
	Text txtHp;

	public Button btn[][];
	public boolean check[][];
	public int lastCount;
	public int whack ,hp = 3,lvl = 1;
	
	public void Click(int r ,int c) {
		check[r][c] = false;
		btn[r][c].setStyle("-fx-background-color: white;");
	}
	
	public void Start(ActionEvent e) {
		newPane.setOpacity(1);
		hp = 3;
		whack = 0;
		txtName.setText("Whack A Mole");
		txtHp.setText("HP : "+Integer.toString(hp));
		txtCount.setText("SCORE : "+Integer.toString(whack));
		Button btn = (Button)e.getSource();
		if(btn.getText().equals("Easy")) {
			lvl = 1;
		}
		if(btn.getText().equals("Normal")) {
			lvl = 2;
		}
		if(btn.getText().equals("Hard")) {
			lvl = 3;
		}
		bt.setVisible(false);
		txtCount.setVisible(true);
		endPane.setVisible(false);
		GameLoad();
		gameStart();
	}
	
	private void gameStart() {
		Grid();
		Thread thread = new Thread(new Game(this));
		thread.start();
	}
	
	private void Grid() {
		GridPane gd;
		gd = grid;
		
		btn = new Button[3][3];
		check = new boolean[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y <3; y++) {
				check[x][y] = false;
				btn[x][y] = new Button();
				btn[x][y].setMaxSize(1000, 1000);
				btn[x][y].setStyle("-fx-padding: 10%");
				btn[x][y].setStyle("-fx-background-color: white;");
				final int r = x;
				final int c = y;
				btn[x][y].setOnMouseClicked(e -> {
					Click(r,c);
				});
				gd.add(btn[x][y], x, y);
			}
			gd.setVisible(true);
		}
	}
	
	public void EndSetting(ActionEvent e) {
		Button btn = (Button)e.getSource();
		if(btn.getText().equals("Return Menu")) {
			 MainLoad();
		}
		if(btn.getText().equals("Exit")) {
			Platform.exit();
			System.exit(0);
		}
	}
	
	public void MainLoad() {
		try {
			Parent main = FXMLLoader.load(
					Objects.requireNonNull(getClass().getResource("/layout/MainLayout.fxml")));
			Scene scene = new Scene(main);
			Stage stage = (Stage)btnLoad.getScene().getWindow();
			stage.setScene(scene);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("MAIN LOADE ERROR");
		}
	}

	
	public void GameLoad() {
		System.out.println("Level : " + lvl);
		System.out.println("LOAD GAME PAGE");
		try {
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(mainPane.opacityProperty(),0);
			KeyFrame keyFrame = new KeyFrame(
					Duration.millis(500),
					(ActionEvent event) -> {
						newPane.setVisible(true);
						mainPane.setVisible(false);
					},
					keyValue
				);		
				timeline.getKeyFrames().add(keyFrame);
				timeline.play();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("GRIDPANE LOAD ERROR");
		}
	}
	
	public void EndLoad() {
//		System.out.println("GAME OVER");
//		System.out.println("LOAD END PAGE");
		try {
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(endPane.opacityProperty(),1);
			KeyFrame keyFrame = new KeyFrame(
					Duration.millis(250),
					(ActionEvent event) -> {
						endPane.setVisible(true);
					},
					keyValue
				);		
				timeline.getKeyFrames().add(keyFrame);
				timeline.play();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("END PANE LOAD ERROR");
		}
	}
	
}
