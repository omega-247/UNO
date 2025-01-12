public class Spieler
{
    String Name = "";
    String Ip = "";
    int Port = 0;
    List KartenL = new List();
    boolean dran = false;
    boolean fertig = false;

    public Spieler(String pIp, int pPort)
    {
        Ip = pIp;
        Port = pPort;
    }

    public List getList()
    {
        return KartenL;
    }
    
    public void bekKarte(Karte k)
    {
        KartenL.append(k);
    }

    public int getKMenge()
    {
        int KMenge = 0;
        KartenL.toFirst();
        for(int i = 0; i > 0; i++)
        {
            KartenL.next();       
        }
        return KMenge;
    }

    public String getIp()
    {
        return Ip;
    }

    public int getPort()
    {
        return Port;
    }

    public void setName(String pName)
    {
        Name = pName;
    }

    public String getName()
    {
        return Name;
    }

    public void setDran(boolean pDran)
    {
        dran = pDran;
    }

    public boolean getDran()
    {
        return dran;
    }
    
    public void setFertig(boolean pFertig)
    {
        fertig = pFertig;
    }
    
    public boolean getFertig()
    {
        return fertig;
    }
}