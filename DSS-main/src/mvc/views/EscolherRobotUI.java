package mvc.views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class EscolherRobotUI extends JPanel implements GetButtons {

    private static JLabel labelimagem;
    private static JLabel label;
    private static JLabel labelrecolha;
    private static JLabel labelentrega;
    private static JButton voltar;
    private static JButton notificarE;
    private static JButton notificarR;
    private static DefaultListModel listRobots;
    private static DefaultListModel listRobotsE;
    private static DefaultListModel listRobotsR;
    private static JList<Integer> robots;
    private static JList<Integer> robotsE;
    private static JList<Integer> robotsR;
    private static JScrollPane scroll;
    private static JScrollPane scrollrecolha;
    private static JScrollPane scrollentrega;

    EscolherRobotUI() {

        this.setName("EscolherRobot");

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        this.setLayout(null);

        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(20, 19, 91,200);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/robot.png"));
        this.add(labelimagem);

        label = new JLabel("Robots livres:");
        label.setBounds(140, 5, 110, 25);
        this.add(label);

        labelrecolha = new JLabel("Robots a recolher:");
        labelrecolha.setBounds(248, 5, 110, 25);
        this.add(labelrecolha);

        labelentrega = new JLabel("Robots a entregar:");
        labelentrega.setBounds(365, 5, 110, 25);
        this.add(labelentrega);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 100, 25);

        this.add(voltar);

        notificarR = new JButton("Notificar");
        notificarR.setName("notificarr");
        notificarR.setBounds(250, 220, 100, 25);

        this.add(notificarR);

        notificarE = new JButton("Notificar");
        notificarE.setName("notificare");
        notificarE.setBounds(370, 220, 100, 25);

        this.add(notificarE);

        listRobots = new DefaultListModel();

        listRobotsR = new DefaultListModel();

        listRobotsE = new DefaultListModel();

        robots = new JList(listRobots);

        robotsR = new JList(listRobotsR);
        robotsR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (robotsR.getModel().getSize() == 0){
            notificarR.setEnabled(false);
        }

        robotsE = new JList(listRobotsE);
        robotsE.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (robotsE.getModel().getSize() == 0){
            notificarE.setEnabled(false);
        }

        scroll = new JScrollPane(robots);
        scroll.setBounds(130, 30, 100, 185);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportBorder(new LineBorder(Color.BLACK));
        this.add(scroll);

        scrollrecolha = new JScrollPane(robotsR);
        scrollrecolha.setBounds(250, 30, 100, 185);
        scrollrecolha.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollrecolha.setViewportBorder(new LineBorder(Color.BLACK));
        this.add(scrollrecolha);

        scrollentrega = new JScrollPane(robotsE);
        scrollentrega.setBounds(370, 30, 100, 185);
        scrollentrega.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollentrega.setViewportBorder(new LineBorder(Color.BLACK));
        this.add(scrollentrega);


    }

    public Integer getSelectedR(){
        return robotsR.getSelectedValue();
    }

    public Integer getSelectedE(){
        return robotsE.getSelectedValue();
    }

    public void setListRobotsR(Collection<Integer> robots) {
        listRobotsR.clear();
        listRobotsR.addAll(robots);
        if (robots.size() == 0){
            notificarR.setEnabled(false);
        } else {
            notificarR.setEnabled(true);
        }
        robotsR.setSelectedIndex(0);
    }

    public void setListRobotsE(Collection<Integer> robots) {
        listRobotsE.clear();
        listRobotsE.addAll(robots);
        if (robots.size() == 0){
            notificarE.setEnabled(false);
        } else {
            notificarE.setEnabled(true);
        }
        robotsE.setSelectedIndex(0);
    }

    public void setListRobots(Collection<Integer> robots) {
        listRobots.clear();
        listRobots.addAll(robots);
    }

    public JPanel setNotificar() {
        return View.updatePanel(this,new EscolherRobotUI());
    }

    public JPanel setVoltar() {
        return View.updatePanel(this,new InicialUI());
    }

    @Override
    public TreeMap<String, JButton> getButtons() {
        TreeMap<String,JButton> list = new TreeMap<String,JButton>();
        list.put("voltar",voltar);
        list.put("notificarr",notificarR);
        list.put("notificare",notificarE);
        return list;
    }
}
