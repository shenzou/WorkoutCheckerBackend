package com.shenzou.workoutcheckerbackend.workout.seance;

import com.shenzou.workoutcheckerbackend.authentication.user.User;
import com.shenzou.workoutcheckerbackend.workout.serie.Serie;
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
