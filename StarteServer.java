import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * Description
 *
 * @version 1.0 from 09.01.2025
 * @author 
 */

public class StarteServer extends JFrame {
    Spiel spiel = new Spiel();
    boolean spGest = false;

    private JTextField jTextField1 = new JTextField();
    private JTextField jTextField2 = new JTextField();
    private JLabel lServerIP1 = new JLabel();
    private JLabel lServerPort1 = new JLabel();
    private JButton bSpielStarten1 = new JButton();
    private JButton bServerSchliessen1 = new JButton();
    private JTextField jTextField3 = new JTextField();
    private JLabel lAnzahlSpieler1 = new JLabel();

    public StarteServer() { 
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 300; 
        int frameHeight = 267;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("StarteServer");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // start components

        jTextField1.setBounds(24, 48, 112, 24);
        cp.add(jTextField1);
        jTextField2.setBounds(24, 128, 112, 24);
        cp.add(jTextField2);
        lServerIP1.setBounds(24, 16, 80, 24);
        lServerIP1.setText("Server IP");
        cp.add(lServerIP1);
        lServerPort1.setBounds(24, 96, 80, 24);
        lServerPort1.setText("Server Port");
        cp.add(lServerPort1);
        bSpielStarten1.setBounds(160, 48, 112, 24);
        bSpielStarten1.setText("Spiel Starten");
        bSpielStarten1.setMargin(new Insets(2, 2, 2, 2));
        bSpielStarten1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bSpielStarten1_ActionPerformed(evt);
                }
            });
        cp.add(bSpielStarten1);
        bServerSchliessen1.setBounds(160, 184, 112, 24);
        bServerSchliessen1.setText("Server Schließen");
        bServerSchliessen1.setMargin(new Insets(2, 2, 2, 2));
        bServerSchliessen1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bServerSchliessen1_ActionPerformed(evt);
                }
            });
        cp.add(bServerSchliessen1);
        jTextField3.setBounds(160, 128, 112, 24);
        cp.add(jTextField3);
        lAnzahlSpieler1.setBounds(160, 96, 104, 24);
        lAnzahlSpieler1.setText("Anzahl Spieler");
        cp.add(lAnzahlSpieler1);
        // end components

        setVisible(true);

        getServerIP();
        getServerPort();
        while(spGest == false)
        {
            int AnzSp = getAnzSp();
            String Anz = Integer.toString(AnzSp);
            jTextField3.setText(Anz);
        }
    }

    public void getServerIP()
    {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        jTextField1.setText(ip);  
    }

    public void getServerPort()
    {
        String pPort = spiel.getPort();
        jTextField2.setText(pPort);
    }

    public int getAnzSp()
    {
        int anzSp = 0;
        if(spGest != false)
        {
            List SpL = spiel.getSpL();
            SpL.toFirst();
            while(SpL.hasAccess())
            {
                SpL.next();
                anzSp = anzSp + 1;
            }
            return anzSp;
        }
        return anzSp;
    }

    public static void main(String[] args) 
    {
        new StarteServer();
    }

    public void bSpielStarten1_ActionPerformed(ActionEvent evt) 
    {
        spiel.start();
        setSpStart(true);
    }

    public boolean getSpStart()
    {
        return spGest;
    }

    public void setSpStart(boolean pSpGest)
    {
        spGest = pSpGest;
    }

    public void bServerSchliessen1_ActionPerformed(ActionEvent evt) 
    {
        spiel.ende();
    }
}