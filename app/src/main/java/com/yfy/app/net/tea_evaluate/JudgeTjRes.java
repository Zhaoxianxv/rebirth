package com.yfy.app.net.tea_evaluate;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name =  TagFinal.TEA_JUDGE_STATISTICS_CLASS+"Response")
public class JudgeTjRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name =  TagFinal.TEA_JUDGE_STATISTICS_CLASS+"Result", required = false)
    public String judge_statistics_classResult;
}
