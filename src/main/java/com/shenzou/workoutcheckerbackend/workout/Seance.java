package com.shenzou.workoutcheckerbackend.workout;

import com.shenzou.workoutcheckerbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "seance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seance {
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @Id
    @GeneratedValue
    private Integer id;
    private Date date;
    private String name;
    @OneToMany(mappedBy = "seance")
    private List<Serie> series;
}
