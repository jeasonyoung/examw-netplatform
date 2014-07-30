<#--选择题基础模版-->
<#macro item_choice dg_answer_view>
<script type="text/javascript">
<!--
$(function(){
	//dg
	var dg = $("#${module}_${CURRENT_ITEM_TYPE}_dg").datagrid({
		width:728,
		height:170,
		fitColumns:true,
		rownumbers:true,
		border:true,
		striped:true,
		idField:"id",
		sortName:"orderNo",
		sortOrder:"asc",
		columns:[[{
			field:"id",
			checkbox:true
		},{
			title:"选项内容",
			field:"content",
			width:100,
			align:"left",
			formatter: function(value,row,index){
				return $.trim(value);
			}
		},{
			title:"正确答案",
			field:"opt",
			width:10,
			align:"center",
			formatter: function(value,row,index){
				return "<input type='${dg_answer_view}' name='answer' value='"+row["id"] +"'/>";
			}
		}]],
		onLoadError:function(e){
			<@error_dialog "e"/>
		}
		<#if CURRENT_OPTS_STATUS>,
		toolbar:[{
			iconCls:"icon-add",
			text:"添加选项",
			handler:function(){
				edit_window("新增选项",0,null);
			}
		},"-",{
			iconCls:"icon-remove",
			text:"删除选项",
			handler:function(){
				var rows = dg.datagrid("getChecked");
				if(rows && rows.length > 0){
					$.messager.confirm("确认","您是否确认删除选中的数据?",function(r){
						if(!r)return;
						for(var i = 0; i < rows.length; i++){
							var index = dg.datagrid("getRowIndex",rows[i]);
							if(index > -1){
								dg.datagrid("deleteRow",index);
							}
						}
					});
				}
			}
		}],
		onDblClickRow:function(rowIndex,rowData){
			edit_window("编辑选项",rowIndex,rowData);
		}
		</#if>
	});
	<#if CURRENT_OPTS_STATUS>
	//edit window
	function edit_window(title,index,row){
		var d = $("<div/>").dialog({
			title:title,
			width:500,
			height:200,
			href:"<@s.url '/admin/papers/paper/item/option'/>",
			modal:true,
			buttons:[{
				text:"保存",
				iconCls:"icon-save",
				handler:function(){
					var data = $("#${module}_edit_opt_form").form("serialize");
					if($.trim(data["content"]) == ""){
						$.messager.alert("警告","请输入选项内容！");
						return;
					}
					if(!row) {
						dg.datagrid("appendRow",data);
					}else{
						dg.datagrid("updateRow",{
							index:index,
							row:data
						});
					}
					d.dialog("close");
				}
			},{
				text:"关闭",
				iconCls:"icon-cancel",
				handler:function(){
					d.dialog("close");
				}
			}],
			onClose:function(){
				$(this).dialog("destroy");
			},
			onLoad:function(){
				if(row){
					$("#${module}_edit_opt_form").form("load",row);
					$("#${module}_edit_opt_form textarea").kindeditor("setValue",row.content);
				}
			}
		});
	};
	</#if>
	var post = {};
	//validate
	${module}_${CURRENT_ITEM_TYPE}_validate = function(){
		post ={};
		if($("#${module}_${CURRENT_ITEM_TYPE}_edit_form").form("validate")){
			post["id"] = $("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='id']").val();
			post["structureId"] = "${CURRENT_STRUCTURE_ID}";
			post["type"] = ${CURRENT_ITEM_TYPE};
			post["typeName"] = $.trim("${CURRENT_ITEM_TYPE_NAME}");
			post["serial"] = $.trim($("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='serial']").val());
			post["score"] = $.trim($("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='score']").val());
			post["orderNo"] = $.trim($("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='orderNo']").val());
			
			post["item"] = {"id": $("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='item_id']").val()};
			post["item"]["type"] = ${CURRENT_ITEM_TYPE};
			post["item"]["content"] = $.trim($("#${module}_${CURRENT_ITEM_TYPE}_edit_form textarea[name='content']").val());
			if($.trim(post["item"]["content"]) == ""){
				$.messager.alert("警告","请输入题干内容！");
				return false;
			}
			var rows = dg.datagrid("getRows");
			if(rows == null || rows.length == 0){
				$.messager.alert("警告","请添加选项！");
				return false;
			}
			$("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='answer']:checked").each(function(i,n){
				if(i == 0){
					post["item"]["answer"] = $.trim($(this).val());
				}else{
					post["item"]["answer"] += "," + $.trim($(this).val());
				}
			});
			if($.trim(post["item"]["answer"]) == ""){
				$.messager.alert("警告","请确定选项中的正确答案！");
				return false;
			}
			post["item"]["analysis"] = $.trim($("#${module}_${CURRENT_ITEM_TYPE}_edit_form textarea[name='analysis']").val());
			if($.trim(post["item"]["analysis"]) == ""){
				$.messager.alert("警告","请输入答案解析！");
				return false;
			}
			return true;
		}
		return false;
	};
	//save
	${module}_${CURRENT_ITEM_TYPE}_update = function(){
		post["item"]["children"] = [];
		var rows = dg.datagrid("getRows");
		if(rows){
			$.each(rows,function(i,n){
				n["orderNo"] = i + 1;
				post["item"]["children"].push(n);
			});
		}
		return post;
	};
	//load
	${module}_${CURRENT_ITEM_TYPE}_load = function(data){
		if(data){
			 $("#${module}_${CURRENT_ITEM_TYPE}_edit_form").form("load",data);
			if(data.item){
				$("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='item_id']").val(data.item.id);
				if(data.item.content) $("#${module}_${CURRENT_ITEM_TYPE}_edit_form textarea[name='content']").kindeditor("setValue",data.item.content);
				if(data.item.children){
					$.each(data.item.children,function(i,n){
						dg.datagrid("appendRow",n);
					});
				}
				if(data.item.analysis)$("#${module}_${CURRENT_ITEM_TYPE}_edit_form textarea[name='analysis']").kindeditor("setValue",data.item.analysis);
				if(data.item.answer){
					$.each(data.item.answer.split(","),function(i,n){
						if($.trim(n) != ""){
							$("#${module}_${CURRENT_ITEM_TYPE}_edit_form input[name='answer'][value='"+n+"']").attr("checked",true);
						}
					});
				}
			}
		}
	};
});
//-->
</script>
<form id="${module}_${CURRENT_ITEM_TYPE}_edit_form"  method="POST" style="padding:10px;">
	<fieldset style="float:left;margin-top:2px;width:96%;border:solid 1px #ccc;">
		<legend>题干内容</legend>
		<div style="float:left;">
			<label>题号：</label>
			<input type="text" name="serial" title="" class="easyui-validatebox" data-options="required:true" style="width:128px;"/> 
			<input type="hidden" name="id" /><input type="hidden" name="item_id" />
		</div>
		<div style="float:left;">
			<label style="width:75px;">分数：</label>
			<input type="text" name="score" class="easyui-numberbox" value="0" style="width:50px;" />
		</div>
		<div style="float:left;">
			<label style="width:75px;">排序号：</label>
			<input type="text" name="orderNo" class="easyui-numberspinner" data-options="required:true,min:1,value:1" style="width:80px;" />
		</div>
		<div style="float:left;margin-top:2px;">
			<textarea style="float:left" name="content" class="easyui-kindeditor"  data-options="minWidth:728,minHeight:70" rows="5" cols="20" />
		</div>
	</fieldset>
	<fieldset style="float:left;margin-top:2px;width:96%;border:solid 1px #ccc;">
		<legend>选项</legend>
		<table id="${module}_${CURRENT_ITEM_TYPE}_dg"></table>
	</fieldset>
	<fieldset style="float:left;margin-top:2px;width:96%;border:solid 1px #ccc;">
		<legend>答案解析</legend>
		<textarea style="float:left" name="analysis" class="easyui-kindeditor"  data-options="minWidth:728,minHeight:80" rows="5" cols="20" />
	</fieldset>
</form>
</#macro>