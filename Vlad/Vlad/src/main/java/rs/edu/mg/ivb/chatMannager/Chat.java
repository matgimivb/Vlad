package rs.edu.mg.ivb.chatMannager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Chat {

    public Integer IdChat;
    public Integer IdFrom;
    public Integer IdTo;

    public Chat(Integer IdChat, Integer IdFrom, Integer IdTo) {
        this.IdChat = IdChat;
        this.IdFrom = IdFrom;
        this.IdTo = IdTo;
    }

}
