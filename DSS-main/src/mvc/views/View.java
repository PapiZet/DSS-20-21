package mvc.views;

import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

public class View extends JFrame {

    public View() {
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void setPanel(JPanel panel){
        this.getContentPane().removeAll();
        this.add(panel);
    }

    public void update() {
        setVisible(true);
    }

    public static JPanel updatePanel(JPanel panel, JPanel newPanel) {
        Component parent = panel.getParent();
        while(!(parent instanceof View)) {parent = parent.getParent();}
        ((View) parent).getContentPane().removeAll();
        ((View) parent).add(newPanel);
        return newPanel;
    }

    public EscolherRobotUI setEscolherRobot() {
        this.getContentPane().removeAll();
        EscolherRobotUI res = new EscolherRobotUI();
        this.add(res);
        return res;
    }

    public InicialUI setInicial() {
        this.getContentPane().removeAll();
        InicialUI res = new InicialUI();
        this.add(res);
        return res;
    }

}