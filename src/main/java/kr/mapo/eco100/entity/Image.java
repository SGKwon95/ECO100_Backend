package kr.mapo.eco100.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String filename;

    private String kind;

    @OneToOne
    @JoinColumn(name="board_id")
    private Board board;
}
