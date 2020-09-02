package com.cy.pj.common.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class Node implements Serializable{
	private static final long serialVersionUID = 9102665096902784699L;
	private Integer id;
	private String name;
	private Integer parentId;
}
