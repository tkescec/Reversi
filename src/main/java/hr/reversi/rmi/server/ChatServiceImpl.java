package hr.reversi.rmi.server;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService {

    List<String> chatHistoryMessageList;

    public ChatServiceImpl() {
        chatHistoryMessageList = new ArrayList<>();
    }

    @Override
    public void sendMessage(String newMessage) {
        chatHistoryMessageList.add(newMessage);
    }

    @Override
    public List<String> getChatHistory() {
        return chatHistoryMessageList;
    }
}
