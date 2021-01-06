package mvc.views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;


public class InserirQRCodeUI extends JPanel implements GetButtons {

    private static JLabel labelimagem;
    private static JLabel detalhes;
    private static JLabel detalhe;
    private static JLabel qrcode;
    private static JTextField qrcodeTf;
    private static JButton voltar;
    private static JButton inserir;
    private Integer leitorId;
    private String detalheString;

    InserirQRCodeUI() {

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black,5));
        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(40, 35, 150,150);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/qrcode.jpg"));
        this.add(labelimagem);

        detalhes = new JLabel("Detalhes da palete:");
        detalhes.setBounds(270, 40, 250, 25);
        this.add(detalhes);

        detalhe = new JLabel("Maçãs");
        detalhe.setBounds(305, 60, 250, 25);
        this.add(detalhe);

        qrcode = new JLabel("QR-Code:");
        qrcode.setBounds(244, 90, 250, 25);
        this.add(qrcode);

        qrcodeTf = new JTextField(20);
        qrcodeTf.setBounds(244, 110, 160, 25);
        this.add(qrcodeTf);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 110, 25);
        this.add(voltar);

        inserir = new JButton("Inserir");
        inserir.setName("inserir");
        inserir.setBounds(270, 145, 110, 25);
        inserir.setEnabled(false);
        this.add(inserir);

        mvc.views.JButtonStateController stateController = new mvc.views.JButtonStateController(inserir);

        Document document1 = qrcodeTf.getDocument();

        stateController.addDocument(document1);

        document1.addDocumentListener(stateController);

    }

    public void setDetalheString(String detalheString) {
        this.detalheString = detalheString;
        detalhe.setText(detalheString);
    }

    public JPanel setVoltar() {
        return View.updatePanel(this,new LeitorQRCodeUI());
    }

    public String getQrCode() {
        return qrcodeTf.getText();
    }

    public void emptyQrCode(){
        qrcodeTf.setText("");
    }

    public void setLeitorId(Integer id) {
        this.leitorId = id;
    }

    public Integer getLeitorId() {
        return this.leitorId;
    }

    @Override
    public TreeMap<String, JButton> getButtons() {
        TreeMap<String,JButton> res = new TreeMap<String, JButton>();
        res.put("voltar",voltar);
        res.put("inserir",inserir);
        return res;
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
}