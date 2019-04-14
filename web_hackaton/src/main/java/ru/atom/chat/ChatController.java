package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.chat.databasemodels.DbMessage;
import ru.atom.chat.databasemodels.IMessageService;
import ru.atom.chat.models.Message;
import ru.atom.chat.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
    @Autowired
    private IMessageService messageService;

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private Queue<User> users = new ConcurrentLinkedQueue<>();

    private User admin = new User("admin", "admin");

    private final String chatFilePath = "D:\\JavaTest\\ChatLog.txt";

    @RequestMapping(
        path = "register",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User register(@RequestBody User user) {
        if (users.contains(user)) {
            return null;
        }

        users.add(user);

        return user;
    }

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
        path = "login",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
        )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody User user) throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (User usr : users) {
            if (usr.username.equals(user.username) && usr.password.equals(user.password)) {
                usersOnline.put(user.username, user.username);
                File chatLogFile = new File(chatFilePath);
                boolean fileCreated = chatLogFile.createNewFile();
                FileOutputStream fileStream = new FileOutputStream(chatFilePath, true);
                if (fileCreated) {
                    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
                    Message message = new Message();
                    message.username = user.username;
                    message.message = " logged in!";
                    message.date = df.format(new Date());
                    objectStream.writeObject(message);
                    objectStream.close();
                } else {
                    AppendingObjectOutputStream objectStream = new AppendingObjectOutputStream(fileStream);
                    Message message = new Message();
                    message.username = user.username;
                    message.message = " logged in!";
                    message.date = df.format(new Date());
                    objectStream.writeObject(message);
                    objectStream.close();
                }

                DbMessage dbMessage = new DbMessage();
                dbMessage.setName(user.username);
                dbMessage.setMessage(" logged in!");
                dbMessage.setDate(df.format(new Date()));
                messageService.addMessage(dbMessage);

                fileStream.close();

                return ResponseEntity.ok(user);
            }
        }

        if(admin.equals(user)){
            File chatLogFile = new File(chatFilePath);
            boolean fileCreated = chatLogFile.createNewFile();
            FileOutputStream fileStream = new FileOutputStream(chatFilePath, true);
            if (fileCreated) {
                ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
                Message message = new Message();
                message.username = user.username;
                message.message = " logged in!";
                message.date = df.format(new Date());
                objectStream.writeObject(message);
                objectStream.close();
            } else {
                AppendingObjectOutputStream objectStream = new AppendingObjectOutputStream(fileStream);
                Message message = new Message();
                message.username = user.username;
                message.message = " logged in!";
                message.date = df.format(new Date());
                objectStream.writeObject(message);
                objectStream.close();
            }

            DbMessage dbMessage = new DbMessage();
            dbMessage.setName(user.username);
            dbMessage.setMessage(" logged in!");
            dbMessage.setDate(df.format(new Date()));
            messageService.addMessage(dbMessage);

            fileStream.close();

            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().body(null);
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
        path = "chat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity chat() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(chatFilePath);
        ObjectInputStream oi = new ObjectInputStream(fi);

        List<Message> messages = new ArrayList<Message>();
        boolean cont = true;
        while (cont) {
            Message message = null;
            try {
                message = (Message) oi.readObject();
            } catch (Exception ex) {
                cont = false;
            }
            if (message != null) {
                messages.add(message);
            } else {
                cont = false;
            }
        }

        fi.close();
        oi.close();

        return ResponseEntity.ok(messages.toArray());
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
        path = "online",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity online() {
        return ResponseEntity.ok(usersOnline.keySet().stream().sorted().collect(Collectors.toList()));
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
        path = "signout",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestBody User user) throws IOException {
        if (usersOnline.containsKey(user.username)) {
            usersOnline.remove(user.username);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            File chatLogFile = new File(chatFilePath);
            chatLogFile.createNewFile();
            FileOutputStream fileStream = new FileOutputStream(chatFilePath, true);
            AppendingObjectOutputStream objectStream = new AppendingObjectOutputStream(fileStream);
            Message message = new Message();
            message.username = user.username;
            message.message = " logged out!";
            message.date = df.format(new Date());
            objectStream.writeObject(message);
            objectStream.close();
            fileStream.close();

            DbMessage dbMessage = new DbMessage();
            dbMessage.setName(user.username);
            dbMessage.setMessage(" logged out!");
            dbMessage.setDate(df.format(new Date()));
            messageService.addMessage(dbMessage);

            return ResponseEntity.ok(user);
        }

        if(admin.equals(user)){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            File chatLogFile = new File(chatFilePath);
            chatLogFile.createNewFile();
            FileOutputStream fileStream = new FileOutputStream(chatFilePath, true);
            AppendingObjectOutputStream objectStream = new AppendingObjectOutputStream(fileStream);
            Message message = new Message();
            message.username = user.username;
            message.message = " logged out!";
            message.date = df.format(new Date());
            objectStream.writeObject(message);
            objectStream.close();
            fileStream.close();

            DbMessage dbMessage = new DbMessage();
            dbMessage.setName(user.username);
            dbMessage.setMessage(" logged out!");
            dbMessage.setDate(df.format(new Date()));
            messageService.addMessage(dbMessage);
        }

        return ResponseEntity.badRequest().body(null);
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
        path = "say",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity say(@RequestBody Message message) throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (usersOnline.containsKey(message.username)) {
            File chatLogFile = new File(chatFilePath);
            chatLogFile.createNewFile();
            FileOutputStream fi = new FileOutputStream(chatFilePath, true);
            AppendingObjectOutputStream oi = new AppendingObjectOutputStream(fi);
            message.date = df.format(new Date());
            /*Pattern pattern = Pattern.compile("https?://[a-z/0-9.]+");
            Matcher matcher = pattern.matcher(message.message);
            while (matcher.find()) {
                message.message = message.message.replace(message.message.substring(matcher.start(), matcher.end()), "<a href=\"" + message.message.substring(matcher.start(), matcher.end()) + "\">" + message.message.substring(matcher.start(), matcher.end()) + "</a>");
            }*/

            DbMessage dbMessage = new DbMessage();
            dbMessage.setName(message.username);
            dbMessage.setMessage(message.message);
            dbMessage.setDate(message.date);
            messageService.addMessage(dbMessage);
            oi.writeObject(message);
            oi.close();
            fi.close();


            return ResponseEntity.ok(message);
        }

        return ResponseEntity.badRequest().body("User " + message.username + "does not exists!");
    }
}
