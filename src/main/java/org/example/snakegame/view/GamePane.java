package org.example.snakegame.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.example.snakegame.data.GraphicData;
import org.example.snakegame.data.SizeData;

// The main play pane upon which snake will move and game will be played
public class GamePane extends AnchorPane implements SizeData {
    GraphicData graphicData = GraphicData.getInstance();
    private static GamePane instance;
    private GamePane(){
        setPrefSize(X_NUM * GRID_SIZE, Y_NUM *GRID_SIZE);
        graphicData.applyGameBackground(this);
    }

    // Applied singleton pattern, so only one object is formed
    public static GamePane getInstance(){
        if (instance == null)
            instance = new GamePane();
        return instance;
    }

}
