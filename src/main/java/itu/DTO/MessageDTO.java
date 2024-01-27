package itu.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private Integer senderId;
    private Integer receiverId;
    private String messageContent;

}
