package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TeamRepository teamRepository;

    public void run(String...args) {
        User admin = new User("admin", "admin", true);
        Role adminRole = new Role("admin", "ROLE_ADMIN");

        User user = new User("user", "user", true);
        Role userRole = new Role("user", "ROLE_USER");

        userRepository.save(admin);
        roleRepository.save(adminRole);
        userRepository.save(user);
        roleRepository.save(userRole);

        Team cicadas = new Team("Cicadas", "Montgomery");
        Set<Player> cicadaPlayers = new HashSet<>();
        cicadas.setPlayers(cicadaPlayers);

        Team blueCrabs = new Team("Blue Crabs", "Bethesda");
        Set<Player> blueCrabPlayers = new HashSet<>();
        blueCrabs.setPlayers(blueCrabPlayers);

        Team woodpeckers = new Team("Woodpeckers", "Wheaton");
        Set<Player> woodpeckerPlayers = new HashSet<>();
        woodpeckers.setPlayers(woodpeckerPlayers);

        Player romo = new Player("Tony", "Romo", "Quarterback", 22,
                "https://res.cloudinary.com/dkim/image/upload/v1628862062/Romo_pnbonw.png", cicadas);
        Player witten = new Player("Jason", "Witten", "Tight End", 21,
                "https://res.cloudinary.com/dkim/image/upload/v1628862062/Witten_iwzqed.png", cicadas);

        Player dak = new Player("Dak", "Prescott", "Quarterback", 20,
                "https://res.cloudinary.com/dkim/image/upload/v1628862257/dak_mb78oq.webp", blueCrabs);
        Player dez = new Player("Dez", "Bryant", "Wide Reciever", 21,
                "https://res.cloudinary.com/dkim/image/upload/v1628862374/dez_u0wobk.png", blueCrabs);

        Player sean = new Player("Sean", "Lee", "Linebacker", 22,
                "https://res.cloudinary.com/dkim/image/upload/v1628862445/sean_lee_rgamgm.png", woodpeckers);

        Player demarcus = new Player("DeMarcus", "Lawrence", "Defensive End", 20,
                "https://res.cloudinary.com/dkim/image/upload/v1628862517/dlaw_idzyum.png", woodpeckers);

        cicadaPlayers.add(romo);
        cicadaPlayers.add(witten);

        blueCrabPlayers.add(dak);
        blueCrabPlayers.add(dez);

        woodpeckerPlayers.add(sean);
        woodpeckerPlayers.add(demarcus);

        teamRepository.save(cicadas);
        teamRepository.save(blueCrabs);
        teamRepository.save(woodpeckers);
    }
}
