package kr.mapo.eco100.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {
    BOARD("게시판"),
    CHALLENGE("챌린지");

    private String kind;
}
