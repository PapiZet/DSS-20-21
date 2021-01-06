package mvc.controllers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import Sistema.CamadaDeDados.Duplo;
import mvc.models.*;
import mvc.views.*;

import javax.swing.*;

public class Controller {

    private Model model;
    private View view;
    private ActionListener actionListener;


    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    public void contol(){
        InicialUI buff = new InicialUI();
        view.setPanel(buff);

        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object o = e.getSource();
                if (o instanceof JButton) {
                    JButton button = (JButton) o;
                    Container parent = button.getParent();
                    String buttonName = button.getName();

                    // INICIAL UI_____________________________________________
                    if(parent instanceof InicialUI) {
                        InicialUI newParente = (InicialUI) parent;

                        if(buttonName.equals("gestor")) {
                            JPanel buff = newParente.setGestor();
                            setModel(ModelLogin.getInstance());

                            updateButtons(buff,this);
                            view.update();
                        }
                        else if(buttonName.equals("motorista")) {
                            JPanel buff = newParente.setMotorista();
                            updateButtons(buff,this);
                            model = ModelDescarga.getInstance();
                            view.update();
                        }
                        else if(buttonName.equals("leitorqr")) {
                            JPanel buff = newParente.setLeitorQr();
                            model = ModelLeitorQRCode.getInstance();
                            ((LeitorQRCodeUI) buff).setListQRCLeitores(((ModelLeitorQRCode) model).getLeitores());
                            updateButtons(buff,this);
                            view.update();
                        }
                        else if(buttonName.equals("robot")) {
                            EscolherRobotUI buff = newParente.setRobot();
                            setModel(ModelEscolherRobot.getInstance());

                            buff.setListRobots(((ModelEscolherRobot) model).getRobotsLivresID());
                            buff.setListRobotsR(((ModelEscolherRobot) model).getRobotsBuscarID());
                            buff.setListRobotsE(((ModelEscolherRobot) model).getRobotsEntregaID());

                            updateButtons(buff,this);
                            view.update();
                        }
                        //-------------------------------------------------------------
                        //DESCARGA UI__________________________________________________
                    } else if (parent instanceof DescargaUI) {
                        DescargaUI newParente = (DescargaUI) parent;
                        if(buttonName.equals("enviarpedido")) {
                            String detalhe = newParente.getDetalhes();
                            String quantidadeS = newParente.getQuantidade();
                            Integer quantidade;
                            try{
                                quantidade = Integer.parseInt(quantidadeS);
                                newParente.loginSuccessfulMessage();
                                ((ModelDescarga) model).motoristaAddDescarga(detalhe,quantidade);
                            } catch (NumberFormatException d) {
                                newParente.descargaErrorMessage();
                            }
                            //updateButtons(newParente,this);
                            view.update();
                        } else if(buttonName.equals("voltar")) {
                            JPanel buff = newParente.setVoltar();
                            view.setPanel(buff);
                            updateButtons(buff,this);
                            view.update();
                        }
                        //ESCOLHER ROBOT UI_____________________________________________
                    } else if (parent instanceof EscolherRobotUI) {
                        EscolherRobotUI newParente = (EscolherRobotUI) parent;
                        if(buttonName.equals("voltar")) {
                            JPanel buff = newParente.setVoltar();
                            updateButtons(buff,this);
                            view.update();
                        } else if(buttonName.equals("notificarr")) {
                            Integer selecionada = newParente.getSelectedR();

                            ModelEscolherRobot robots = (ModelEscolherRobot) model;

                            robots.pegouPalete(selecionada);

                            newParente.setListRobots(robots.getRobotsLivresID());
                            newParente.setListRobotsR(robots.getRobotsBuscarID());
                            newParente.setListRobotsE(robots.getRobotsEntregaID());
                            view.update();
                        } else if(buttonName.equals("notificare")) {
                            Integer selecionada = newParente.getSelectedE();

                            ModelEscolherRobot robots = (ModelEscolherRobot) model;

                            robots.deixouPalete(selecionada);

                            newParente.setListRobots(((ModelEscolherRobot) model).getRobotsLivresID());
                            newParente.setListRobotsR(((ModelEscolherRobot) model).getRobotsBuscarID());
                            newParente.setListRobotsE(((ModelEscolherRobot) model).getRobotsEntregaID());
                            view.update();
                        }
                        // LOGIN UI--------------------------------------------------------
                    } else if (parent instanceof LoginUI) {
                        LoginUI newParente = (LoginUI) parent;
                        if(buttonName.equals("login")) {

                            String idS = newParente.getNome();
                            try {
                                Integer id = Integer.parseInt(idS);
                                String password = newParente.getPassword();

                                String nome = ((ModelLogin) model).login(id,password);

                                if(nome == null) {
                                    newParente.loginErrorMessage();
                                }else {
                                    newParente.loginSuccessfulMessage();
                                    JPanel buff = newParente.setLogin();
                                    ((GestorLogadoUI) buff).setGestor(nome);
                                    updateButtons(buff,this);
                                    view.update();
                                }
                            } catch (NumberFormatException d) {
                                newParente.loginErrorMessage();
                            }

                        } else if(buttonName.equals("voltar")) {
                            JPanel buff = newParente.setVoltar();
                            view.setPanel(buff);
                            updateButtons(buff,this);
                            view.update();
                        }
                        //GESTOR LOGADO UI-----------------------------------------------
                    } else if(parent instanceof GestorLogadoUI) {
                        GestorLogadoUI newParente = (GestorLogadoUI) parent;
                        if (buttonName.equals("listagemlocalizacoes")) {

                            Integer gestorId = newParente.getGestorID();
                            String gestor = newParente.getGestor();

                            JPanel buff = newParente.setListagemLocalizacoes();
                            setModel(ModelListagemLocalizacoes.getInstance());

                            ((ListagemLocalizacoesUI) buff).setGestorId(gestorId);
                            ((ListagemLocalizacoesUI) buff).setGestor(gestor);

                            Duplo<TreeMap<Integer,Integer>,TreeMap<Integer,Integer>> list = ((ModelListagemLocalizacoes) model).getListagens();
                            ((ListagemLocalizacoesUI) buff).setList(list.getFirst(), list.getSecond());

                            updateButtons(buff, this);
                            view.update();

                        } else if (buttonName.equals("pedidosdescarga")) {
                            Integer gestorId = newParente.getGestorID();
                            String gestor = newParente.getGestor();

                            JPanel buff = newParente.setPedidosDescarga();
                            setModel(ModelPedidosDescarga.getInstance());

                            ((PedidosDescargaUI) buff).setGestorId(gestorId);
                            ((PedidosDescargaUI) buff).setGestor(gestor);

                            TreeMap<Integer, Duplo<Integer,String>> listagem = ((ModelPedidosDescarga) model).getDescargasPendentes();
                            ((PedidosDescargaUI) buff).setList(listagem);

                            updateButtons(buff, this);
                            view.update();

                        } else if (buttonName.equals("logout")) {
                            JPanel buff = newParente.setLogout();
                            setModel(ModelLogin.getInstance());
                            updateButtons(buff, this);
                            view.update();
                        }
                        //LEITOR QRCODE UI-----------------------------------------------
                    } else if(parent instanceof LeitorQRCodeUI) {
                        LeitorQRCodeUI newParente = (LeitorQRCodeUI) parent;
                        if (buttonName.equals("selecionar")) {
                            Integer leitorId = newParente.getSelectedValue();
                            String detalhes = ((ModelLeitorQRCode) model).getDetalhes(leitorId);
                            if(detalhes == null) newParente.noPaletes();
                            else {
                                JPanel buff = newParente.setSelecionar();
                                ((InserirQRCodeUI) buff).setLeitorId(leitorId);
                                ((InserirQRCodeUI) buff).setDetalheString(detalhes);
                                setModel(ModelInserirQRCode.getInstance());
                                updateButtons(buff,this);
                                view.update();
                            }
                        } else if (buttonName.equals("voltar")) {
                            JPanel buff = newParente.setVoltar();
                            updateButtons(buff, this);
                            view.update();
                        } else if (buttonName.equals("add")) {
                            ((ModelLeitorQRCode) model).addLeitorQRC();
                            newParente.setListQRCLeitores(((ModelLeitorQRCode) model).getLeitores());
                            view.update();
                        }
                    } else if(parent instanceof InserirQRCodeUI) {
                        InserirQRCodeUI newParent = (InserirQRCodeUI) parent;
                        if (buttonName.equals("voltar")) {
                            Integer leitorId = newParent.getLeitorId();
                            ((ModelInserirQRCode) model).notFinished(leitorId);

                            JPanel buff = newParent.setVoltar();
                            setModel(ModelLeitorQRCode.getInstance());
                            ((LeitorQRCodeUI) buff).setListQRCLeitores(((ModelLeitorQRCode) model).getLeitores());
                            updateButtons(buff, this);
                            view.update();
                        } else if (buttonName.equals("inserir")){
                            Integer leitorId = newParent.getLeitorId();
                            String qrCode = newParent.getQrCode();
                            ((ModelInserirQRCode) model).finished(leitorId,qrCode);

                            String detalhes = (ModelLeitorQRCode.getInstance()).getDetalhes(leitorId);
                            if(detalhes == null) {
                                JPanel buff = newParent.setVoltar();
                                setModel(ModelLeitorQRCode.getInstance());
                                ((LeitorQRCodeUI) buff).setListQRCLeitores(((ModelLeitorQRCode) model).getLeitores());
                                updateButtons(buff, this);
                                view.update();
                            }
                            else {
                                newParent.setDetalheString(detalhes);
                                newParent.emptyQrCode();
                                view.update();
                            }
                        }
                    } else if (parent instanceof PedidosDescargaUI) {
                        PedidosDescargaUI newParent = (PedidosDescargaUI) parent;
                        if (buttonName.equals("voltar")) {
                            String gestor = newParent.getGestor();
                            JPanel buff = newParent.setVoltar();
                            ((GestorLogadoUI) buff).setGestor(gestor);
                            updateButtons(buff, this);
                            view.update();
                        } else if (buttonName.equals("detalhes")) {
                            newParent.detalhes();
                            view.update();
                        } else if (buttonName.equals("aceitar")) {
                            Integer selected = newParent.getSelected();
                            ((ModelPedidosDescarga) model).aceite(selected);

                            TreeMap<Integer, Duplo<Integer,String>> listagem = ((ModelPedidosDescarga) model).getDescargasPendentes();
                            newParent.setList(listagem);

                            view.update();

                        } else if (buttonName.equals("rejeitar")) {
                            Integer selected = newParent.getSelected();
                            ((ModelPedidosDescarga) model).rejeitado(selected);

                            TreeMap<Integer, Duplo<Integer,String>> listagem = ((ModelPedidosDescarga) model).getDescargasPendentes();
                            newParent.setList(listagem);

                            view.update();
                        }
                    } else if (parent instanceof ListagemLocalizacoesUI) {
                        ListagemLocalizacoesUI newParent = (ListagemLocalizacoesUI) parent;
                        if (buttonName.equals("voltar")) {
                            String gestor = newParent.getGestor();
                            JPanel buff = newParent.setVoltar();
                            ((GestorLogadoUI) buff).setGestor(gestor);
                            updateButtons(buff, this);
                            view.update();
                        } else if (buttonName.equals("detalhes")) {
                            newParent.detalhes();
                            view.update();
                        } else if (buttonName.equals("alternar")) {
                            if (newParent.getLayoutBool()) {
                                Duplo<TreeMap<Integer, ArrayList<Integer>>, TreeMap<Integer, ArrayList<Integer>>> list = ((ModelListagemLocalizacoes) model).getListagensNewLayout();
                                newParent.setListLayout(list.getFirst(), list.getSecond());
                            } else {
                                Duplo<TreeMap<Integer, Integer>, TreeMap<Integer, Integer>> list = ((ModelListagemLocalizacoes) model).getListagens();
                                newParent.setList(list.getFirst(), list.getSecond());
                            }
                            view.update();
                        }
                    }

                }
            }
        };

        updateButtons(buff, actionListener);
        view.update();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void updateButtons(JPanel jpanel, ActionListener actionListener){
        for (JButton b: ((GetButtons) jpanel).getButtons().values()) {
            b.addActionListener(actionListener);
        }
    }
}

