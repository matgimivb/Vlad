
package rs.edu.mg.ivb.chatMannager;

import rs.edu.mg.ivb.db.dao.User;


public class Message {
    
    public Integer IdMess;
    
    public Integer IdChat;
    
    public Integer IdSender;
    
    public String messtext;
    
    public String messtime;
    
    public char messstatus;
    
    public Message(Integer IdMess,Integer IdChat,Integer IdSender,String messtext,String messtime,char messstatus){
        this.IdMess=IdMess;
        this.IdChat=IdChat;
        this.IdSender=IdSender;
        this.messtime=messtime;
        this.messtext=messtext;
        this.messstatus=messstatus;
        
    }
   
}
