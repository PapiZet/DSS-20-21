package mvc.views;

import Sistema.CamadaDeDados.Duplo;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


public class ListagemLocalizacoesUI extends JPanel implements GetButtons {

    private static JLabel labelimagem;
    private static JLabel label;
    private static JButton voltar;
    private static JButton detalhes;
    private static JScrollPane scroll;
    private static DefaultListModel listPedidos;
    private static DefaultListModel listInfo;
    private static JList<String> pedidos;
    private static String infopedido;
    private static String nomepedido;
    private static JButton changeLayout;
    private Boolean layout;
    private Integer gestorId;
    private String gestor;

    ListagemLocalizacoesUI() {

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(30, 10, 120,200);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/listagem.jpg"));
        this.add(labelimagem);

        label = new JLabel("Panorama atual do sistema:");
        label.setBounds(220, 15, 250, 25);
        this.add(label);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 110, 25);
        this.add(voltar);

        changeLayout = new JButton("Alternar");
        changeLayout.setName("alternar");
        changeLayout.setBounds(313, 220, 110, 25);
        this.add(changeLayout);

        detalhes = new JButton("Detalhes");
        detalhes.setName("detalhes");
        detalhes.setBounds(175, 220, 110, 25);
        this.add(detalhes);

        listPedidos = new DefaultListModel();
//        listPedidos.addElement("Pedido Sistema.CamadaDeDados.Descarga A");


        listInfo = new DefaultListModel();
//        listInfo.addElement("Pedido que contém A");

        pedidos = new JList(listPedidos);
        pedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll = new JScrollPane(pedidos);
        scroll.setBounds(175,55,250,150);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportBorder(new LineBorder(Color.BLACK));
        this.add(scroll);
    }

    @Override
    public TreeMap<String, JButton> getButtons() {
        TreeMap<String, JButton> res = new TreeMap<String, JButton>();
        res.put("voltar",voltar);
        res.put("detalhes",detalhes);
        res.put("changelayout",changeLayout);
        return res;
    }

    public JPanel setVoltar(){
        return View.updatePanel(this,new GestorLogadoUI());
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getGestor() {
        return this.gestor;
    }

    public void setGestorId(Integer gestorId) {
        this.gestorId = gestorId;
    }

    public Integer getGestorId() {return this.gestorId;}

    private void setListPedidos(Collection<String> c) {
        listPedidos.addAll(c);
    }

    private void setListInfo(Collection<String> c) {
        listInfo.addAll(c);
    }

    public void setList(TreeMap<Integer,Integer> l, TreeMap<Integer, Integer> m) {

        layout = true;

        listPedidos.removeAllElements();
        listInfo.removeAllElements();

        TreeMap<Integer,String> tt = new TreeMap<Integer,String>();

        for(Map.Entry<Integer,Integer> d : l.entrySet()){
            tt.put(d.getKey(),"prateleira: " + d.getValue());
        }

        for(Map.Entry<Integer,Integer> d : m.entrySet()){
            tt.put(d.getKey(),"robot: " + d.getValue());
        }

        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> b = new ArrayList<String>();

        for (Map.Entry<Integer,String> e: tt.entrySet()) {
            a.add("Palete " + e.getKey());
            b.add(e.getValue());
        }

        setListPedidos(a);
        setListInfo(b);

        Boolean applied = true;
        if(l.size() == 0 && m.size() == 0) {
            applied = false;
        }else {
            pedidos.setSelectedIndex(0);
        }
        detalhes.setEnabled(applied);
        changeLayout.setEnabled(applied);
    }

    public void setListLayout(TreeMap<Integer,ArrayList<Integer>> l, TreeMap<Integer, ArrayList<Integer>> m) {

        layout = false;

        Integer max= 50;
        listPedidos.removeAllElements();
        listInfo.removeAllElements();

        TreeMap<String,String> tt = new TreeMap<String,String>();

        for(Map.Entry<Integer,ArrayList<Integer>> d : l.entrySet()){
            String paletes = "Paletes: ";
            Integer i = max - paletes.length();
            for (Integer p: d.getValue()) {
                String buff = p.toString() + ", ";
                Integer length = buff.length();
                if(i > length) i -= length;
                else {
                    paletes = paletes + "\n";
                    i = max;
                }
                paletes = paletes + buff;
            }
            paletes = paletes.substring(0,paletes.length()-2);
            tt.put("Prateleira " + d.getKey(),paletes);
        }

        for(Map.Entry<Integer,ArrayList<Integer>> d : m.entrySet()){

            String paletes = "Paletes: ";
            Integer i = max - paletes.length();
            for (Integer p: d.getValue()) {
                String buff = p.toString() + ", ";
                Integer length = buff.length();
                if(i > length) i -= length;
                else {
                    paletes = paletes + "\n";
                    i = max;
                }
                paletes = paletes + buff;
            }
            paletes = paletes.substring(0,paletes.length()-2);
            tt.put("Robot " + d.getKey(),paletes);
        }

        setListPedidos(tt.keySet());
        setListInfo(tt.values());

        Boolean applied = true;
        if(l.size() == 0 && m.size() == 0) {
            applied = false;
        }else {
            pedidos.setSelectedIndex(0);
        }
        detalhes.setEnabled(applied);
        changeLayout.setEnabled(applied);
    }

    public void detalhes() {
        int index = pedidos.getSelectedIndex();
        infopedido = (String) listInfo.getElementAt(index);
        nomepedido = (String) listPedidos.getElementAt(index);
        JOptionPane.showMessageDialog(this,infopedido,nomepedido.toString(),JOptionPane.INFORMATION_MESSAGE);
    }

    public Boolean getLayoutBool(){
        return layout;
    }

}