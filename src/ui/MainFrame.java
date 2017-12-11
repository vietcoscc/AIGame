package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    PanelManager panelManager;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        setTitle(title);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponent();
    }

    private void initComponent() {
        panelManager = new PanelManager();
        add(panelManager);
    }
}
