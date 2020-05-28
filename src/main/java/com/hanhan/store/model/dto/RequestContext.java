/**
 * 
 */
package com.hanhan.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author JerryXia
 *
 */
@Data
@AllArgsConstructor
public class RequestContext {
    private final String ip4;

    private final String method;
    private final String contentType;

    private final String userAgent;
    private final String accept;
    private final String acceptLanguage;
}
