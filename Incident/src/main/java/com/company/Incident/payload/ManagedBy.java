package com.company.Incident.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ManagedBy {

    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
}
