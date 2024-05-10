package com.group4.fashionstarshop.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
public class CategoryRequest {

    private String name;
 // Constructors
    public CategoryRequest() {
    }
    
    public CategoryRequest(String name) {
        this.name = name;
    }
    
    // Getter and Setter methods
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
