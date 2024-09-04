package com.collect.community_collect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "community_centers")
@Getter @Setter
@AllArgsConstructor
public class CommunityCenter {

    @Id
    private String id;
    private String name;
    private String address;
    private String location;
    private int maxCapacity;
    private int currentOccupancy;
    private List<Resource> resources;

    // Getters e Setters
}
