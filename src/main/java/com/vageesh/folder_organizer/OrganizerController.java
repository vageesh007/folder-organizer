package com.vageesh.folder_organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;        // ← add this

@Controller
public class OrganizerController {

    @Autowired
    private OrganizerService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/organize")
    public String organize(@RequestParam("folderPath") String folderPath,
                           Model model) {
        Path path = Paths.get(folderPath);   // ← use Paths.get()

        if (!Files.exists(path)) {
            model.addAttribute("message", "❌ Path does not exist.");
            model.addAttribute("enteredPath", folderPath);
            return "index";
        }
        if (!Files.isDirectory(path)) {
            model.addAttribute("message", "❌ Not a directory. Please enter a folder path.");
            model.addAttribute("enteredPath", folderPath);
            return "index";
        }

        try {
            service.organize(path);
            model.addAttribute("message", "✔ Successfully organized!");
        } catch (Exception e) {
            model.addAttribute("message", "❌ Error: " + e.getMessage());
        }

        model.addAttribute("enteredPath", folderPath);
        return "index";
    }
}
