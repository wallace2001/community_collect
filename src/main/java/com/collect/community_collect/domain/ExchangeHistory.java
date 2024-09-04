package com.collect.community_collect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "exchange_history")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeHistory {

    @Id
    private String id;
    private String fromCenterId;
    private String toCenterId;
    private List<Resource> resourcesSent;
    private List<Resource> resourcesReceived;
    private Date date;

    // Getters e Setters
}
