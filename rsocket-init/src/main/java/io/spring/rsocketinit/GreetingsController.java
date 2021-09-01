package io.spring.rsocketinit;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Locale;

@Controller
public class GreetingsController {
    @MessageMapping("greetings.{lang}")
    public String greet(@DestinationVariable("lang") Locale lang
            , @Payload String name) {
        System.out.println("locale: " + lang.getLanguage());
        return "Hello, " + name + "!";
    }
}