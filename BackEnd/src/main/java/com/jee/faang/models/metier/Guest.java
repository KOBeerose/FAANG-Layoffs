package com.jee.faang.models.metier;

import com.jee.faang.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Guest extends User{
    @OneToMany(cascade = CascadeType.ALL)
    private List<Enrollment> enrollmentList = new ArrayList<>();

    public Guest(String username, String email, String password, String fullName) {
        super(username, email, password,fullName);
    }


}
