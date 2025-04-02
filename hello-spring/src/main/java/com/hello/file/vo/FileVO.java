package com.hello.file.vo;

/**
 * @TableName FILE
 * @TableComment null
 */
public class FileVO {

    /**
     * @ColumnName FL_ID
     * @ColumnType NUMBER(38, 0)
     * @ColumnComment null
     */
    private int flId;

    /**
     * @ColumnName ID
     * @ColumnType NUMBER(38, 0)
     * @ColumnComment null
     */
    private int id;

    /**
     * @ColumnName FL_NM
     * @ColumnType VARCHAR2(255)
     * @ColumnComment null
     */
    private String flNm;

    /**
     * @ColumnName OBFS_FL_NM
     * @ColumnType VARCHAR2(255)
     * @ColumnComment null
     */
    private String obfsFlNm;

    /**
     * @ColumnName OBFS_PTH
     * @ColumnType VARCHAR2(2000)
     * @ColumnComment null
     */
    private String obfsPth;

    /**
     * @ColumnName FL_SZ
     * @ColumnType NUMBER(38, 0)
     * @ColumnComment null
     */
    private long flSz;

    public int getFlId() {
        return this.flId;
    }
    
    public void setFlId(int flId) {
        this.flId = flId;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFlNm() {
        return this.flNm;
    }
    
    public void setFlNm(String flNm) {
        this.flNm = flNm;
    }
    
    public String getObfsFlNm() {
        return this.obfsFlNm;
    }
    
    public void setObfsFlNm(String obfsFlNm) {
        this.obfsFlNm = obfsFlNm;
    }
    
    public String getObfsPth() {
        return this.obfsPth;
    }
    
    public void setObfsPth(String obfsPth) {
        this.obfsPth = obfsPth;
    }
    
    public long getFlSz() {
        return this.flSz;
    }
    
    public void setFlSz(long l) {
        this.flSz = l;
    }
    
    @Override
    public String toString() {
        return "FileVO(flId: " + flId + ", id: " + id + ", flNm: " + flNm + ", obfsFlNm: " + obfsFlNm + ", obfsPth: " + obfsPth + ", flSz: " + flSz + ", )";
    }
}