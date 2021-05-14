package rs.edu.mg.ivb.chatMannager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.User;


public class ChatManager extends TimerTask{
    
     
    User user;
    
    public ChatManager(User u){
        this.user=user;
        
        
        
    }
    
    //(allch,newmess,menjanje onog glupog dela za or)->odradjeno  iiii jos dodavanje da se uzme prethodnih 20 poruka, grupe
    //count je broj stranice ya poruke, brojanje krece od 0. Vraca 20x2 matricu gde je prva vrednost teks a druga vreme poslatog teksta
    public String[][] last20mess(Chat c,int count){
        String l1[][] = new String[20][2];
        
        String upit = "SELECT M.messtime AS MTIME,M.messtext AS MTEXT" +
                      "FROM message M"+
                      "WHERE M.IdChat = ?"+
                      "ORDER BY M.messtime DESC" +
                      "LIMIT 20 OFFSET ?";
        try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit);){
            ps.setInt(1,count*20);
            ps.setInt(1,c.IdChat);
            ps.execute();
            ResultSet rs=ps.getResultSet();
            for (int i=0;rs.next();i++)
            {
                
                    String mTime = rs.getString("MTIME");
                    String mText = rs.getString("MTEXT");
                    l1[i][0] = mText;
                    l1[i][1] = mTime;
            }
        }catch(SQLException e){
        e.printStackTrace();
    }    
        return l1;
   
    }
    
    public void sendMessage(Chat chat,String text){
    long millis=System.currentTimeMillis();
    Date date=new Date(millis);
    String upit="INSERT INTO message(IdChat,IdSender,messtext,messtime)VALUES(?,?,?,?)";//pre nego sto je poruka uopste poslata
    //ona ce se pojaviti u chatu osobe koja je salje i onda joj je status N ,da bi se prebacilo na L, mora zapravo da se posalje
    String upit1="UPDATE message SET messstatus='L' WHERE messtime=? AND IdSender=?";//za prebacivanje na L
    try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit);PreparedStatement ps1=conn.prepareStatement(upit1)){
        conn.setAutoCommit(false);
        ps.setInt(1,chat.IdChat);
        ps.setInt(2,this.user.ID);
        ps.setString(3,text);
        ps.setDate(4,date);
        ps.execute();
        //OVDE SAD TREBA SMISLITI STA ZA COMMIT ,ma da mislim da moze ovako da ostane 
        ps1.setDate(1,date);
        ps1.setInt(2,this.user.ID);
        ps1.execute();//Prevacuje na l
        conn.commit();
        conn.setAutoCommit(true);

        
    }catch(SQLException e){
        e.printStackTrace();
    }    
        
        
    }/*
    public void sendMessage(User sender,User receiver,String text){
       OVO NE MORA JER NEMA SLUCAJA U KOM SALJEMO NEKOME PORUKU A NE ZNAMO U KOM SE CHATU SALJE  
    }*/
    
    //da li su 2 osobe zajedno u nekom chatu 
    public boolean areChatting(User b){
        
       for(Chat i: this.allChats()){
           if(i.IdTo==b.ID||i.IdFrom==b.ID) return true;
       }        
       return false;
    }
    
    
    //pravi chat izmedju 2 osobe 
    public void createNewChat(User b){
        String upit="INSERT INTO CHAT(IdFrom,IdTo) VALUES(?,?)";
        try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit)){
            ps.setInt(1, this.user.ID);
            ps.setInt(2,b.ID);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    
    //KAKO NAPRAVITI CHAT ZA GRUPU

    
    //ISPRAVLJENA VERZIJA 8.5.2021.  22:33
    //vraca svaki chat u koji je ova osoba ukljucena
    public List<Chat> allChats(){
        List<Chat> l1=new LinkedList<>();
        String upit="SELECT C.IdChat AS CH,C.IdFrom AS FR,C.IdTo AS TO"
                + "FROM Chat C"
                + "WHERE C.IdFrom=? OR C.IdTo=?";
        try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit)){
            ps.setInt(1,this.user.ID);
            ps.setInt(2,this.user.ID);
            ps.execute();
            
            ResultSet rs=ps.getResultSet();
            while(rs.next()){
                Integer IdChat=rs.getInt("CH");
                Integer IdFrom=rs.getInt("FR");
                Integer IdTo=rs.getInt("TO");
                Chat ch=new Chat(IdChat,IdFrom,IdTo);
                l1.add(ch);
            }
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
     
        return l1;
    }
    //ne znam zasto bi obo bilo korisno ali mozda bi pa eto 
     public List<User> allUsers(){
        List<User> l1=new LinkedList<>();
         String upit="SELECT U.ID,U.Username,U.Password,U.FirstName,U.LastName,U.Email"
                + "FROM User U,Chat C"
                + "WHERE (C.IdFrom=? AND C.IdTo=U.ID) OR (C.IdFrom=U.ID AND C.IdTo=?)";
        try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit)){
            ps.setInt(1,this.user.ID);
            ps.setInt(2,this.user.ID);
            ps.execute();
            
            ResultSet rs=ps.getResultSet();
            while(rs.next()){
                Integer ID=rs.getInt(1);
                String Username=rs.getString(2);
                String Password=rs.getString(3);
                String FirstName=rs.getString(4);
                String LastName=rs.getString(5);
                String Email=rs.getString(6);
                //User u1=new User(ID,Username,Password,FirstName,LastName,Email);
                //l1.add(u1);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
         return l1;
    }  
    
  // promenjen status 22:45 8.5.2021.   
  //promenim status  
//nalazi nove poruke iz odredjenog ceta i ispisuje ih po vremenu,postavlja te poruke na R za received (vise nisu N)
    public List<Message> newMessagesFromChat(Chat c){
        List<Message> mess=new LinkedList<>();
      
        //treba da nadje nove poruke,kasnije ovo se koristi da se prikaze koliko ih ima,i da se sve ispisu
        //prvo iskucati bez tajmera kao nesto sto se jednom radi a posle samo staviti u tajmer, jednostavnije je 
        String upit="SELECT K.ID,K.Username,M.IdMess,M.messtext,M.vreme,M.messstatus"
                + "FROM user K,message M,chat C"
                + "WHERE C.IdChat=? AND M.IdChat=C.IdChat AND M.IdSender!=? AND M.messstatus!='S'AND M.IdSender=K.ID"
                + "ORDER BY M.vreme ASC ";
        
        String upit1="UPDATE message M SET messstatus='R' WHERE IdMess=?";
        
        
        
         try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit);PreparedStatement ps1=conn.prepareStatement(upit1); ){
        ps.setInt(1,c.IdChat);
        ps.setInt(2, this.user.ID);
        ps.execute();
        
        ResultSet rs=ps.getResultSet();
        
        int i=1;
        while(rs.next()){
        int idsender=rs.getInt(1);
        String username=rs.getString(2);
        String text=rs.getString(4);
        String vreme=rs.getString(5);
        Integer idmess=rs.getInt(3);
        
        ps1.setInt(1, idmess);
        ps1.execute();
        /**//**//**//**//**//**//**//**//**//**//**//**//**///********************************************************/
        
       //ovo ne valjaaaaaaaaaaaaaaaaaa 
        /*char status=(char)(rs.getObject(6));*/
        String status ="R";
        
        Message mess1;
        mess1=new Message(idmess,c.IdChat,idsender,text,vreme,status);
        i++;
        mess.add(mess1);
        }
        /*
        while(rs.next()){
            int idsender=rs.getInt("Username");
            String text=rs.getString("messtext");
            String vreme=rs.getString("vreme");
            System.out.println(idsender+text+vreme);
        }*/
        //this can be a void function
        //printallmessagestochat()
        //dealwithgraphics();
        
          
    }catch(SQLException e){
        e.printStackTrace();
    } 
         
         
        return mess;
    } 
    
    //broji ukupan broj neprocitanih poruka, ovo moze za gui da stoji broj na ikonici za poruke
    public int countAllNewMessages(){
        int m=-1;
        String upit="SELECT *"
                + "FROM message M,chat C"
                + "WHERE M.IdChat=C.IdChat AND (C.IdFrom=? OR C.IdTo=?)AND M.IdSender!=? AND M.messstatus!='S'";
        
        try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit)){
        ps.setInt(1,this.user.ID);
        ps.setInt(2, this.user.ID);
        ps.setInt(3, this.user.ID);
        ps.execute();
        m=ps.getMetaData().getColumnCount();
    }catch(SQLException e){
        e.printStackTrace();
    } 
        return m;
    }
        
    //vraca brojeve poruka po chatovima//users 
    //VRACA STRINK KOJI JE KONKATENACIJA IDJA OSOBE OD KOJE IMAMO NEPROCITANE PORUKE,NJENOG USERNAME-A I BRPJ NEPROCITANIH PORUKA
    //VRACAM OVAKO JER NEMAM IDEJU STA DRUGO DA URADIM 
    //rastavljanje posle mogu da srede ljudi koji rade gui njima ce to trebati
    public List<String> countNewMessagesGroupByChats(){
        List<String> l1=new LinkedList<>();
        
        String upit="SELECT U.ID,U.Username,COUNT(*)"
                + "FROM User U,Message M,Chat C"
                + "WHERE M.IdChat=C.IdChat AND (C.IdFrom=? OR C.IdTo=?)AND M.IdSender!=? AND M.messstatus!='S' AND M.IdSender=U.ID"
                + "GROUP BY U.ID";
        
         try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit)){
        ps.setInt(1,this.user.ID);
        ps.setInt(2, this.user.ID);
        ps.setInt(3, this.user.ID);
        ps.execute();
        ResultSet rs=ps.getResultSet();
        while(rs.next()){
            Integer idSender=rs.getInt(1);
            String username=rs.getString(2);
            Integer count=rs.getInt(3);
            
            String s1=idSender+":"+username+":"+count;
            l1.add(s1);
        }
        
    }catch(SQLException e){
        e.printStackTrace();
    } 
        return l1;
    }
   
    //izmenjeno 8.5.2021. 22:40
    //samo seenuje sve poruke u chatu
    public void seenMessage(Chat c){
        //koja je poenta gledati da li je poruka vidjena/kada je poslata ,jer kada udje u chat svakako treba svaka da 
        //bude stavljena na s, a svakako ce svaku da proveri... da li se uopste gubi rveme ako se to proverava???
            String upit="UPDATE message M"/*+",chat C "*/
                    + "SET M.messstatus='S'"
                    + " WHERE M.IdChat=? AND M.IdSender!=?"/*+" AND (C.IdTo=? OR C.IdFrom=?)"*/+" AND M.messstatus!='S'";
         try(Connection conn=DBConnection.getConnection();PreparedStatement ps=conn.prepareStatement(upit)){
        ps.setInt(1,c.IdChat);
        ps.setInt(2, this.user.ID);
        ps.setInt(3,this.user.ID);
         ps.setInt(4,this.user.ID);
        ps.execute();
    }catch(SQLException e){
        e.printStackTrace();
    } 
    }
    
    
    
    public void manageTime(User u){
        long millis=System.currentTimeMillis();
        Date date=new Date(millis);
        Timer timer = new Timer(); // creating timer
      TimerTask task = new ChatManager(u); // creating timer task
      timer.scheduleAtFixedRate(task,date,5000);
      // scheduling the task at the specified time at fixed-rate
        
    }

    @Override
    public void run() {
         List<Message> mess=new LinkedList<>();
         List<String> l1=new LinkedList<>();
         int j;
     for(Chat i:this.allChats()){
     
        mess= newMessagesFromChat(i);
         l1=countNewMessagesGroupByChats();
         
     }
       j=countAllNewMessages();
       //moze i samo da se rastavi string iz l1 za svaki i da se saberu svi 
       
    }
    
   
    
    
    
}