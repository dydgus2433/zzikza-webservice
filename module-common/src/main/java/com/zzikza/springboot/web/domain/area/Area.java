package com.zzikza.springboot.web.domain.area;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_area")
public class Area {
    @Id
    @Column(name = "CO_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name = "SIDO")
    String sido;

    @Column(name = "GUGUN")
    String gugun;

    @Builder
    public Area(String sido, String gugun) {
        this.sido = sido;
        this.gugun = gugun;
    }
}
