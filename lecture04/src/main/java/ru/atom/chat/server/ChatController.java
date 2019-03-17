package ru.atom.chat.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private Queue<String> messages = new ConcurrentLinkedQueue<>();
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        usersOnline.put(name, name);
        messages.add("[" + name + "] logged in");
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        String responseBody = String.join("\n", usersOnline.keySet().stream().sorted().collect(Collectors.toList()));
        return ResponseEntity.ok(responseBody);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    //TODO
    @RequestMapping(
        path = "logout",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity logout(@RequestParam("name") String name) {
        if (usersOnline.containsKey(name)) {
            usersOnline.remove(name);
            return ResponseEntity.ok("User " + name + " successfully logged out!");
        }

        return ResponseEntity.badRequest().body("User " + name + " is not exists");
    }

    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    //TODO
    @RequestMapping(
        path = "say",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (usersOnline.containsKey(name)) {
            messages.add("(" + name + "): " + msg);
            return ResponseEntity.ok("(" + name + "): " + msg);
        } else {
            return ResponseEntity.badRequest().body("User with name " + name + " is not found");
        }
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    //TODO
    @RequestMapping(
        path = "chat",
        method = RequestMethod.GET,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity chat() {
        String responseBody = String.join("\n", messages);

        return ResponseEntity.ok(responseBody);
    }

    @RequestMapping(
        path = "deletechat",
        method = RequestMethod.DELETE,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity deleteHistory() {
        messages.clear();

        return ResponseEntity.ok("Messages deleted successfully!");
    }

    @RequestMapping(
        path = "currentdate",
        method = RequestMethod.GET,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity getCurrenetDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return ResponseEntity.ok(df.format(new Date()));
    }
}
