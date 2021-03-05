import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler {
    private String nickname;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public String getNickname() {
        return nickname;
    }

    public ClientHandler(Server server, Socket socket) {
        logger.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/auth ")) {
                            logger.log(Level.FINE, "Пользователь пытается аутентифицироваться.");
                            String[] tokens = msg.split("\\s");
                            String nickname = server.getAuthService().getNickname(tokens[1], tokens[2]);
                            if (nickname != null && !server.isNickBusy(nickname)) {
                                sendMsg("/auth " + nickname);
                                this.nickname = nickname;
                                server.subscribe(this);
                                break;
                            }
                            logger.log(Level.FINE, "Пользователь не аутентифицирован.");
                        }
                    }
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            if (msg.equals("/end")) {
                                sendMsg("/end");
                                break;
                            }
                            if (msg.startsWith("/w ")) {
                                String[] tokens = msg.split("\\s", 3);
                                server.privateMsg(this, tokens[1], tokens[2]);
                            }
                            if (msg.startsWith("/changenick ")) {
                                logger.log(Level.FINE, "Пользователь " + this.nickname + " пытается сменить ник.");
                                String newNickname = msg.split("\\s", 2)[1];
                                if (newNickname.contains(" ")) {
                                    sendMsg("Ник не может содержать пробелов");
                                    logger.log(Level.FINE, "Новый логин " + this.nickname + " содержит некорректный символы");
                                    continue;
                                }
                                if (server.getAuthService().changeNickname(this.nickname, newNickname)) {
                                    logger.log(Level.FINE, "Новый ник " + this.nickname + ": " + newNickname);
                                    this.nickname = newNickname;
                                    sendMsg("/Никнейм сменен успешно: " + nickname);
                                    server.broadcastClientsList();
                                } else {
                                    sendMsg("Ник уже занят");
                                    logger.log(Level.FINE, "Поьзователь " + this.nickname + " пытается сменить ник на уже занятый.");
                                }
                            }
                        } else {
                            server.broadcastMsg(this.nickname, msg);
                        }
                    }
                } catch (IOException e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
                } finally {
                    ClientHandler.this.disconnect();
                }
            }).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            in.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        try {
            out.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
