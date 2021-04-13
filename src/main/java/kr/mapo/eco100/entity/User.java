package kr.mapo.eco100.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long userNo;

    private Integer level;

    private String nickname;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    //cascade는 join한 객체의 영속성을 관리할 수 있게 해주고, orphanRemoval은 user가 삭제되면 관련 객체들을 삭제해 준다
    private List<Board> boards;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Comment> comments;

}
