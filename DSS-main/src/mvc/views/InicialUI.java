package mvc.views;

import mvc.views.GetButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class InicialUI extends JPanel implements GetButtons {
    private static JLabel labelimagem;
    private static JButton motorista;
    private static JButton gestor;
    private static JButton leitorqr;
    private static JButton robot;

    public InicialUI(){

        this.setName("Inicial");
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(140, 50, 200,100);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/logo.png"));
        this.add(labelimagem);

        motorista = new JButton("Motorista");
        motorista.setName("motorista");
        motorista.setBounds(15,175,110,25);
        this.add(motorista);

        gestor = new JButton("Gestor");
        gestor.setName("gestor");
        gestor.setBounds(130, 175, 110, 25);
        this.add(gestor);

        leitorqr = new JButton("Leitor QRC");
        leitorqr.setName("leitorqr");
        leitorqr.setBounds(245, 175, 110, 25);
        this.add(leitorqr);

        robot = new JButton("Robot");
        robot.setName("robot");
        robot.setBounds(360, 175, 110, 25);
        this.add(robot);
    }

    @Override
    public TreeMap<String,JButton> getButtons() {
        TreeMap<String,JButton> list = new TreeMap<String,JButton>();
        list.put("motorista",motorista);
        list.put("gesto",gestor);
        list.put("leitorqr",leitorqr);
        list.put("robot",robot);
        return list;
    }

    public LoginUI setGestor() {
        return (LoginUI) View.updatePanel(this,new LoginUI());
    }

    public JPanel setMotorista() {
        return View.updatePanel(this,new DescargaUI());
    }

    public JPanel setLeitorQr() {
        return View.updatePanel(this,new LeitorQRCodeUI());
    }

    public EscolherRobotUI setRobot() {
        return (EscolherRobotUI) View.updatePanel(this,new EscolherRobotUI());
    }

}
