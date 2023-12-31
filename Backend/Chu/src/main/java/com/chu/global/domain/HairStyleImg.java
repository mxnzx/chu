package com.chu.global.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class HairStyleImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hair_style_seq")
    private HairStyleDict hairStyleDict;

    @Embedded
    private ImagePath imagePath;

    public HairStyleImg(){
        this.imagePath = new ImagePath();
    }
}
