package org.example.datn_website_best.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TargetUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TargetUser extends BaseEntity {

    @Column
    private String name;

    @JsonIgnore
    @JsonManagedReference(value = "targetUserProductReference")
    @OneToMany(mappedBy = "targetUser")
    private List<Product> products;

}
