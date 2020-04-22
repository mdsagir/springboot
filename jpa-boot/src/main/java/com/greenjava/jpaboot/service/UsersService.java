package com.greenjava.jpaboot.service;

import com.greenjava.jpaboot.model.User;
import com.greenjava.jpaboot.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UsersService {

    @Autowired
    private UserRepo userRepo;

    public void jpaTest() {

        List<String> names = Arrays.asList("sagir", "kunal");
        List<String> emails = Arrays.asList("tech.sagir@gmail.com", "kunal@gmail.com");
//
//        List<User> name = userRepo.findDistinctNameByNameInAndPasswordInAndStatus
//                (names, emails, true);

        List<Object[]> qauryData = userRepo.getQauryData(names, emails);


        List<Emp> dbList = new ArrayList<>();
        dbList.add(new Emp("sagir", "tech.sagir@gmail.com"));


        List<Emp> update = new ArrayList<>();
        for (Object object[] : qauryData) {

            String name = (String) object[0];
            String email = (String) object[1];

            for (Emp emp : dbList) {
                if (emp.getName().equals(name) && emp.getEmail().equals(email)) {
                    update.add(emp);
                }
            }
        }
        update.forEach(System.out::println);




//        dbList.stream()
//                .filter(Objects::nonNull)
//                .filter(emp ->
//                        qauryData.stream()
//                                .filter(Objects::nonNull)
//                        .anyMatch(objects ->
//
//                              //  Arrays.stream(objects);
//
//                                )
//
//                        );

//        List<Emp> emps = new ArrayList<>();
//        for (Object[] o : qauryData) {
//            emps.add(new Emp((String) o[0], (String) o[1]));
//        }
//        emps.forEach(System.out::println);

    }

}

class Emp {
    String name;
    String email;

    public Emp() {
    }

    public Emp(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
