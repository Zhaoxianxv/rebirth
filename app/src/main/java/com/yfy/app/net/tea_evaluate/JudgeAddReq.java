package com.yfy.app.net.tea_evaluate;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/5/22.
 */
@Root(name = TagFinal.TEA_JUDGE_CLASS, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"year"})
public class JudgeAddReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "session_key", required = false)
    private String session_key= Base.user.getSession_key();

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "year", required = false)
    private int year= 0;

    public void setYear(int year) {
        this.year = year;
    }
}
