package mvc.views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;


public class LoginUI extends JPanel implements GetButtons {

    private static JLabel labelimagem;
    private static JLabel nomeLabel;
    private static JTextField nomeTf;
    private static JLabel passwordLabel;
    private static JPasswordField passwordTf;
    private static JButton login;
    private static JButton voltar;

    public LoginUI() {

        this.setName("Login");

        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        this.setLayout(null);

        this.setLayout(null);

        labelimagem = new JLabel();
        labelimagem.setBounds(215, 25, 75,70);
        labelimagem.setIcon(new ImageIcon("src/mvc/views/keylock.jpg"));
        this.add(labelimagem);

        nomeLabel = new JLabel("ID");
        nomeLabel.setBounds(100, 110, 70, 25);
        this.add(nomeLabel);

        nomeTf = new JTextField(20);
        nomeTf.setBounds(180, 110, 160, 25);
        this.add(nomeTf);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 140, 70, 25);
        this.add(passwordLabel);

        passwordTf = new JPasswordField(20);
        passwordTf.setBounds(180, 140, 160, 25);
        this.add(passwordTf);

        login = new JButton("Login");
        login.setName("login");
        login.setBounds(205, 180, 110, 25);
        this.add(login);
        login.setEnabled(false);

        mvc.views.JButtonStateController stateController = new mvc.views.JButtonStateController(login);

        Document document1 = passwordTf.getDocument();
        Document document2 = nomeTf.getDocument();

        stateController.addDocument(document1);
        stateController.addDocument(document2);

        document1.addDocumentListener(stateController);
        document2.addDocumentListener(stateController);

        voltar = new JButton("Voltar");
        voltar.setName("voltar");
        voltar.setBounds(15, 220, 110, 25);
        this.add(voltar);

    }

    public String getNome(){
        return nomeTf.getText();
    }

    public String getPassword(){
        return String.valueOf(passwordTf.getPassword());
    }

    public void loginErrorMessage(){
        JOptionPane.showMessageDialog(this,"Credenciais inválidas!","Login",JOptionPane.ERROR_MESSAGE);
    }

    public void loginSuccessfulMessage(){
        JOptionPane.showMessageDialog(this,"Login bem-sucedido!","Login",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public TreeMap<String, JButton> getButtons() {
        TreeMap<String,JButton> list = new TreeMap<String,JButton>();
        list.put("login",login);
        list.put("voltar",voltar);
        return list;
    }

    public JPanel setLogin() {
        return View.updatePanel(this,new GestorLogadoUI());
    }

    public JPanel setVoltar() {
        return View.updatePanel(this,new InicialUI());
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




