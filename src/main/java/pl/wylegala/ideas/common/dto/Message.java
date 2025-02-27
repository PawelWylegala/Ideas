package pl.wylegala.ideas.common.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String title;
    private String content;

    //info
    public static Message info(String msg){
        return new Message("Info",msg);
    }

    public static Message error(String msg){
        return new Message("Error",msg);
    }
}
