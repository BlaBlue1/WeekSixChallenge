package com.example.springboot_security403;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CloudinaryConfig cloudc;


    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("teams", teamRepository.findAll());
        return "index";
    }
    @GetMapping("/addTeam")
    public String addTeam(Model model){
        model.addAttribute("team", new Team());
        return "teamForm";
    }
    @RequestMapping("/updateTeam/{id}")
    public String updateTeam(@PathVariable("id")long id, Model model){
        model.addAttribute("team", teamRepository.findById(id).get());
        return "teamForm";
    }
    @PostMapping("/processTeam")
    public String processTeam(@ModelAttribute Team team){
        teamRepository.save(team);
        return "redirect:/";
    }
    @RequestMapping("/allTeams")
    public String allTeams(Model model){
        model.addAttribute("players", teamRepository.findAll());
        return "allTeam";
    }

    @GetMapping("/addPlayer")
    public String addPlayer(Model model){
        model.addAttribute("player", new Player());
        model.addAttribute("team", teamRepository.findAll());
        return "playerForm";
    }

    @RequestMapping("/updatePlayer/{id}")
    public String updatePlayer(@PathVariable("id")long id, Model model){
        model.addAttribute("player", playerRepository.findById(id).get());
        model.addAttribute("teams", teamRepository.findAll());
        return "playerForm";
    }

    @RequestMapping("/deletePlayer/{id}")
    public String deletePlayer(@PathVariable("id")long id){
        playerRepository.deleteById(id);
        return "redirect:/";
    }
    @PostMapping("/processPlayer")
    public String processPlayer(@ModelAttribute Player player,
                                @RequestParam("file")MultipartFile file){
        if(file.isEmpty() && (player.getImage() == null) || player.getImage().isEmpty()) {
            player.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1jQTHhcQWcW_7fChLchkAdJkqLSg8lus_bQ&usqp=CAU");
        } else if (!file.isEmpty()){
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                player.setImage(uploadResult.get("url").toString());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/addPlayer";
            }
        }
        playerRepository.save(player);
        return "/redirect:/";
    }

    @RequestMapping("/allPlayers")
    public String allPlayers(Model model){
        model.addAttribute("players", playerRepository.findAll());
        return "allPlayers";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }

}
