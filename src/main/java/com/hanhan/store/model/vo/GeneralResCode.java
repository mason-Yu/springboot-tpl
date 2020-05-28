/**
 * 
 */
package com.hanhan.store.model.vo;

/**
 * @author JerryXia
 *
 */
public enum GeneralResCode {

    /**
     * OK
     */
    Ok(0),

    /**
     * Fail
     */
    Fail(1);

    private int value;

    GeneralResCode(int v) {
        this.value = v;
    }

    public int getValue() { 
        return value; 
    }
}
