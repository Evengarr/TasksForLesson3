import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private final Vector<ClientHandler> clients;
    private final AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        clients = new Vector<>();

        if (!Database.connect()) {
            throw new RuntimeException("Невозможно подключиться к базе данных");
        }

        authService = new DatabaseAuthService();

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер работает на порту " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
                System.out.println("Пользователь подключился");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Database.disconnect();
            System.out.println("Сервер отключен");
        }
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler client : clients) {
            client.sendMsg(msg);
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
                return;
            }
        }
        sender.sendMsg("Пользователь " + receiverNickname + " не найден");
    }

    public void subscribe(ClientHandler Client) {
        clients.add(Client);
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler Client) {
        clients.remove(Client);
        broadcastClientsList();
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