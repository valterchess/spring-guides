package com.example.restserviceinitial;

import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
@RestController
public class GreetingController {
    private final String template = "Hello, %s!";
    private AtomicLong id = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name",
            defaultValue = "World") String name) {
        return new Greeting(id.incrementAndGet(),
                String.format(template, name + "\n"));
    }
}