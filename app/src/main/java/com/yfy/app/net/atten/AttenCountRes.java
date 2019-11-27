package com.yfy.app.net.atten;

import com.yfy.base.Base;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name = "get_attendance_review_countResponse")
public class AttenCountRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = "get_attendance_review_countResult", required = false)
    public String get_attendance_review_countResult;
}
