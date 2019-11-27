package com.yfy.app.net.maintain;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name =  TagFinal.MAINNEW_GET_MAIN_LIST_USER+Base.RESPONSE)
public class MaintainListRes {

    @Attribute(name = Base.XMLNS, empty = Base.NAMESPACE, required = false)
    private String nameSpace;


    @Element(name =  TagFinal.MAINNEW_GET_MAIN_LIST_USER+Base.RESULT, required = false)
    public String get_Maintain_userResult;
}
