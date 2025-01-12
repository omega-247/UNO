import java.util.Random;
public class GameServer extends Server 
{
    Spiel spiel;
    public List ConList;
    public List Spielerliste;
    public String aktKarte = "";

    public void setAktK(String pKarte)//Startkarte wird festgelegt
    {
        aktKarte = pKarte;
    }

    public List getSpL()
    {
        return Spielerliste;
    }

    public GameServer(int pPortNr) {
        super(pPortNr);
        Spielerliste = new List();
    }

    public void processClosingConnection(String pClientIP, int pClientPort) 
    {
        Spieler Verlasser;
        while (Spielerliste.hasAccess() ) {
            Spieler akt = (Spieler) Spielerliste.getContent();
            if (akt.getIp().equals(pClientIP) && akt.getPort() == pClientPort) {
                send (pClientIP, pClientPort, "...und tschuess!");
                Spielerliste.remove();
            }
            Spielerliste.next();
        }
        sendToAll ("Spieler " +  pClientIP + " " + pClientPort + " ist nicht mehr dabei!");
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage)//Spielregeln 
    {
        Spieler aktSp = spiel.getAktSp();

        //Substrings der Karte, die oben auf dem Stapel liegt
        String aktKF = aktKarte.substring(0,1);
        String aktKNr = aktKarte.substring(3,3);
        String aktKSpec = "";
        String aktKFarbW = "";
        if(aktKarte.contains("Farbwahl"))
        {
            if(aktKarte.contains("Farbwahl 4 ziehen"))
            {
                aktKSpec = aktKarte.substring(0,17);
                aktKFarbW = aktKarte.substring(18);
            }
            aktKSpec = aktKarte.substring(0,7);
            aktKFarbW = aktKarte.substring(9);
        }

        //Substrings der Karte, die gespielt werden soll
        String farbe = pMessage.substring(0,1);
        String nr = pMessage.substring(3,3);
        String spec = "";
        String farbWF = "";
        String nK = "";

        //Farbwahlkarten legen
        if(!aktKarte.contains("Farbwahl"))
        {
            if(pMessage.contains("Farbwahl"))
            {
                if(pMessage.contains("Farbwahl 4 ziehen"))
                {
                    spec = pMessage.substring(0,17);
                    farbWF = pMessage.substring(18);//gewünschte Farbe
                    nK = spec + " " + farbWF;
                    aktKarte = nK;
                    this.sendToAll(nK);
                    if(aktSp.getKMenge() == 0)
                    {
                        this.sendToAll(aktSp.getName() + " hat keine Karten mehr!");
                        aktSp.setFertig(true);
                    }
                    aktSp.setDran(false);
                    spiel.nextSp();
                }
                spec = pMessage.substring(0,7);
                nK = spec + " " + pMessage.substring(9);//gewünschte Farbe
                aktKarte = nK;
                this.sendToAll(nK);
                aktSp.setDran(false);
                spiel.nextSp();
            }
        }
        else
        {
            this.send(pClientIP, pClientPort, "Diese Karte passt nicht");
        }

        //2 Ziehen Karten legen
        if(spec.contains("2 ziehen"))
        {
            if(aktKF == farbe)
            {
                nK = farbe + " 2 ziehen";
                aktKarte = nK;
                if(aktSp.getKMenge() == 0)
                {
                    this.sendToAll(aktSp.getName() + " hat keine Karten mehr!");
                    aktSp.setFertig(true);
                }
                this.sendToAll(nK);
                aktSp.setDran(false);
                spiel.nextSp();
            }
            else
            {
                this.send(pClientIP, pClientPort, "Diese Karte passt nicht");
            }
        }

        //Farbwahl liegt oben
        if(aktKarte.contains("Farbwahl"))
        {
            if(farbe == aktKFarbW)
            {
                nK = pMessage;
                this.sendToAll(nK);
                aktSp.setDran(false);
                spiel.nextSp();
            }
            else
            {
                this.send(pClientIP, pClientPort, "Diese Karte passt nicht");
            }
        }

        //2 ziehen liegt oben, Spieler zieht automatisch
        if(aktKarte.contains("2 ziehen"))
        {
            String ip = aktSp.getIp();
            spiel.gibKarten(ip);
            spiel.gibKarten(ip);
        }

        //4 ziehen liegt oben, Spieler zieht automatisch
        if(aktKarte.contains("4 ziehen"))
        {
            String ip = aktSp.getIp();
            for(int i = 0; i < 4; i++)
            {
                spiel.gibKarten(ip);
            }
        }

        //normale Karten legen
        if(aktKF != farbe)//akt. Farbe nicht Farbe neue Karte
        {
            if(aktKNr == nr)//Nummern gleich
            {
                nK = farbe + " " + nr;
                aktKarte = nK;
                if(aktSp.getKMenge() == 0)
                {
                    this.sendToAll(aktSp.getName() + " hat keine Karten mehr!");
                    aktSp.setFertig(true);
                }
                this.sendToAll(nK);//neue Karte
                aktSp.setDran(false);
                spiel.nextSp();
            }
            else
            {
                this.send(pClientIP, pClientPort, "Diese Karte passt nicht");
            }
        }
        else//Farben gleich
        {
            nK = farbe + " " + nr;
            aktKarte = nK;
            if(aktSp.getKMenge() == 0)
            {
                this.sendToAll(aktSp.getName() + " hat keine Karten mehr!");
                aktSp.setFertig(true);
            }
            this.sendToAll(nK);//neue Karte
            aktSp.setDran(false);
            spiel.nextSp();
        }
        
        if(pMessage == "Karte Ziehen")
        {
            Karte k;
            List L;
            aktSp.setDran(false);
            aktSp.bekKarte(spiel.vergK());
            spiel.nextSp();
        }
    }

    public void processNewConnection(String pClientIP, int pClientPort) 
    {
        ConList.append(new Connection (pClientIP, pClientPort));
        Spielerliste.append (new Spieler(pClientIP, pClientPort));
        Spielerliste.toLast();
        Spieler sp = (Spieler)Spielerliste.getContent();
        //sp.setName();
        //hier Namen aus ClientGUI vergeben
        getAnzSp();
    }

    public int getAnzSp()
    {
        int anzSp = 0;
        Spielerliste.toFirst();
        while(Spielerliste.hasAccess())
        {
            Spielerliste.next();
            anzSp = anzSp + 1;
        }
        return anzSp;
    }

    private Spieler gibSpielerIpPort(String pIp, int pPort) 
    {
        Spielerliste.toFirst();
        Spieler ergebnis = null;
        while (Spielerliste.hasAccess() && ergebnis == null) {
            Spieler akt = (Spieler) Spielerliste.getContent();
            if (akt.getIp().equals(pIp) && akt.getPort() == pPort) {
                ergebnis = akt;
            }
            Spielerliste.next();
        }
        return ergebnis;
    }

    private Spieler getSpielerNam(String pName) 
    {
        Spielerliste.toFirst();
        Spieler ergebnis = null;
        while (Spielerliste.hasAccess() && ergebnis == null) {
            Spieler akt = (Spieler) Spielerliste.getContent();
            if (akt.getName().equals(pName)){
                ergebnis = akt;
            }
            Spielerliste.next();
        }
        return ergebnis;
    }
}