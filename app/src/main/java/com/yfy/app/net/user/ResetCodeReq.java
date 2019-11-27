package com.yfy.app.net.user;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.RESET_CODE, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {"phone","usertype"})
public class ResetCodeReq {


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "phone", required = false)
    private String phone;     ///


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "usertype", required = false)
    private String usertype= "tea";

    public void setPhone(String phone) {
        this.phone = phone;
    }
    ///
}
