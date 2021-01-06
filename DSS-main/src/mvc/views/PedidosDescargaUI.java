package mvc.views;

import Sistema.CamadaDeDados.Duplo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


public class PedidosDescargaUI extends JPanel implements GetButtons {

    private static JLabel label;
    private static JButton voltar;
    private static JButton detalhes;
    private static JButton aceitar;
    private static JButton rejeitar;
    private static JScrollPane scroll;
    private static DefaultListModel listPedidos;
    private static DefaultListModel listInfo;
    private static JList<String> pedidos;
    private static String infopedido;
    private static Integer nomepedido;
    private Integer gestorId;
    private String gestor;


    PedidosDescargaUI() {

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.setLayout(null);

        label = new JLabel("Pedidos de descarga pendentes:");
        label.setBounds(144, 15, 250, 25);
        this.add(label);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 110, 25);
        this.add(voltar);

        detalhes = new JButton("Detalhes");
        detalhes.setName("detalhes");
        detalhes.setBounds(130, 220, 110, 25);
        this.add(detalhes);

        aceitar = new JButton("Aceitar");
        aceitar.setName("aceitar");
        aceitar.setActionCommand("Aceitar");
        aceitar.setBounds(245, 220, 110, 25);
        this.add(aceitar);

        rejeitar = new JButton("Rejeitar");
        rejeitar.setName("rejeitar");
        rejeitar.setActionCommand("Rejeitar");
        rejeitar.setBounds(360, 220, 110, 25);
        this.add(rejeitar);

        listPedidos = new DefaultListModel();
//        listPedidos.addElement("Pedido Sistema.CamadaDeDados.Descarga A");


        listInfo = new DefaultListModel();
//        listInfo.addElement("Pedido que contém A");

        pedidos = new JList(listPedidos);
        pedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll = new JScrollPane(pedidos);
        scroll.setBounds(65,55,350,150);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportBorder(new LineBorder(Color.BLACK));
        this.add(scroll);

    }

    @Override
    public TreeMap<String, JButton> getButtons() {
        TreeMap<String, JButton> res = new TreeMap<String, JButton>();
        res.put("voltar",voltar);
        res.put("detalhes",detalhes);
        res.put("aceitar",aceitar);
        res.put("rejeitar",rejeitar);
        return res;
    }

    public JPanel setVoltar(){return View.updatePanel(this,new GestorLogadoUI());}

    public void setList(Duplo<Collection<Integer>,Collection<String>> d) {
        setListPedidos(d.getFirst());
        setListInfo(d.getSecond());
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

    private void setListPedidos(Collection<Integer> c) {
        listPedidos.removeAllElements();
        listPedidos.addAll(c);
    }

    private void setListInfo(Collection<String> c) {
        listInfo.removeAllElements();
        listInfo.addAll(c);
    }

    public void setList(TreeMap<Integer,Duplo<Integer,String>> l) {
        setListPedidos(l.keySet());
        ArrayList<String> r = new ArrayList<String>();
        for (Duplo<Integer,String> d: l.values()) {
            r.add("detalhes:\n" + d.getSecond() + "\nquantidade: " + d.getFirst());
        }
        setListInfo(r);

        pedidos.setSelectedIndex(0);

        Boolean applied = true;
        if(l.size() == 0) {
            applied = false;
        }
        detalhes.setEnabled(applied);
        aceitar.setEnabled(applied);
        rejeitar.setEnabled(applied);

    }

    public Integer getSelected() {
        int index = pedidos.getSelectedIndex();
        return (Integer) listPedidos.getElementAt(index);
    }

    public void detalhes() {
        int index = pedidos.getSelectedIndex();
        infopedido = (String) listInfo.getElementAt(index);
        nomepedido = (Integer) listPedidos.getElementAt(index);
        JOptionPane.showMessageDialog(this,infopedido,nomepedido.toString(),JOptionPane.INFORMATION_MESSAGE);
    }





}