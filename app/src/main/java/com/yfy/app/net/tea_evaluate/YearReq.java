package com.yfy.app.net.tea_evaluate;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/5/22.
 */
@Root(name = TagFinal.TEA_JUDGE_YEAR, strict = false)
@Namespace(reference = Base.NAMESPACE)
public class YearReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "session_key", required = false)
    private String session_key= Base.user.getSession_key();

}
