package com.yfy.app.net.maintain;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name =  TagFinal.MAINNEW_GET_TYPE+Base.RESPONSE)
public class BranchTypeRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = TagFinal.MAINNEW_GET_TYPE+"Result", required = false)
    public String getclassidResult;
}
