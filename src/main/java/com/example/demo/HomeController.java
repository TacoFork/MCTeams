package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("teams", teamRepository.findAll());
        return "index";
    }

    @GetMapping("/addTeam")
    public String addTeam(Model model){
        model.addAttribute("team", new Team());
        return "teamform";
    }

    @PostMapping("/processTeam")
    public String processTeam(@Valid Team team ,BindingResult result){
        if (result.hasErrors()){
            return "teamform";
        }
        teamRepository.save(team);
        return "redirect:/";
    }

    @GetMapping("/addPlayer")
    public String addPlayer(Model model){
        model.addAttribute("player", new Player());
        model.addAttribute("teams", teamRepository.findAll());
        return "playerform";
    }

    @PostMapping("/processPlayer")
    public String processPlayer(@Valid Player player, BindingResult result, @RequestParam("file") MultipartFile file, Model model){
        if (result.hasErrors()){
            model.addAttribute("teams", teamRepository.findAll());
            return "playerform";
        }
        if (file.isEmpty() && (player.getPhoto() == null || player.getPhoto().isEmpty())){
            player.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1628863946/default_boupm5.jpg");
        }
        else if(!file.isEmpty()){
            try{
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                player.setPhoto(uploadResult.get("url").toString());
            } catch (IOException e){
                e.printStackTrace();
                model.addAttribute("teams", teamRepository.findAll());
                return "redirect:/addPLayer";
            }
        }
        playerRepository.save(player);
        return "redirect:/";
    }

    @RequestMapping("/teamPage/{id}")
    public String teamPage(@PathVariable("id") long id, Model model){
        model.addAttribute("team", teamRepository.findById(id).get());
        return "teampage";
    }

    @RequestMapping("/viewTeams")
    public String allTeams(Model model){
        model.addAttribute("teams", teamRepository.findAll());
        return "updateteam";
    }

    @RequestMapping("/updateTeam/{id}")
    public String updateTeam(@PathVariable("id") long id, Model model){
        model.addAttribute("team", teamRepository.findById(id).get());
        return "teamform";
    }

    @RequestMapping("/deleteTeam/{id}")
    public String deleteTeam(@PathVariable("id") long id){
        teamRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/playerPage/{id}")
    public String playerPage(@PathVariable("id") long id, Model model){
        model.addAttribute("player", playerRepository.findById(id).get());
        return "playerpage";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }
}
