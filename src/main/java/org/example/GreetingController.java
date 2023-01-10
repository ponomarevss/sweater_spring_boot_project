package org.example;

import org.example.domain.Message;
import org.example.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

//	@GetMapping("/greeting")
//	public String greeting(
//			@RequestParam(name="name", required=false, defaultValue="World") String name,
//			Map<String, Object> model
//	) {
//		model.put("name", name);
//		return "greeting";
//	}

    @GetMapping("/")
    public String root(Map<String, Object> model) {
        showMessages(model);
        return "root";
    }

    @PostMapping("/")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);
        showMessages(model);
        return "root";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;
            if (filter != null && !filter.isEmpty()) {
                messages = messageRepo.findByTag(filter);
            } else {
                messages = messageRepo.findAll();
            }
        model.put("messages", messages);
        return "root";
    }

    private void showMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
    }
}
