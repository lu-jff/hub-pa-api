package com.function;

import java.io.Serializable;
import java.util.List;

public class CsvModel implements Serializable {

  private static final long serialVersionUID = -996346261287121570L;
  
  private String fileName;
  private List<CsvRowModel> rows;
}
