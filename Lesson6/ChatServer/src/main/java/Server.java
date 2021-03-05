import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final Vector<ClientHandler> clients;
    private final AuthService authService;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        logger.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        clients = new Vector<>();

        if (!Database.connect()) {
            RuntimeException e =  new RuntimeException("Невозможно подключиться к базе данных");
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        authService = new DatabaseAuthService();

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            logger.log(Level.INFO, "Сервер работает на порту " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
                logger.log(Level.INFO, "Пользователь подключился");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            Database.disconnect();
            logger.log(Level.INFO, "Сервер отключен");
        }
    }

    public void broadcastMsg(String senderNickname, String msg) {
        logger.log(Level.FINE, "Пользователь " + senderNickname + " отправил сообщение.");
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(senderNickname)) {
                client.sendMsg("Вы: " + msg);
            }else {
                client.sendMsg(senderNickname + ": " + msg);
            }
        }
    }

    public void privateMsg(ClientHandler sender, String receiverNickname, String msg) {
        if (sender.getNickname().equals(receiverNickname)) {
            sender.sendMsg("сообщение: " + msg);
            return;
        }
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(receiverNickname)) {
                client.sendMsg(sender.getNickname() + " написал вам" + ": " + msg);
                sender.sendMsg("написать " + receiverNickname + ": " + msg);
                logger.log(Level.FINE, "Пользователь " + sender.getNickname() + " написал приватное сообещение.");
                return;
            }
        }
        sender.sendMsg("Пользователь " + receiverNickname + " не найден");
        logger.log(Level.FINEST, "Пользователь " + sender.getNickname() + " пытается написать личное сообщение несуществующему пользователю.");
    }

    public void subscribe(ClientHandler Client) {
        clients.add(Client);
        broadcastClientsList();
        logger.log(Level.FINE, "Пользовтаель " + Client.getNickname() + " подключен");
    }

    public void unsubscribe(ClientHandler Client) {
        clients.remove(Client);
        broadcastClientsList();
        logger.log(Level.FINE, "Пользователь " + Client.getNickname() + " отключен");
    }

    public boolean isNickBusy(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder(15 * clients.size());
        sb.append("/clients ");
        for (ClientHandler client : clients) {
            sb.append(client.getNickname()).append(" ");
        }
        sb.setLength(sb.length() - 1);
        String out = sb.toString();
        for (ClientHandler client : clients) {
            client.sendMsg(out);
        }
    }
}