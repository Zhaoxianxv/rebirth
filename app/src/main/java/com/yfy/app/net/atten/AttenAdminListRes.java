package com.yfy.app.net.atten;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name = TagFinal.ATTENNEW_GET_MAIN_LIST_ADMIN+"Response")
public class AttenAdminListRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = TagFinal.ATTENNEW_GET_MAIN_LIST_ADMIN+"Result", required = false)
    public String result;
}
