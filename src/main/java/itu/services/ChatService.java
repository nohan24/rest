package itu.services;

import itu.entity.sql.Chat;

import java.util.List;

public interface ChatService {

    List<Chat> getChatByUserId(Integer User);

    Chat createChat(Chat chat);

}
