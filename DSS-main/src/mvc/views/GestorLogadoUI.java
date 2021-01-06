package mvc.views;

import Sistema.CamadaDeDados.Duplo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;


public class GestorLogadoUI extends JPanel implements GetButtons{

    private JLabel label;
    private String gestor;
    private Integer gestorID;
    private JButton listagemlocalizacoes;
    private JButton pedidosdescarga;
    private JButton logout;


    GestorLogadoUI() {

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.setLayout(null);

        label = new JLabel("Bem Vindo, " + gestor + "!");
        label.setBounds(190, 25, 235, 25);
        this.add(label);

        listagemlocalizacoes = new JButton("Consultar listagem de localizações");
        listagemlocalizacoes.setName("listagemlocalizacoes");
        listagemlocalizacoes.setBounds(123, 70, 235, 25);
        this.add(listagemlocalizacoes);

        pedidosdescarga = new JButton("Verificar pedidos de descarga");
        pedidosdescarga.setName("pedidosdescarga");
        pedidosdescarga.setBounds(123, 100, 235, 25);
        this.add(pedidosdescarga);

        logout = new JButton("Logout");
        logout.setName("logout");
        logout.setBounds(185, 130, 110, 25);
        this.add(logout);

    }

    @Override
    public TreeMap<String,JButton> getButtons() {
        TreeMap<String,JButton> list = new TreeMap<String,JButton>();
        list.put("listagemlocalizacoes",listagemlocalizacoes);
        list.put("pedidosdescarga",pedidosdescarga);
        list.put("logout",logout);
        return list;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
        label.setText("Bem Vindo, " + gestor + "!");
    }

    public void setGestorID(Integer gestorId) {
        this.gestorID = gestorId;
    }

    public Integer getGestorID() {
        return this.gestorID;
    }

    public String getGestor() {
        return this.gestor;
    }

    public JPanel setListagemLocalizacoes() {
        return View.updatePanel(this,new ListagemLocalizacoesUI());
    }

    public JPanel setPedidosDescarga() {
        return View.updatePanel(this,new PedidosDescargaUI());
    }

    public JPanel setLogout() {
        return View.updatePanel(this,new LoginUI());
    }
}
