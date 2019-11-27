package com.yfy.app.net.atten;

import com.yfy.base.Base;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name = "attendance_typeResponse")
public class AttenTypeRes {
    @Attribute(name = "xmlns",  empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = "attendance_typeResult", required = false)
    public String attendance_typeResult;
}
