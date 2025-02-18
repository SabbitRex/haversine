package org.lucidity.delivery.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Location {
    private double latitude;
    private double longitude;
}