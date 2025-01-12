public class Spiel
{
    int pPortNr = 55;
    List SpListe = new List();
    List KListe = new List();
    GameServer gSer = new GameServer(pPortNr);
    Spieler SpAkt;
    Karte StK;

    public String getPort()
    {
        return Integer.toString(pPortNr);
    }

    public GameServer retServ()
    {
        return gSer;
    }

    public List getSpL()
    {
        return gSer.getSpL();
    }

    public void start()
    {
        resetKList();
        resetSpList();
        fillKList();
        gibStartKarten();
        StK = gibStartK();
        gSer.setAktK(StK.getBez());
        List SpL = gSer.getSpL();
        SpL.toFirst();
        SpAkt = (Spieler)SpL.getContent();
        SpAkt.setDran(true);
    }

    public void fillKList()
    {
        if(KListe.hasAccess())
        {
            KListe.toFirst();
            while(KListe.hasAccess())
            {
                KListe.remove();
            }
        }
        for (int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 9; i++)//blau
            {
                Karte k = new Karte();
                k.setFarbe("bl");
                k.setZahl(i);
                k.setBez(k.getFarbe() + " " + k.getZahl());
                KListe.append(k);
            }
        }   
        for (int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 9; i++)//grün
            {
                Karte k = new Karte();
                k.setFarbe("gr");
                k.setZahl(i);
                k.setBez(k.getFarbe() + " " + k.getZahl());
                KListe.append(k);
            }
        }  
        for (int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 9; i++)//rot
            {
                Karte k = new Karte();
                k.setFarbe("ro");
                k.setZahl(i);
                k.setBez(k.getFarbe() + " " + k.getZahl());
                KListe.append(k);
            }
        }  
        for (int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 9; i++)//gelb
            {
                Karte k = new Karte();
                k.setFarbe("ge");
                k.setZahl(i);
                k.setBez(k.getFarbe() + " " + k.getZahl());
                KListe.append(k);
            }
        }  
        for(int i = 0; i < 2; i++)//blau Zieh 2
        {
            Karte k = new Karte();
            k.setFarbe("bl");
            k.setBez(k.getFarbe() + " 2 ziehen");
            KListe.append(k);
        }
        for(int i = 0; i < 2; i++)//grün Zieh 2
        {
            Karte k = new Karte();
            k.setFarbe("gr");
            k.setBez(k.getFarbe() + " 2 ziehen");
            KListe.append(k);
        }
        for(int i = 0; i < 2; i++)//rot Zieh 2
        {
            Karte k = new Karte();
            k.setFarbe("ro");
            k.setBez(k.getFarbe() + " 2 ziehen");
            KListe.append(k);
        }
        for(int i = 0; i < 2; i++)//gelb Zieh 2
        {
            Karte k = new Karte();
            k.setFarbe("ge");
            k.setBez(k.getFarbe() + " 2 ziehen");
            KListe.append(k);
        }
        for(int i = 0; i < 4; i++)//Farbwahl
        {
            Karte k = new Karte();
            k.setBez("Farbwahl");
            KListe.append(k);
        }
        for(int i = 0; i < 4; i++)//Farbwahl
        {
            Karte k = new Karte();
            k.setBez("Farbwahl 4 ziehen");
            KListe.append(k);
        }
    }

    public void resetKList()
    {
        KListe.toFirst();
        while(KListe.hasAccess())
        {
            KListe.remove();
        }
    }
    
    public void resetSpList()
    {
        List SpL = gSer.getSpL();
        SpL.toFirst();
        Spieler sp;
        while(SpL.hasAccess())
        {
            sp = (Spieler)SpL.getContent();
            sp.setFertig(false);
            sp.setDran(false);
            SpL.next();
        }
    }

    public int gibRand()
    {
        int rand = (int)Math.floor(Math.random() * 97);//97, weil Methode gibt Zahl zwischen 0 und 96 aus
        return rand;                
    }

    public void gibStartKarten()
    {
        List SpL = gSer.getSpL();
        gSer.setAktK(vergK().getBez());
        Spieler Sp;
        Karte k;
        if(SpL.length() == 0)
        {
            System.out.println("Keine Spieler vorhanden");
        }
        else
        {
            if(SpL.length() == 1)
            {
                System.out.println("Ein Spieler ist mindestens einer zu wenig");
            }
            else
            {
                if(SpL.length() >= 2)
                {
                    SpL.toFirst();
                    for(int i = 0; i < SpL.length() * 5; i++)
                    {
                        Sp = (Spieler)SpL.getContent();
                        k = vergK();
                        Sp.getList().append(k);
                        SpL.next();
                        if(!SpL.hasAccess())
                        {
                            SpL.toFirst();
                        }
                    }
                }
            }
        }
    }      

    public Karte gibStartK()
    {
        Karte k = vergK();
        return k;
    }

    public Karte getStartK()
    {
        return StK;
    }

    public Karte vergK()
    {
        Karte k;
        int rand = gibRand();
        KListe.toFirst();
        for(int i = 0; i < rand; i++)
        {
            KListe.next();
        }
        k = (Karte)KListe.getContent();
        return k;
    }

    public void gibKarten(String pSpIp)
    {
        Karte k;
        Spieler Sp;
        List SpL = gSer.getSpL();
        SpL.toFirst();
        while(SpL.hasAccess())
        {
            Sp = (Spieler)SpL.getContent();
            if(pSpIp == Sp.getIp())
            {
                k = vergK();
                Sp.getList().append(k);
            }
            else
            {
                SpL.next();
            }
        }
    }

    public void nextSp()
    {
        List SpL = new List();
        List SpZ = new List();
        SpListe.toFirst();
        while(SpListe.hasAccess())
        {
            SpL.append(SpListe.getContent());
            SpZ.append(SpListe.getContent());
            SpListe.next();
        }
        Spieler sp;
        int anzNFertig = 0;
        while(SpAkt.getFertig() == true)
        {
            if(SpL.hasAccess())
            {
                SpL.next();
            }
            else
            {
                SpL.toFirst();
            }
            SpZ.toFirst();
            while(SpZ.hasAccess())
            {
                sp = (Spieler)SpZ.getContent();
                if(sp.getFertig() == false)
                {
                    anzNFertig = anzNFertig + 1;
                    SpZ.next();
                }
                else
                {
                    SpZ.next();
                }
            }
        }
        if(anzNFertig < 2)
        {
            gSer.sendToAll("");
        }
        SpAkt = (Spieler)SpL.getContent();
        SpAkt.setDran(true);
    }

    public Spieler getAktSp()
    {
        List SpL = gSer.getSpL();
        SpL.toFirst();
        Spieler sp;
        while(SpL.hasAccess())
        {
            sp = (Spieler)SpL.getContent();
            if(sp.getDran() == true)
                return sp;
            else
                SpL.next();
        }
        return null;
    }

    public String spielzug(Karte pKarte)
    {
        String bez = pKarte.getBez();
        return bez;
    }

    public void ende()
    {
        gSer.sendToAll("Der Server wird jetzt geschlossen");
        System.exit(0);
    }
}