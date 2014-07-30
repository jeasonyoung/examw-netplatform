<#--试卷题型模版-->
<#--题型输出-->
<#macro page_item item>
<#if (item ??) && (item.type ??)>
	<#switch item.type>
		<#case 1><#--选择题-->
			<@item_choice item/>
		<#break>
		<#case 2><#--多选-->
			<@item_choice item/>
		<#break>
		<#case 3><#--不定向选-->
			<@item_choice item/>
		<#break>
		<#case 4><#--判断题-->
			<@item_judge item/>
		<#break>
		<#case 5><#--简单题-->
			<@item_qanda item/>
		<#break>
		<#case 6><#--共享题干-->
			<@item_share_title item/>
		<#break>
		<#case 7><#--共享答案-->
			<@item_share_answer item/>
		<#break>
		<#default>
		<!--类型不能解析-->
	</#switch>
</#if>
</#macro>
<#--选择题-->
<#macro item_choice data>
<dl id="s_${data.id}" style="float:left;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${data.item.content}<#if (data.score ??) && (data.score > 0)>(分数：${data.score})</#if>
		</p>
	</dt>
	<#if (data.item ??) && (data.item.children ??)>
	<dt style="float:left;list-style:none;margin:0px;">
		<#list data.item.children as i>
		<dd style="float:left;width:96%;">
			<label><input type="<#if data.type == 1>radio<#else>checkbox</#if>" disabled="disabled"  <#if (data.item.answer ??)><#list data.item.answer?split(",") as a><#if i.id == a>checked="checked"</#if></#list></#if>/> ${i.content}</label>
		</dd>
		</#list>
	</dt>
	</#if>
	<#if (data.item ??) && (data.item.analysis ??)>
	<fieldset style="float:left;width:96%;margin-top:5px;margin-bottom:5px;border:solid 1px #ccc;">
		<legend>答案解析</legend>
		<p>${data.item.analysis}</p>
	</fieldset>
	</#if>
</dl>
</#macro>
<#--共享题干选择-->
<#macro item_share_title_item data root>
<dl id="s_${data.id}" style="float:left;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${root.content}<#if (data.score ??) && (data.score > 0)>(分数：${data.score})</#if>
		</p>
	</dt>
	<#if (root.children ??)>
		<#list root.children as i>
		<dd style="float:left;width:96%;">
			<label><input type="<#if root.type == 1>radio<#else>checkbox</#if>" disabled="disabled"  <#if (root.answer ??)><#list root.answer?split(",") as a><#if i.id == a>checked="checked"</#if></#list></#if>/> ${i.content}</label>
		</dd>
		</#list>
	</#if>
	<#if (root.analysis ??)>
	<fieldset style="float:left;width:96%;margin-top:5px;margin-bottom:5px;border:solid 1px #ccc;">
		<legend>答案解析</legend>
		<p>${root.analysis}</p>
	</fieldset>
	</#if>
</dl>
</#macro>
<#--共选项选择-->
<#macro item_share_answer_item data root opts>
<dl id="s_${data.id}" style="float:left;width:100%;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${root.content}<#if (data.score ??) && (data.score > 0)>(分数：${data.score})</#if>
		</p>
	</dt>
	<#if (root.answer ??) && (opts ??)>
	<fieldset style="float:left;width:96%;border:solid 1px #ccc;">
		<legend>正确答案</legend>
		<#list root.answer?split(",") as a>
		<dl>
			<#list opts as opt>
				<#if (opt.id == a)>
					<dd style="float:left;width:96%;"><p style="float:left;">${opt.content}</p></dd>
					<#break>
				</#if>
			</#list>
		</dl>
		</#list>
	</fieldset>
	</#if>
	<#if (root.analysis ??)>
	<fieldset style="float:left;width:96%;margin-top:5px;margin-bottom:5px;border:solid 1px #ccc;">
		<legend>答案解析</legend>
		<p>${root.analysis}</p>
	</fieldset>
	</#if>
</dl>
</#macro>
<#--判断题-->
<#macro item_judge data>
<dl id="s_${data.id}" style="float:left;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${data.item.content}<#if (data.score ??) && (data.score > 0)>(分数：${data.score})</#if>
		</p>
	</dt>
	<dd style="float:left;">
		<label><input type="radio" disabled="disabled" <#if (data.item.answer ??) && (data.item.answer == ANSWER_JUDGE_RIGTH)>checked="checked"</#if>/>${ANSWER_JUDGE_RIGTH_NAME}</label>
	</dd>
	<dd style="float:left;">
		<label><input type="radio" disabled="disabled" <#if (data.item.answer ??) && (data.item.answer == ANSWER_JUDGE_WRONG)>checked="checked"</#if>/>${ANSWER_JUDGE_WRONG_NAME}</label>
	</dd>
	<#if (data.item ??) && (data.item.analysis ??)>
	<fieldset style="float:left;width:96%;margin-top:5px;border:solid 1px #ccc;">
		<legend>答案解析</legend>
		<p>${data.item.analysis}</p>
	</fieldset>
	</#if>
</dl>
</#macro>
<#--简单题-->
<#macro item_qanda data>
<dl id="s_${data.id}" style="float:left;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${data.item.content}<#if (data.score ??) && (data.score > 0)>(分数：${data.score})</#if>
		</p>
	</dt>
	<fieldset style="float:left;margin-top:5px;width:96%;border:solid 1px #ccc;">
		<legend>正确答案</legend>
		<p>${data.item.answer }</p>
	</fieldset>
	<#if (data.item ??) && (data.item.analysis ??)>
	<fieldset style="float:left;margin-top:5px;width:96%;border:solid 1px #ccc;">
		<legend>答案解析</legend>
		<p>${data.item.analysis}</p>
	</fieldset>
	</#if>
</dl>
</#macro>
<#--共享题干-->
<#macro item_share_title data>
<dl id="s_${data.id}" style="float:left;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${data.item.content}<#if (data.score ??) && (data.score > 0)>(分数：${data.score})</#if>
		</p>
	</dt>
	<#if (data.itemScores ??) && (data.item.children ??)>
	<ul style="float:left;list-style:none;margin:0px;">
		<#list data.itemScores as sc>
			<#list data.item.children as c>
				<#if (sc.itemId == c.id)>
					<li style="float:left;width:100%;">
						<@item_share_title_item sc c/>
					</li>
					<#break>
				</#if>
			</#list>
		</#list>
	</ul>
	</#if>
</dl>
</#macro>
<#--共享答案-->
<#macro item_share_answer data>
<dl id="s_${data.id}" style="float:left;width:100%;">
	<dt>
		<p style="float:left;">
			<#if data.serial ??>${data.serial}.</#if>${data.item.content}<#if item.score ??>(${item.score})</#if>
		</p>
	</dt>
	<#if (data.item.children ??) && (data.item.children?size > 0)>
	<#assign opts_size=(data.item.children?size)> 
	<#list data.item.children as opt>
		<#if (opt_index < opts_size -1)>
			<dd style="float:left;width:96%;">${opt.content}</dd>
		</#if>
	</#list> 
	<#assign childs=(data.item.children[opts_size-1])>
	<#if (data.itemScores ??) && (childs.children ??)>
	<ul style="float:left;list-style:none;margin:0px;">
		<#list data.itemScores as sc>
			<#list childs.children as c>
				<#if (sc.itemId == c.id)>
					<li style="float:left;width:100%;">
						<@item_share_answer_item sc c data.item.children/>
					</li>
					<#break>
				</#if>
			</#list>
		</#list>
	</ul>
	</#if>
	</#if>
</dl>
</#macro>