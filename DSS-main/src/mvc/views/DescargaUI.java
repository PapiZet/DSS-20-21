package mvc.views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;


public class DescargaUI extends JPanel implements GetButtons {

    private static JLabel labelimagem;
    private static JLabel labelimagem2;
    private static JLabel label;
    private static JLabel detalhes;
    private static JLabel quantidade;
    private static JTextField detalhesTF;
    private static JTextField quantidadeTF;
    private static JButton voltar;
    private static JButton enviarPedido;

    DescargaUI() {

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(20, 55, 125,87);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/truck.jpg"));
        this.add(labelimagem);

        labelimagem2 = new JLabel();
        labelimagem2.setBounds(345, 57, 100,100);
        labelimagem2.setIcon(new ImageIcon("src/mvc/views/palete.jpg"));
        this.add(labelimagem2);

        label = new JLabel("Boa viagem, conduza com precaução!");
        label.setBounds(250, 219, 250, 25);
        this.add(label);

        detalhes = new JLabel("Detalhes da descarga:");
        detalhes.setBounds(160, 25, 250, 25);
        this.add(detalhes);

        detalhesTF = new JTextField(20);
        detalhesTF.setBounds(160, 50, 160, 25);
        this.add(detalhesTF);



        quantidade = new JLabel("Quantidade de paletes:");
        quantidade.setBounds(160, 90, 250, 25);
        this.add(quantidade);

        quantidadeTF = new JTextField(20);
        quantidadeTF.setBounds(160, 115, 160, 25);
        this.add(quantidadeTF);

        enviarPedido = new JButton("Enviar pedido");
        enviarPedido.setName("enviarpedido");
        enviarPedido.setBounds(185, 160, 110, 25);
        enviarPedido.setEnabled(false);
        this.add(enviarPedido);

        JButtonStateController stateController = new JButtonStateController(enviarPedido);

        Document document1 = detalhesTF.getDocument();
        Document document2 = quantidadeTF.getDocument();

        stateController.addDocument(document1);
        stateController.addDocument(document2);

        document1.addDocumentListener(stateController);
        document2.addDocumentListener(stateController);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 110, 25);
        this.add(voltar);

    }

    public TreeMap<String,JButton> getButtons() {
        TreeMap<String,JButton> list = new TreeMap<String,JButton>();
        list.put("Enviar pedido",enviarPedido);
        list.put("Voltar",voltar);
        return list;

    }

    public String getDetalhes(){return detalhesTF.getText();}

    public String getQuantidade(){return quantidadeTF.getText();}

    public JPanel setVoltar() {
        return View.updatePanel(this,new InicialUI());
    }


    public void descargaErrorMessage(){
        JOptionPane.showMessageDialog(this,"Introduza uma quantidade de paletes válida!","Pedido de descarga:",JOptionPane.ERROR_MESSAGE);
        quantidadeTF.setText("");
    }

    public void loginSuccessfulMessage(){
        JOptionPane.showMessageDialog(this,"Pedido enviado com sucesso!","Pedido de descarga:",JOptionPane.INFORMATION_MESSAGE);
        detalhesTF.setText("");
        quantidadeTF.setText("");
    }

}

class JButtonStateController implements DocumentListener {
    JButton button;
    TreeMap<String,Boolean> documents;

    JButtonStateController(JButton button) {
        this.button = button ;
        this.documents = new TreeMap<String,Boolean>();
    }

    public void addDocument(Document d) {
        documents.put(d.toString(),false);
    }

    public void changedUpdate(DocumentEvent e) {
        disableIfEmpty(e);
    }

    public void removeUpdate(DocumentEvent e) {
        disableIfEmpty(e);
    }

    public void disableIfEmpty(DocumentEvent e) {
        if(e.getDocument().getLength() > 0) documents.put(e.getDocument().toString(),true);
        else documents.put(e.getDocument().toString(),false);
        Boolean res = true;
        for (Boolean v: documents.values() ) {
            if(!v) {
                res = false;
                break;
            }
        }
        button.setEnabled(res);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        disableIfEmpty(e);
    }
}