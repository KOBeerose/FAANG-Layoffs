package com.jee.faang.models.metier;

import com.jee.faang.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee extends User {
    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Company> companies = new ArrayList<>() ;

    public Employee(String username, String email, String password, String fullName) {
        super(username, email, password,fullName);
    }
}
