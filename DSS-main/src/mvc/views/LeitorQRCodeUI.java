package mvc.views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.TreeMap;


public class LeitorQRCodeUI extends JPanel implements GetButtons {

    private static JLabel label;
    private static JLabel labelimagem;
    private static JButton voltar;
    private static JButton add;
    private static JButton selecionar;
    private static DefaultListModel listLeitores;
    private static JList<Integer> leitoresqrc;
    private static JScrollPane scroll;
    private Integer leitorId;

    LeitorQRCodeUI() {

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(30, 50, 111,125);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/leitor.jpg"));
        this.add(labelimagem);

        label = new JLabel("Leitores de Qr-Codes disponíveis:");
        label.setBounds(190, 15, 250, 25);
        this.add(label);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 110, 25);
        this.add(voltar);

        add = new JButton("Add");
        add.setName("add");
        add.setBounds(15, 182, 110, 25);
        this.add(add);

        selecionar = new JButton("Selecionar");
        selecionar.setName("selecionar");
        selecionar.setBounds(235, 170, 110, 25);
        this.add(selecionar);

        listLeitores = new DefaultListModel();

        leitoresqrc = new JList(listLeitores);
        leitoresqrc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll = new JScrollPane(leitoresqrc);
        scroll.setBounds(170,55,250,100);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportBorder(new LineBorder(Color.BLACK));
        this.add(scroll);

    }

    public TreeMap<String, JButton> getButtons() {
        TreeMap<String,JButton> list = new TreeMap<String,JButton>();
        list.put("voltar",voltar);
        list.put("selecionar",selecionar);
        list.put("add",add);
        return list;
    }

    public void setListQRCLeitores(Collection<Integer> leitoresqrcode) {
        listLeitores.clear();
        listLeitores.addAll(leitoresqrcode);
        if (leitoresqrcode.size() == 0){
            selecionar.setEnabled(false);
            add.setEnabled(true);
        } else {
            selecionar.setEnabled(true);
            add.setEnabled(false);
        }
        leitoresqrc.setSelectedIndex(0);
    }

    public JPanel setSelecionar() {
        return View.updatePanel(this,new InserirQRCodeUI());
    }

    public JPanel setVoltar() {
        return View.updatePanel(this,new InicialUI());
    }

    public void setLeitorId(Integer leitorId) {
        this.leitorId = leitorId;
    }

    public Integer getSelectedValue() {
        return (Integer) leitoresqrc.getSelectedValue();
    }

    public void noPaletes() {
        JOptionPane.showMessageDialog(this,"Não há paletes em espera!","Paletes disponíveis",JOptionPane.INFORMATION_MESSAGE);
    }

}