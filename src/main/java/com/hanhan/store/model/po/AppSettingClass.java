package com.hanhan.store.model.po;

public class AppSettingClass {
    private String id;

    private Integer loadType;

    private String className;

    private String contentMd5;

    private String classFileName;

    private byte[] classFileData;

    private String jsonSchema;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return load_type
     */
    public Integer getLoadType() {
        return loadType;
    }

    /**
     * @param loadType
     */
    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }

    /**
     * @return class_name
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return content_md5
     */
    public String getContentMd5() {
        return contentMd5;
    }

    /**
     * @param contentMd5
     */
    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    /**
     * @return class_file_name
     */
    public String getClassFileName() {
        return classFileName;
    }

    /**
     * @param classFileName
     */
    public void setClassFileName(String classFileName) {
        this.classFileName = classFileName;
    }

    /**
     * @return class_file_data
     */
    public byte[] getClassFileData() {
        return classFileData;
    }

    /**
     * @param classFileData
     */
    public void setClassFileData(byte[] classFileData) {
        this.classFileData = classFileData;
    }

    /**
     * @return json_schema
     */
    public String getJsonSchema() {
        return jsonSchema;
    }

    /**
     * @param jsonSchema
     */
    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }
}