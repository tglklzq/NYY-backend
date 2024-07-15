package org.lzq.nyy.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Admin_log {
    private Integer log_id;
    private Integer admin_id;
    private String action;
    private String details;
    private Date action_time;
}
