package itu.services;

import itu.DTO.MessageDTO;
import itu.entity.sql.Message;

import java.util.List;

public interface MessageService {
    Message addMessage(MessageDTO messageDTO);

    List<Message> getListMessageByIdChat(Integer senderId,Integer receiverId);
}
