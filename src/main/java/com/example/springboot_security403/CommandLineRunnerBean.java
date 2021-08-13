package com.example.springboot_security403;


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

    public void run(String...args){
        User user = new User("bart", "bart@domain.com", "bart", "Bart", "Simpson", true); //the names and passwords are examples
        Role userRole = new Role("bart", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);

        User admin = new User("super", "super@domain.com", "super", "Super", "Hero", true);
        Role adminRole1 = new Role("super", "ROLE_ADMIN");
        Role adminRole2 = new Role("super", "ROLE_USER");

        userRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);

        //creating teams

        Team team = new Team();
        team.setName("The Cicadas");
        team.setCity("Maryland");

        Team team2 = new Team();
        team2.setName("The Blue Crabs");

        Team team3 = new Team();
        team3.setName("Woodpeckers");

        //creating players
        Player player = new Player();
        player.setFirstName("Pete");
        player.setLastName("Italiano");
        player.setAge(5);
        player.setImage("");
        player.setTeam(team);

        Player player2 = new Player();
        player2.setFirstName("Harry");
        player2.setLastName("Noah");
        player2.setAge(5);
        player2.setImage("");
        player2.setTeam(team);

        Player player3 = new Player();
        player3.setFirstName("Oliver");
        player3.setLastName("Michael");
        player3.setAge(5);
        player3.setImage("");
        player3.setTeam(team2);

        Player player4 = new Player();
        player4.setFirstName("George");
        player4.setLastName("Italiano");
        player4.setAge(5);
        player4.setImage("");
        player4.setTeam(team2);

        Player player5 = new Player();
        player5.setFirstName("Nathan");
        player5.setLastName("Luck");
        player5.setAge(5);
        player5.setImage("");
        player5.setTeam(team3);

        Player player6 = new Player();
        player6.setFirstName("Adoni");
        player6.setLastName("Fit");
        player6.setAge(5);
        player6.setImage("");
        player6.setTeam(team3);

        //adding the player to an empty list

        Set<Player> players = new HashSet<>();
        players.add(player);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);

        team.setPlayers(players);
        team2.setPlayers(players);
        team3.setPlayers(players);

        teamRepository.save(team);
        teamRepository.save(team2);
        teamRepository.save(team3);


    }
}
