package ui;

import javax.swing.*;
import java.awt.*;

public class PanelManager extends JPanel {
    public static final String HOME_PANEL = "HomePanel";
    public static final String GAME_PANEL = "GamePanel";
    private HomePanel homePanel;
    private GamePanel gamePanel;
    private CardLayout cardLayout = new CardLayout();

    public PanelManager() {
        homePanel = new HomePanel(this,cardLayout);
        gamePanel = new GamePanel(this,cardLayout);
        setLayout(cardLayout);
        add(homePanel, HOME_PANEL);
        add(gamePanel, GAME_PANEL);
        cardLayout.show(this, HOME_PANEL);
        initComponent();
    }

    private void initComponent() {

    }
}
