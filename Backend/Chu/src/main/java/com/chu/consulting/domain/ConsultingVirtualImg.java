package com.chu.consulting.domain;

import com.chu.designer.domain.DesignerPortfolio;
import com.chu.global.domain.HairStyleDict;
import com.chu.global.domain.ImagePath;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class ConsultingVirtualImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    private ImagePath imagePath;

    private Boolean isSelected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_seq")
    private Consulting consulting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_seq")
    private DesignerPortfolio designerPortfolio;

}
