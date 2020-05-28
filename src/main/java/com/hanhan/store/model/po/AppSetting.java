package com.hanhan.store.model.po;

public class AppSetting {
    /**
     * 编号
     */
    private String id;

    /**
     * 环境
     */
    private String env;

    /**
     * 键，可以重复
     */
    private String key;

    /**
     * 值的类型
     */
    private String valueType;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String mark;

    private String classId;

    /**
     * 有效
     */
    private Boolean valid;

    /**
     * 获取编号
     *
     * @return id - 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取环境
     *
     * @return env - 环境
     */
    public String getEnv() {
        return env;
    }

    /**
     * 设置环境
     *
     * @param env 环境
     */
    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * 获取键，可以重复
     *
     * @return key - 键，可以重复
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置键，可以重复
     *
     * @param key 键，可以重复
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取值的类型
     *
     * @return value_type - 值的类型
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * 设置值的类型
     *
     * @param valueType 值的类型
     */
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取备注
     *
     * @return mark - 备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置备注
     *
     * @param mark 备注
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * @return class_id
     */
    public String getClassId() {
        return classId;
    }

    /**
     * @param classId
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * 获取有效
     *
     * @return valid - 有效
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 设置有效
     *
     * @param valid 有效
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}