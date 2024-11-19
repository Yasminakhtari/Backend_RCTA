package com.ifacehub.tennis.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Paging {
    private long noOfPages;
    private int page;
    private int limit;
    private long count;
}
