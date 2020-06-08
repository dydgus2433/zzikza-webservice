package com.zzikza.springboot.web.domain.crawling;


import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "crawling_company")
@Getter
@Setter
@NoArgsConstructor
public class Company {
    @Id
    @Column(name = "COM_ID", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "crawling_company"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "COM"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "com_title", length = 1000)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "com_url", length = 1000)
    private String url;

    @Column(name = "com_query", length = 1000)
    private String query;

    @Builder
    public Company(String title, String url, String description, String query) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.query = query;
    }


}