package com.yfy.app.net.user;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name = TagFinal.READNOTICE, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"type"})
public class ReadNoticeReq {
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key= Base.user.getSession_key();
    ///
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "type", required = false)
    public String type;     ///

}
