/**
 * 
 */
package com.hanhan.store.generated.template.freemarker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.vip.vjtools.vjkit.time.DateFormatUtil;

import com.github.jerryxia.devutil.ObjectId;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author JerryXia
 *
 */
public class RenderObjectIdDateFtlEx implements TemplateMethodModelEx {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
        SimpleScalar args0 = (SimpleScalar) args.get(0);
        String id = args0.getAsString();
        if (StringUtils.isNotBlank(id)) {
            ObjectId objectId = new ObjectId(id);
            return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(objectId.getDate());
        } else {
            return StringUtils.EMPTY;
        }
    }

}
