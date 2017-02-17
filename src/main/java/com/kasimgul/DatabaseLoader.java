package com.kasimgul;

import com.kasimgul.domain.Announce;
import com.kasimgul.domain.Comment;
import com.kasimgul.domain.User;
import com.kasimgul.repository.AnnounceRepository;
import com.kasimgul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {


    private final AnnounceRepository announceRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(AnnounceRepository announceRepository, UserRepository userRepository) {
        this.announceRepository = announceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<User> users = Arrays.asList(
                new User("username0", "password", "michaelJackson@gmail.com"),
                new User("username1", "password", "jacob@gmail.com"),
                new User("username2", "password", "norman@gmail.com"),
                new User("username3", "password", "k_freemansmith@gmail.com"),
                new User("username4", "password", "seth_lk@gmail.com"),
                new User("username5", "password", "mrstreetgrid@gmail.com"),
                new User("username6", "password", "anthonymikhail@gmail.com"),
                new User("username7", "password", "boog690@gmail.com"),
                new User("username8", "password", "faelor@gmail.com"),
                new User("username9", "password", "christophern@gmail.com"),
                new User("kasimgul", "ksm123", "kasimgul@gmail.com")
        );

        for (User user : users) {
            user.setCreatedAt(new Date());
            user.setMessageList(new ArrayList<>());
            if(user.getUsername().equals("kasimgul"))
                user.setRoles(new String[] {"ROLE_USER", "ROLE_ADMIN"});
            else
                user.setRoles(new String[] {"ROLE_USER"});
        }
        userRepository.deleteAll();
        userRepository.save(users);

        String[] categories = {
                "Temizlik", "Teknik", "Tasima", "Tamir", "Boya Badana"
        };

        String[] cities = {
                "Izmir", "Istanbul", "Ankara", "Mugla", "Ardahan",
                "Bursa", "Adiyaman", "Edirne", "Trabzon", "Adana", "Sivas"
        };

        String[] states = {
                "Merkez", "Cankaya", "Gaziler Mahallesi", "Sahilyolu", "Cumhuriyet Meydani"
        };

        String announceTemplate = "%s konusunda deneyimli yardimci araniyor.";

        announceRepository.deleteAll();
        Random random = new Random();
        IntStream.range(0, 100)
                .forEach(i -> {
                    User user = users.get(random.nextInt(10) + 1);
                    String category = categories[i % categories.length];
                    String city = cities[i % cities.length];
                    String state = states[i % states.length];
                    String adText = String.format(announceTemplate, category);
                    Announce ad = new Announce(category, adText);
                    ad.setCommentList(new ArrayList<>());
                    ad.setCreatedAt(new Date());
                    ad.setOwnerUsername(user.getUsername());
                    ad.setCity(city);
                    ad.setState(state);
                    Comment comment = new Comment("This is a test Comment");
                    User u = users.get(random.nextInt(10) + 1);
                    comment.setAuthorsUsername(u.getUsername());
                    comment.setCreatedAt(new Date());
                    ad.getCommentList().add(comment);
                    announceRepository.save(ad);
                });
    }
}
